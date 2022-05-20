package com.von;

/**
 * @author xwx
 * @create 2020/6/14 12:04
 **/
public class BinarySearch {

    public static int search(int[] array, int v){
        if (array == null || array.length == 0) return -1;
        int begin = 0;
        int end = array.length;
        while(begin < end){
            int mid = (begin+end)>>1;
            if(v < array[mid]){
                end = mid;
            } else {
                begin = mid +1;
            }
        }
        return begin;
    }
}
