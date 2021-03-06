package com.von.sort;

/**将【交换】 转为【挪动】
 *
 * ① 先将待插入的元素备份
 * ② 头部有序数据中比待插入元素大的，都朝尾部方向挪动1个位置
 * ③ 将待插入元素放到最终的合适位置
 * @author xwx
 * @create 2020/6/14 14:42
 **/
public class InsertionSort2<E extends Comparable<E>> extends Sort<E> {

    @Override
    protected void sort() {
        for(int begin = 1; begin < array.length;begin++){
            int cur = begin;
            E v = array[cur];
            while( cur>0 && cmp(v,array[cur-1])<0){
                array[cur] = array[cur-1];
                cur--;
            }
            array[cur]=v;
        }
    }
}
