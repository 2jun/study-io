package com.aj.study.iostudy.study;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: aiJun
 * @Date: 2020-10-22 17:26
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
        List<Person> lst = new ArrayList<>();
        lst.add(new Person("张1", 11));
        lst.add(new Person("张2", 12));
        lst.add(new Person("张3", 13));
        System.out.println(JSONObject.toJSONString(lst));
    }
}

class Person {
    private String name;
    private transient int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

}
