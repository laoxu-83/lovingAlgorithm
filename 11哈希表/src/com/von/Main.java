package com.von;


import com.von.map.HashMap;
import com.von.map.Map;
import com.von.model.Key;
import com.von.model.Person;
import com.von.model.SubKey1;
import com.von.model.SubKey2;




/**
 * @author xwx
 * @create 2020/6/8 15:07
 **/
public class Main {
    public static void main(String[] args) {
        test2(new HashMap<>());
		test3(new HashMap<>());
		test4(new HashMap<>());
		test5(new HashMap<>());

    }

    private static void test3(){
        HashMap<Object,Integer> map = new HashMap<>();
        for (int i=0;i<=18;i++){
            map.put(new Key(i),i);
        }
        map.print();
        System.out.println(map.get(new Key(2)));
        System.out.println(map.size());
    }

    private static void test2(){
        Person p1 = new Person(10,1.67f,"jack");
        Person p2 = new Person(10,1.67f,"jack");
        System.out.println(p1.hashCode());
        System.out.println(p2.hashCode());
    }
    private static void test1(){
        String string = "wangzhe";
        System.out.println(string.hashCode());
//        int hashCode = 0;
//        for (int i=0;i<string.length();i++){
//            char c = string.charAt(i);
//            //hashCode = hashCode*31+c;
//            hashCode = (hashCode << 5) - hashCode +c;
//        }
//        System.out.println(hashCode);
    }


    static void test2(HashMap<Object, Integer> map) {
        for (int i = 1; i <= 20; i++) {
            map.put(new Key(i), i);
        }
        for (int i = 5; i <= 7; i++) {
            map.put(new Key(i), i + 5);
        }
        Asserts.test(map.size() == 20);
        Asserts.test(map.get(new Key(4)) == 4);
        Asserts.test(map.get(new Key(5)) == 10);
        Asserts.test(map.get(new Key(6)) == 11);
        Asserts.test(map.get(new Key(7)) == 12);
        Asserts.test(map.get(new Key(8)) == 8);
    }

    static void test3(HashMap<Object, Integer> map) {
        map.put(null, 1); // 1
        map.put(new Object(), 2); // 2
        map.put("jack", 3); // 3
        map.put(10, 4); // 4
        map.put(new Object(), 5); // 5
        map.put("jack", 6);
        map.put(10, 7);
        map.put(null, 8);
        map.put(10, null);
        Asserts.test(map.size() == 5);
        Asserts.test(map.get(null) == 8);
        Asserts.test(map.get("jack") == 6);
        Asserts.test(map.get(10) == null);
        Asserts.test(map.get(new Object()) == null);
        Asserts.test(map.containsKey(10));
        Asserts.test(map.containsKey(null));
        Asserts.test(map.containsValue(null));
        Asserts.test(map.containsValue(1) == false);
    }

    static void test4(HashMap<Object, Integer> map) {
        map.put("jack", 1);
        map.put("rose", 2);
        map.put("jim", 3);
        map.put("jake", 4);
        map.remove("jack");
        map.remove("jim");
        for (int i = 1; i <= 10; i++) {
            map.put("test" + i, i);
            map.put(new Key(i), i);
        }
        for (int i = 5; i <= 7; i++) {
            Asserts.test(map.remove(new Key(i)) == i);
        }
        for (int i = 1; i <= 3; i++) {
            map.put(new Key(i), i + 5);
        }
        Asserts.test(map.size() == 19);
        Asserts.test(map.get(new Key(1)) == 6);
        Asserts.test(map.get(new Key(2)) == 7);
        Asserts.test(map.get(new Key(3)) == 8);
        Asserts.test(map.get(new Key(4)) == 4);
        Asserts.test(map.get(new Key(5)) == null);
        Asserts.test(map.get(new Key(6)) == null);
        Asserts.test(map.get(new Key(7)) == null);
        Asserts.test(map.get(new Key(8)) == 8);
        map.traversal(new Map.Visitor<Object, Integer>() {
            public boolean visit(Object key, Integer value) {
                System.out.println(key + "_" + value);
                return false;
            }
        });
    }

    static void test5(HashMap<Object, Integer> map) {
        for (int i = 1; i <= 20; i++) {
            map.put(new SubKey1(i), i);
        }
        map.put(new SubKey2(1), 5);
        Asserts.test(map.get(new SubKey1(1)) == 5);
        Asserts.test(map.get(new SubKey2(1)) == 5);
        Asserts.test(map.size() == 20);
    }

}
