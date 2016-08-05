package com.gy.wm.thread;

import com.gy.wm.exception.TaskUnkilledException;

/**
 * <类详细说明>
 *
 * @Author： Huanghai
 * @Version: 2016-08-05
 **/
public class TaskUnkillThread implements Runnable{
    private long timeout;
    private boolean isCancel;

    public TaskUnkillThread(long timeout)   {
        super();
        this.timeout = timeout;
    }

    /**
     *设置监听是否取消
     */
    public void isCancel()  {
        this.isCancel = true;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(timeout);
            if(!isCancel) {
                throw new TaskUnkilledException("thread is unKilled!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
