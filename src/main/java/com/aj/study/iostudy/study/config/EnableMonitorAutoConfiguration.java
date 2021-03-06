package com.aj.study.iostudy.study.config;

/**
 * @Author: aiJun
 * @Date: 2020-10-22 18:18
 * @Version 1.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.lang.management.*;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 JVM监控
 String[] value() default {}; //数组，获取对应property名称的值，与name不可同时使用
 String prefix() default "";//property名称的前缀，可有可无
 String[] name() default {};//数组，property完整名称或部分名称（可与prefix组合使用，组成完整的property名称），与value不可同时使用
 String havingValue() default "";//可与name组合使用，比较获取到的属性值与havingValue给定的值是否相同，相同才加载配置
 boolean matchIfMissing() default false;//缺少该property时是否可以加载。如果为true，没有该property也会正常加载；反之报错
 */
@Configuration
@ConditionalOnProperty(
        name = {"jvmmonitor.enabled"},
        havingValue = "true",
        matchIfMissing = true
)
public class EnableMonitorAutoConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnableMonitorAutoConfiguration.class);
    private static final long DELAY = 60000L;//  执行任务之前的延迟（以毫秒为单位）。连续执行任务之间的时间（以毫秒为单位）。
    private static final long PERIOD = 600000L;// 连续执行任务之间的时间（以毫秒为单位）

    public EnableMonitorAutoConfiguration() {
        LOGGER.info("####EnableMonitorAutoConfiguration init success####");
    }

    @PostConstruct
    public void init() {
        (new Timer()).schedule(new TimerTask() {
            public void run() {
                EnableMonitorAutoConfiguration.this.printJvm();
            }
        }, DELAY, PERIOD);
    }

    private void printJvm() {
        LOGGER.info("<<<<=====================================================================>>>");
        List<MemoryManagerMXBean> memoryManagerMXBeans = ManagementFactory.getMemoryManagerMXBeans();
        Iterator var2 = memoryManagerMXBeans.iterator();

        while(var2.hasNext()) {
            MemoryManagerMXBean memoryManagerMXBean = (MemoryManagerMXBean)var2.next();
            String name = memoryManagerMXBean.getName();
            String[] memoryPoolNames = memoryManagerMXBean.getMemoryPoolNames();
            LOGGER.info("GC : {}, memory area : {}", name, memoryPoolNames);
        }

        List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();
        Iterator var27 = memoryPoolMXBeans.iterator();

        while(var27.hasNext()) {
            MemoryPoolMXBean memoryPoolMXBean = (MemoryPoolMXBean)var27.next();
            String name = memoryPoolMXBean.getName();
            String[] memoryManagerNames = memoryPoolMXBean.getMemoryManagerNames();
            MemoryType type = memoryPoolMXBean.getType();
            MemoryUsage usage = memoryPoolMXBean.getUsage();
            long used = usage.getUsed() / 1048576L;
            long init = usage.getInit() / 1048576L;
            long max = usage.getMax() / 1048576L;
            long committed = usage.getCommitted() / 1048576L;
            MemoryUsage peakUsage = memoryPoolMXBean.getPeakUsage();
            long peakUsed = peakUsage.getUsed() / 1048576L;
            long peakInit = peakUsage.getInit() / 1048576L;
            long peakMax = peakUsage.getMax() / 1048576L;
            long peakCommit = peakUsage.getCommitted() / 1048576L;
            LOGGER.info(name + ":");
            LOGGER.info("    managers: {}", memoryManagerNames[0]);
            LOGGER.info("    type: {}", type.toString());
            LOGGER.info("    usage: init={}M, used={}M, committed={}M, max={}M", new Object[]{init, used, committed, max});
            LOGGER.info("    peakUsage: peakInit={}M, peakUsed={}M, peakCommit={}M, peakMax={}M", new Object[]{peakInit, peakUsed, peakCommit, peakMax});
            LOGGER.info("");
        }

        LOGGER.info("<<<<=====================================================================>>>\r\n\r\n");
    }
}
