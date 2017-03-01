package com.leafo3.data.dto;

import java.util.List;

/**
 *
 * @author Alberto Rubalcaba <arubalcaba@24hourfit.com>
 */
public class ListResponse<E> {
    private List<E> items;

    public ListResponse() {
    }

    public List<E> getItems() {
        return items;
    }

    public void setItems(List<E> items) {
        this.items = items;
    }
    
    
}
