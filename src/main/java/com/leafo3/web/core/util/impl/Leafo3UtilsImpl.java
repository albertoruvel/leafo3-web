package com.leafo3.web.core.util.impl;

import com.leafo3.web.core.util.Leafo3Utils;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Singleton
@Startup
public class Leafo3UtilsImpl implements Leafo3Utils{
    private static final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy mm:ss");

    @PostConstruct
    public void init(){
        dateFormat.setTimeZone(TimeZone.getTimeZone("PST"));
    }

    @Override
    public Date parseDate(String date) throws ParseException {
        return dateFormat.parse(date);
    }

    @Override
    public String formatDate(Date date) {
        return dateFormat.format(date);
    }


}
