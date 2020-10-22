package com.aj.study.iostudy.study.cas;

import com.aj.study.iostudy.kuang.stream.User;
import com.aj.study.iostudy.study.Enum.KeyEnum;

import java.lang.reflect.Field;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: aiJun
 * @Date: 2020-10-14 20:40
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
        // 使用replaceAll()结合Lambda表达式实现
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(4, null);
        map.replaceAll((k, v) -> v.toUpperCase());
        map.compute(4, (k,v) -> v==null ? "2" : v.concat("4"));
        map.forEach((k,v)-> System.out.println(k+"--->"+v));
    }

    private static void test01() throws NoSuchFieldException {
        User user = new User(1,"name",3);
        Field name = user.getClass().getDeclaredField("name");
        System.out.println(name.getName());
        Record record = new Record();
        System.out.println(record.getData(KeyEnum.KEY_1));
    }

    public static class Record {
        private static final Map<KeyEnum, String> extData = new EnumMap<>(KeyEnum.class);
        static {
            extData.put(KeyEnum.KEY_1, "key_1");
            extData.put(KeyEnum.KEY_2, "key_2");
            extData.put(KeyEnum.KEY_3, "key_3");
        }
        public void setData(KeyEnum keyEnum, String data) {
            extData.put(keyEnum, data);
        }
        public String getData(KeyEnum keyEnum) {
            return extData.get(keyEnum);
        }
    }
}
