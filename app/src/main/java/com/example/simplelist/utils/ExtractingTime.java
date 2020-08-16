package com.example.simplelist.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExtractingTime {
    public static String formatDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        String dateString = format.format(date);
        return dateString;
    }

    public static String formatTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm ");
        String hourMinute = timeFormat.format(date);
        return hourMinute;
    }
}
