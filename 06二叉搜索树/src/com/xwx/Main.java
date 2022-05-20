package com.xwx;

import com.xwx.printer.BinaryTrees;


import java.util.Comparator;

/**
 * @author xwx
 * @create 2020/6/1 15:01
 **/
public class Main {



    public static void main(String[] args) {
        //test2();
        test3();
    }

    public static class PersonComparator implements Comparator<Person> { // 比较器
        @Override
        public int compare(Person e1, Person e2) {
            return e1.getAge() - e2.getAge();
        }
    }
    // Person类型的数据
    public static void test2(){
        // Java，匿名类
        BinarySearchTree<Person> bst = new BinarySearchTree<>(new Comparator<Person>() {
            @Override
            public int compare(Person e1, Person e2) {
                return e1.getAge() - e2.getAge();
            }
        });
        Integer date[] = new Integer[] { 7, 4, 9, 2, 5, 8, 11, 3, 12, 1};
        for (int i = 0; i < date.length; i++) {
            bst.add(new Person(date[i]));
        }
        BinaryTrees.println(bst);
    }

    public static void test3(){
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        Integer data[] = new Integer[]{7, 4, 9, 2, 5, 8, 11, 3, 12, 1};
        for(int i = 0; i<data.length;i++){
            bst.add(data[i]);
        }
        BinaryTrees.println(bst);
      //  bst.preOrderTraversal();
        bst.levelOrder(new BinarySearchTree.Visitor<Integer>(){
            @Override
            public boolean visit(Integer element) {
                System.out.print("_"+element);
                return element == 4?true:false;
            }
        });
    }

}
