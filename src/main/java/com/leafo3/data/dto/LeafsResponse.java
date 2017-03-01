package com.leafo3.data.dto;

import com.leafo3.data.entity.Leaf;

/**
 *
 * @author Alberto Rubalcaba <arubalcaba@24hourfit.com>
 */
public class LeafsResponse {

    private Page<Leaf> data;
    private String message;
    private boolean success;

    public void setData(Page<Leaf> leafs) {
        this.data = leafs;
    }

    public Page<Leaf> getData() {
        return this.data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
