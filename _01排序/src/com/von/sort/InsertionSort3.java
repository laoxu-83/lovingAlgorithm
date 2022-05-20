package com.von.sort;

/**
 *二叉搜索确定插入位置
 *
 * @author xwx
 * @create 2020/6/14 14:42
 **/
public class InsertionSort3<E extends Comparable<E>> extends Sort<E> {

    @Override
    protected void sort() {
        for(int begin = 1; begin < array.length;begin++){
            int inseartIndex = search(begin);
            E v = array[begin];
            for(int i=begin; i>inseartIndex;i--){
                array[i] = array[i-1];
            }
            array[inseartIndex] = v;
        }
    }

    /**
     * 利用二分搜索找到 index 位置元素的待插入位置
     * @param index
     * @return
     */
    public int search( int index){

        int begin = 0;
        int end = index;
        while(begin < end){
            int mid = (begin+end)>>1;
            if(cmp(array[index] , array[mid])<0){
                end = mid;
            } else {
                begin = mid +1;
            }
        }
        return begin;
    }
}
