package com.leafo3.web.core.crypt.impl;

import com.github.roar109.syring.annotation.ApplicationProperty;
import com.leafo3.web.core.crypt.EncryptionService;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Singleton
@Startup
public class EncryptionServiceImpl implements EncryptionService {

    private final Logger log = Logger.getLogger(getClass());

    @Inject
    @ApplicationProperty(name = "com.ridermates.web.crypto.secret.key", type = ApplicationProperty.Types.SYSTEM)
    private String secretKey;

    @Inject
    @ApplicationProperty(name = "com.ridermates.web.crypto.algorithm", type = ApplicationProperty.Types.SYSTEM)
    private String algorithm;

    private final SecureRandom secureRandom = new SecureRandom();

    private Key key;
    private Cipher decryptCipher;
    private Cipher encryptCipher;

    @PostConstruct
    public void init(){
        try{
            //generate key
            key = new SecretKeySpec(secretKey.getBytes(), algorithm);
            initEncryptor();
            initDecryptor();
        }catch(NoSuchPaddingException ex){
            log.error("Could not create padding", ex);
        }catch(NoSuchAlgorithmException ex){
            log.error("No such algorithm", ex);
        }catch(InvalidKeyException ex){
            log.error("Invalid key", ex);
        }
    }

    private void initEncryptor()throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException{
        encryptCipher = Cipher.getInstance(algorithm);
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
    }

    private void initDecryptor()throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException{
        decryptCipher = Cipher.getInstance(algorithm);
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    private String encryptValue(String value){
        try{
            byte[] encryptedBytes = encryptCipher.doFinal(value.getBytes());
            return new String(new Base64().encode(encryptedBytes));
        }catch(BadPaddingException ex){
            log.error("Bad padding", ex);
            return null;
        }catch(IllegalBlockSizeException ex){
            log.error("Illegal block size", ex);
            return null;
        }
    }

    private String decryptValue(String value){
        try{
            byte[] decodeBuffer = new Base64().decode(value.getBytes());
            byte[] encryptedBytes = decryptCipher.doFinal(decodeBuffer);
            return new String(encryptedBytes);
        }catch(BadPaddingException ex){
            log.error("Bad padding", ex);
            return null;
        }catch(IllegalBlockSizeException ex){
            log.error("Illegal block size", ex);
            return null;
        }
    }

    @Override
    public String encrypt(String value) {
        return encryptValue(value);
    }

    @Override
    public String decrypt(String value) {
        return decryptValue(value);
    }

    @Override
    public String createAccessToken() {
        byte[] bytes = new byte[28];
        secureRandom.nextBytes(bytes);
        return encrypt(bytes.toString());
    }
}
