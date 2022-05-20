package com.von;

/**
 * @author xwx
 * @create 2020/6/12 21:27
 **/
public class Asserts {
    public static void test(boolean value){
        try {
            if (!value) throw new Exception("测试未通过");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
