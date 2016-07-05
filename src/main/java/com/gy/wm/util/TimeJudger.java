package com.gy.wm.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/5.
 */
public class TimeJudger {
    public static void main(String[] args) {
        String s = "2016-07-25";
        String [] given = s.split("\\-");
        String s1  = new SimpleDateFormat("yy-MM-dd").format(new Date());

    }
}
