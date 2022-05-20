package com.xwx;

import com.xwx.printer.BinaryTrees;
import com.xwx.tree.BinarySearchTree;

/**
 * @author xwx
 * @create 2020/6/3 15:45
 **/
public class Main {
    public static void main(String[] args) {
        test1();
    }

    // Integer类型的数据
    public static void test1(){
        Integer date[] = new Integer[] { 7, 4, 9, 2, 5, 8, 11, 3, 12, 1};
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < date.length; i++) {
            bst.add(date[i]);
        }
        BinaryTrees.println(bst);
    }


}
