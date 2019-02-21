package com.example.demo.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Help {

    /**
     * 格式化时间
     * @param time
     * @return
     */
    public static String timeFormat(Timestamp time){
    String timeStr="";
    if(Objects.nonNull(time)){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timeStr=format.format(time);
    }
    return timeStr;
}

    /**
     * 与当前时间相差的天数
     * @param time
     * @return
     */
    public static Integer  timeToNow(Timestamp time){
    Long week=new Date().getTime()-time.getTime();
    if(week>0){
        week=week/1000/60/60/24;
    }
    return  week.intValue();
}
}
