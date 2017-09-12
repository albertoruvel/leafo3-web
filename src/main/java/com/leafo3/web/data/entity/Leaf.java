package com.leafo3.web.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Leaf")
@Table(name = "leaf")
public class Leaf extends AbstractEntity{
    @Column(name = "title")
    private String title;
    
    @Column(name = "comment")
    private String comment;
    
    @Column(name = "location")
    private String location;
    
    @Column(name = "uploaded_by")
    private String uploadedBy;
    
    @Column(name = "damage_class")
    private int damageClass;
    
    @Column(name = "percentage")
    private int percentage; 
    
    @Column(name = "creation_date")
    private String creationDate;
    
    @Column(name = "iso_code")
    private String isoCode;

    public Leaf() {
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

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public void setDamageClass(int damageClass){
        this.damageClass = damageClass;
    }

    public int getDamageClass(){
        return this.damageClass;
    }

    public void setPercentage(int percentage){
        this.percentage = percentage; 
    }

    public int getPercentage(){
        return this.percentage; 
    }

    public void setCreationDate(String creationDate){
        this.creationDate = creationDate; 
    }

    public String getCreationDate(){
        return this.creationDate; 
    }

    public void setIsoCode(String isoCode){
        this.isoCode = isoCode; 
    }

    public String getIsoCode(){
        return this.isoCode; 
    }
}
