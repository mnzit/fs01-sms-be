package com.sudreeshya.sms.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */

public class TestThread {

    static ThreadLocal<SimpleDateFormat> format1 = ThreadLocal.withInitial(() -> {
        System.out.println(Thread.currentThread().getName());
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    });

    public static void main(String[] args) {


        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {

            executor.execute(new MyThreadRunner(format1));
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }

}


class MyThreadRunner implements Runnable{

    ThreadLocal<SimpleDateFormat> simpleDateFormat;

    public MyThreadRunner(ThreadLocal<SimpleDateFormat> simpleDateFormat) {
        this.simpleDateFormat = simpleDateFormat;
    }

    @Override
    public void run() {

        System.out.println(simpleDateFormat.get().format(new Date()));

        try{
            Thread.sleep(1000);
        }catch(Exception ex){

        }
    }
}