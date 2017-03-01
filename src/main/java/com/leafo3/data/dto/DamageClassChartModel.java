package com.leafo3.data.dto;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Alberto Rubalcaba <arubalcaba@24hourfit.com>
 */
public class DamageClassChartModel {
    private List<Map<String, Object>> data; 
    private boolean success;

    public DamageClassChartModel() {
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
