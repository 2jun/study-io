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
 * JVM监控
 */
@Configuration
@ConditionalOnProperty(
        name = {"jvmmonitor.enabled"},
        havingValue = "true",
        matchIfMissing = true
)
public class EnableMonitorAutoConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnableMonitorAutoConfiguration.class);
    private static final long DELAY = 60000L;
    private static final long PERIOD = 600000L;

    public EnableMonitorAutoConfiguration() {
        LOGGER.info("####EnableMonitorAutoConfiguration init success####");
    }

    @PostConstruct
    public void init() {
        (new Timer()).schedule(new TimerTask() {
            public void run() {
                EnableMonitorAutoConfiguration.this.printJvm();
            }
        }, 60000L, 600000L);
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
