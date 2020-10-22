package com.aj.study.iostudy.study.defaultInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: aiJun
 * @Date: 2020-10-22 16:39
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
        ExtendClass aClass = new ExtendClass();
        aClass.abstractMethod();
        aClass.defaultMethod();
        DefaultInterface.staticMethod();

        Map<Integer, Object> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put(i, i);

        }
        map.forEach((k,v)->{
            System.out.println("k=" + k + ",v=" + v);
        } );
    }
}

class ExtendClass implements DefaultInterface {
    @Override
    public void abstractMethod() {
        System.out.println("abstractMethod------------>>>>>");
    }
}