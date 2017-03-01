package com.leafo3.data.dto;

/**
 *
 * @author Alberto Rubalcaba <arubalcaba@24hourfit.com>
 */
public class FindResourceResponse <T>{
    private T data;
    private boolean success;

    public FindResourceResponse(T data, boolean success) {
        this.data = data;
        this.success = success;
    }

    public FindResourceResponse() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
