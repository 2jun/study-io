package com.aj.study.iostudy.study.defaultInterface;

/**
 * @Author: aiJun
 * @Date: 2020-10-22 16:39
 * @Version 1.0
 */
public interface DefaultInterface {

    void abstractMethod();

    default int defaultMethod() {
        int num = 520;
        System.out.println("defaultMethod----->>>>" + num);
        return num;
    }

    static int staticMethod() {
        int num = 520;
        System.out.println("staticMethod----->>>>" + num);
        return num;
    }

}
