package com.xwx.sort;

/**
 * @author xwx
 * @create 2020/6/13 19:44
 **/
public class SelectionSort<E extends Comparable<E>> extends Sort<E> {
    @Override
    protected void sort() {
        for (int end = array.length - 1; end > 0; end--) {
            int maxIndex = 0;
            for (int begin = 1; begin <= end; begin++) {
                if(cmp(maxIndex,begin)<=0) maxIndex = begin;
            }
            swap(maxIndex,end);
        }

    }
}
