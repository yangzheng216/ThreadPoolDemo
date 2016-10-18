package com.parbat.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yangzheng on 2016/10/18.
 * 测试4个类别的Executor的区别
 */
public class MyExecutor {
    private static void run(ExecutorService threadPool) {
        for(int i = 1; i < 5; i++) {
            final int taskID = i;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    for(int i = 1; i < 5; i++) {
                        try {
                            Thread.sleep(20);// 为了测试出效果，让每次任务执行都需要一定时间
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("第" + taskID + "次任务的第" + i + "次执行");
                    }
                }
            });
        }
        threadPool.shutdown();// 任务执行完毕，关闭线程池
    }

    /**
     * 在FixedThreadPool中，有一个固定大小的池。
     * 如果当前需要执行的任务超过池大小，那么多出的任务处于等待状态，直到有空闲下来的线程执行任务，
     * 如果当前需要执行的任务小于池大小，空闲的线程也不会去销毁。
     * 重用：fixedThreadPool与cacheThreadPool差不多，也是能reuse就用，但不能随时建新的线程
     固定数目：其独特之处在于，任意时间点，最多只能有固定数目的活动线程存在，此时如果有新的线程要建立，只能放在另外的队列中等待，直到当前的线程中某个线程终止直接被移出池子
     超时：和cacheThreadPool不同，FixedThreadPool没有IDLE机制（可能也有，但既然文档没提，肯定非常长，类似依赖上层的TCP或UDP IDLE机制之类的），
     使用场景：所以FixedThreadPool多数针对一些很稳定很固定的正规并发线程，多用于服务器
     源码分析：从方法的源代码看，cache池和fixed 池调用的是同一个底层池，只不过参数不同：
     fixed池线程数固定，并且是0秒IDLE（无IDLE）
     cache池线程数支持0-Integer.MAX_VALUE(显然完全没考虑主机的资源承受能力），60秒IDLE
     */
    public static void runFixedThreadPool(){
        // 创建可以容纳3个线程的线程池
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        run(fixedThreadPool);
    }

    /**
     * CachedThreadPool会创建一个缓存区，将初始化的线程缓存起来。会终止并且从缓存中移除已有60秒未被使用的线程。
     如果线程有可用的，就使用之前创建好的线程，
     如果线程没有可用的，就新创建线程。
     重用：缓存型池子，先查看池中有没有以前建立的线程，如果有，就reuse；如果没有，就建一个新的线程加入池中
     使用场景：缓存型池子通常用于执行一些生存期很短的异步型任务，因此在一些面向连接的daemon型SERVER中用得不多。
     超时：能reuse的线程，必须是timeout IDLE内的池中线程，缺省timeout是60s，超过这个IDLE时长，线程实例将被终止及移出池。
     结束：注意，放入CachedThreadPool的线程不必担心其结束，超过TIMEOUT不活动，其会自动被终止。
     */
    public static void runCachedThreadPool(){
        // 线程池的大小会根据执行的任务数动态分配
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        run(cachedThreadPool);
    }

    /**
     * SingleThreadExecutor得到的是一个单个的线程，这个线程会保证你的任务执行完成。
     如果当前线程意外终止，会创建一个新线程继续执行任务，这和我们直接创建线程不同，也和newFixedThreadPool(1)不同。
     */
    public static void runSingleThreadPool(){
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        run(singleThreadPool);
    }

    /**
     * ScheduledThreadPool是一个固定大小的线程池，与FixedThreadPool类似，执行的任务是定时执行。
     */
    public static void runScheduledThreadPool(){
        ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);
        run(scheduledThreadPool);
    }
}
