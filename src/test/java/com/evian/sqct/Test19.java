package com.evian.sqct;

/**
 * ClassName:Test19
 * Package:com.evian.sqct
 * Description:请为该功能做描述
 *
 * @Date:2020/9/24 13:18
 * @Author:XHX
 */
public class Test19 {
    public static volatile int race = 0;
    public static int race2 = 0;
    public static void increase(){
        race++;
    }

    public static volatile boolean race2Switch = true;

    public static void increase2(){
        while (race2Switch){
            race2Switch = false;
            race2++;
            race2Switch = true;
            return;
        }
    }

    private static final int THREADS_COUNT = 20;

    public static void main(String[] args) {
        Thread[] threads  = new Thread[THREADS_COUNT];
        for (int i=0;i<THREADS_COUNT;i++){
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i=0;i<10000;i++){
                        increase2();
                    }
                }
            });
            threads[i].start();
        }

        // 等待所有累加线程都结束
        while (Thread.activeCount()>2){
            Thread.yield();
        }

        System.out.println(race2);
    }
}
