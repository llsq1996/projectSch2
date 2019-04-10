package com.example.demo.util;

import com.google.common.collect.Lists;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Help {

    public static List<String> list= Lists.newArrayList();
    static {
        list.add("类别");
        list.add("配送方式");
        list.add("商家名称");
        list.add("商家负责人");
        list.add("联系电话");
        list.add("客服电话");
        list.add("起送价");
        list.add("配送费");
    }
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
