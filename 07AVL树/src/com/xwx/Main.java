package com.xwx;

import com.xwx.printer.BinaryTrees;
import com.xwx.tree.AVLTree;
import com.xwx.tree.BinarySearchTree;

/**
 * @author xwx
 * @create 2020/6/3 15:45
 **/
public class Main {
    public static void main(String[] args) {
        //test1();
        test2();
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

    public static void test2(){
        Integer data[] = new Integer[]{
                85,19,69,3,7,99,95,100
        };

        AVLTree<Integer> avl = new AVLTree<>();
        for (int i=0;i<data.length;i++){
            avl.add(data[i]);
        }
        BinaryTrees.println(avl);
        avl.remove(85);
        BinaryTrees.println(avl);
    }


}
