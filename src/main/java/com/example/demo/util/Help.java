package com.example.demo.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class Help {
public static String timeFormat(Timestamp time){
    String timeStr="";
    if(Objects.nonNull(time)){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timeStr=format.format(time);
    }
    return timeStr;
}
}
