package com.xwx;

import com.xwx.sort.*;
import com.xwx.tools.Asserts;
import com.xwx.tools.Integers;

import java.util.Arrays;

/**
 * @author xwx
 * @create 2020/6/13 16:12
 **/
public class Main {

    public static void main(String[] args) {
        Integer[] array = Integers.random(50000, 1, 50000);
        testSorts(array,
//                new BubbleSort1(),		// 冒泡排序
//				new BubbleSort2(),		// 冒泡排序-优化1
//				new BubbleSort3(),		// 冒泡排序-优化2
//				new SelectionSort(), 	// 选择排序
//                new InsertionSort3(),	// 插入排序-二分查找优化
//
                new HeapSort(), 		// 堆排序
                new MergeSort(), 		// 归并排序
                new QuickSort()
                );    	// 快速排序

//        Times.test("HeapSort",()->{
//            new HeapSort().sort(array);
//        });
//
//        Times.test("SelectionSort",()->{
//            new SelectionSort().sort(array2);
//        });
//
//        Times.test("BubbleSort3",()->{
//            new BubbleSort3().sort(array3);
//        });
    }

    static void testSorts(Integer[] array, Sort... sorts) {
        for (Sort sort : sorts) {
            Integer[] newArray = Integers.copy(array);
            sort.sort(newArray);
            Asserts.test(Integers.isAscOrder(newArray));
        }
        Arrays.sort(sorts);
        for (Sort sort : sorts) {
            System.out.println(sort);
        }
    }

}
