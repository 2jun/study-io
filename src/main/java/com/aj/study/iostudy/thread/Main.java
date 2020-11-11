package com.aj.study.iostudy.thread;

import lombok.SneakyThrows;

import java.util.concurrent.*;

/**
 * @Author: aiJun
 * @Date: 2020-11-10 13:34
 * @Version 1.0
 */
public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        test01();
    }

    /**引用类型对象放到线程里会导致数据共享问题
     *
     * @throws InterruptedException
     */
    private static void test01() throws InterruptedException {
        ExecutorService service = new ThreadPoolExecutor(1,
                1,
                0,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100));
        Person person = new Person();
        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                person.setPerson(true);
            } else {
                person.setPerson(false);
            }
            int finalI = i;
            service.submit(() -> {

                        try {
                            TimeUnit.SECONDS.sleep(5L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(finalI + "---------->>>>>" + person.isPerson);
                    }
            );
            if(i==0) TimeUnit.SECONDS.sleep(6);
        }
        TimeUnit.SECONDS.sleep(10L);
    }
}
class Person{
    boolean isPerson;

    public boolean isPerson() {
        return isPerson;
    }

    public void setPerson(boolean person) {
        isPerson = person;
    }
}