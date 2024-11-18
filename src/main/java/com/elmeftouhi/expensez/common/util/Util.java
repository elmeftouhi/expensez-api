package com.elmeftouhi.expensez.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public static Boolean isValidDate(String strDate){
        if (strDate.trim().isEmpty()) {
            return false;
        }

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(strDate);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
