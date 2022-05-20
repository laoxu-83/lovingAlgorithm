package com.xwx;

import com.xwx.printer.BinaryTrees;
import com.xwx.tree.RBTree;

/**
 * @author xwx
 * @create 2020/6/6 16:58
 **/
public class Main {
    public static void main(String[] args) {
        //test1();
        test2();
    }

    public static void test1(){
        Integer data[] = new Integer[] {
                55, 87, 56, 74, 96, 22, 62, 20, 70, 68, 90, 50
        };
        RBTree<Integer> rb = new RBTree<>();
        for (int i=0;i<data.length;i++){
            rb.add(data[i]);

        }
        BinaryTrees.println(rb);
    }

    static void test2() {
        Integer data[] = new Integer[] {
                55, 87, 56, 74, 96, 22, 62, 20, 70, 68, 90, 50
        };

        RBTree<Integer> rb = new RBTree<>();
        for (int i = 0; i < data.length; i++) {
            rb.add(data[i]);
        }

        BinaryTrees.println(rb);

        for (int i = 0; i < data.length; i++) {
            rb.remove(data[i]);
            System.out.println("---------------------------------------");
            System.out.println("【" + data[i] + "】");
            BinaryTrees.println(rb);
        }
    }
}
