package com.leafo3.web.core.crypt;

public interface EncryptionService {
    public String encrypt(String value);
    public String decrypt(String value);
    public String createAccessToken();
}
