package com.leafo3.data.entity;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author Alberto Rubalcaba <arubalcaba@24hourfit.com>
 */
@MappedSuperclass
public class AbstractEntity implements Serializable{
    @Id
    @Column(name = "id")
    protected String id;

    public AbstractEntity(){
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
