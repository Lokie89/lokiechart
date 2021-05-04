package com.lokiechart.www.common;

/**
 * @author SeongRok.Oh
 * @since 2021/05/04
 */
public class ThreadSleep {
    public static void doSleep(long timestampMillis) {
        try {
            Thread.sleep(timestampMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
