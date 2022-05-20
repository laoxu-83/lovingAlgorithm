package com.xwx;

import com.xwx.tools.Asserts;

/**
 * @author xwx
 * @create 2020/6/23 20:15
 **/
public class Main {
    public static void main(String[] args) {
        Asserts.test(BrutalForce.indexOf("Hello World", "H") == 0);
        Asserts.test(BrutalForce.indexOf("Hello World", "d") == 10);
        Asserts.test(BrutalForce.indexOf("Hello World", "or") == 7);
        Asserts.test(BrutalForce.indexOf("Hello World", "abc") == -1);

        Asserts.test(KMP.indexOf("Hello World", "H") == 0);
        Asserts.test(KMP.indexOf("Hello World", "d") == 10);
        Asserts.test(KMP.indexOf("Hello World", "or") == 7);
        Asserts.test(KMP.indexOf("Hello World", "abc") == -1);
    }
}
