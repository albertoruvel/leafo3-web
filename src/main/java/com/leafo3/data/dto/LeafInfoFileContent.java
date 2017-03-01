package com.leafo3.data.dto;

import java.io.InputStream;

/**
 *
 * @author Alberto Rubalcaba <arubalcaba@24hourfit.com>
 */
public class LeafInfoFileContent {
    private InputStream leafStream; 
    private String title; 
    private String comment; 
    private String location; 
    private String isoCode; 
    private String email; 

    public LeafInfoFileContent() {
    }

    public InputStream getLeafStream() {
        return leafStream;
    }

    public void setLeafStream(InputStream leafStream) {
        this.leafStream = leafStream;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
