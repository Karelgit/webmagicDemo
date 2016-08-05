package com.gy.wm.exception;

/**
 * 待爬取队列为空，并且等待时间超过预定时常，判断为webmagic无法结束异常
 *
 * @Author： Huanghai
 * @Version: 2016-08-05
 **/
public class TaskUnkilledException extends RuntimeException{
    private static final long serialVersionUID = 551822143131900511L;

    /**
     *抛出异常信息
     * @param str 异常信息
     */
    public TaskUnkilledException(String str) {
        super(str);
    }
}
