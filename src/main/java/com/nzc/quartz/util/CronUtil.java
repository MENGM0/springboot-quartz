package com.nzc.quartz.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CronUtil {

    public static String getCron(Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }
}
