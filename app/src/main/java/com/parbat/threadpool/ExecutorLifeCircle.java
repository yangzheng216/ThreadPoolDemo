package com.parbat.threadpool;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangzheng on 2016/10/18.
 * Executor的生命周期
 */
public class ExecutorLifeCircle {
    public class Task1 implements Callable<String>{
        @Override
        public String call() throws Exception {
            String base = "abcdefghijklmnopqrstuvwxyz0123456789";
            Random random = new Random();
            StringBuffer sb = new StringBuffer();
            for(int i =0;i<10;i++){
                int number = random.nextInt(base.length());
                sb.append(base.charAt(number));
            }
            return sb.toString();
        }
    }

    public class LongTask implements Callable<String>{
        @Override
        public String call() throws Exception {
            TimeUnit.SECONDS.sleep(10);
            return "success";
        }
    }

    /**
     * 1、shutdown方法：这个方法会平滑地关闭ExecutorService，当我们调用这个方法时，ExecutorService停止接受任何新的任务且等待已经提交的任务执行完成(已经提交的任务会分两类：一类是已经在执行的，另一类是还没有开始执行的)，当所有已经提交的任务执行完毕后将会关闭ExecutorService。这里我们先不举例在下面举例。

     2、awaitTermination方法：这个方法有两个参数，一个是timeout即超时时间，另一个是unit即时间单位。这个方法会使线程等待timeout时长，当超过timeout时间后，会监测ExecutorService是否已经关闭，若关闭则返回true，否则返回false。一般情况下会和shutdown方法组合使用。例如：
     */
    public void run1(){
        ExecutorService service = Executors.newFixedThreadPool(4);
        service.submit(new Task1());
        service.submit(new Task1());
        service.submit(new LongTask());
        service.submit(new Task1());
        service.shutdown();
        try {
            while(!service.awaitTermination(1,TimeUnit.SECONDS)){
                System.out.println("线程池没有关闭");
            }
            System.out.println("线程池已经关闭");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 3、shutdownNow方法：这个方法会强制关闭ExecutorService，它将取消所有运行中的任务和在工作队列中等待的任务，这个方法返回一个List列表，列表中返回的是等待在工作队列中的任务。例如：
     */

    public void run2(){
        ExecutorService service = Executors.newFixedThreadPool(3);

        service.submit(new LongTask());
        service.submit(new LongTask());
        service.submit(new LongTask());
        service.submit(new LongTask());
        service.submit(new LongTask());

        List<Runnable> runnables = service.shutdownNow();
        System.out.println(runnables.size());

        try {
            while (!service.awaitTermination(1, TimeUnit.MILLISECONDS)) {
                System.out.println("线程池没有关闭");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程池已经关闭");
    }
}
