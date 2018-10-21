package com.fych;

import java.util.Calendar;

public class DateFormat {

    public static void main(String[] args) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(1539520646972l);
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int m=calendar.get(Calendar.MINUTE);
        int S=calendar.get(Calendar.SECOND);
        System.out.println(year+"-"+month+"-"+day+" "+hour+":"+m+":"+S);
        System.out.println(System.currentTimeMillis());

    }
}
