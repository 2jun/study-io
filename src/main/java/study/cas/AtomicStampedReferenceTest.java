package study.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Author: aiJun
 * @Date: 2020-10-14 22:05
 * @Version 1.0
 */
@Slf4j
public class AtomicStampedReferenceTest {

    // 初始值为1，版本号为0
    private static AtomicStampedReference<Integer> a = new AtomicStampedReference<>(1, 0);

    // 计数器
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {

        new Thread(() -> {
            log.info("线程名字：" + Thread.currentThread() + ", 当前 value = " + a.getReference());
            // 获取当前版本号
            int stamp = a.getStamp();

            // 计数器阻塞，直到计数器为0，才执行
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.info("线程名字：" + Thread.currentThread() + ",CAS操作结果: " + a.compareAndSet(1, 2, stamp, stamp + 1));
        }, "线程1").start();

        // 线程2
        new Thread(() -> {
            // 将 value 值改成 2
            a.compareAndSet(1, 2, a.getStamp(), a.getStamp() + 1);
            log.info("线程名字" + Thread.currentThread() + "value = " + a.getReference());
            // 将 value 值又改成 1
            a.compareAndSet(2, 1, a.getStamp(), a.getStamp() + 1);
            log.info("线程名字" + Thread.currentThread() + "value = " + a.getReference());
            // 线程计数器
            countDownLatch.countDown();
        }, "线程2").start();

    }
}
