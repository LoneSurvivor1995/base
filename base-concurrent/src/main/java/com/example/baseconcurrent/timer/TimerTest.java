package com.example.baseconcurrent.timer;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 第一种 1、schedule(task,delay)
 *        2、schedule(task,date);
 * 第二种 3、schedule(task,delay,period)
 *        4、schedule(task,delay,date)
 * 第三种 5、scheduleAtFixedRate(task,delay,period)
 *        6、scheduleAtFixedRate(task,delay,date)
 *  注:date过了，4>执行一次 6>全部执行
 */

public class TimerTest {
    static Timer timer = new Timer();;
    public static void main(String[] args) {
//        timer1();
//        timer2();
//        timer3();
//        timer4();
        timer5();
    }

    /** 指定延迟 */
    public static void timer1(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(new Date());
            }
        },3000);
    }

    /** 指定延迟和间隔 */
    public static void timer2(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(new Date());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },1000,2000);
    }

    /** 指定延迟和间隔 */
    public static void timer3(){
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(new Date());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },1000,2000);
    }

    /** 指定日期和间隔 */
    public static void timer4(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,13);
        calendar.set(Calendar.MINUTE,54);
        calendar.set(Calendar.SECOND,0);
        Date date = calendar.getTime();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(new Date());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },date,2000);
    }

    /** 线程池 */
    public static void timer5() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(new Date()+" "+Thread.currentThread());
            }
        },1, 1,TimeUnit.SECONDS);
    }
}
