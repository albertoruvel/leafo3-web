package com.leafo3.web.core.util;

import java.text.ParseException;
import java.util.Date;

public interface Leafo3Utils {
    public Date parseDate(String date)throws ParseException;
    public String formatDate(Date date);
}
