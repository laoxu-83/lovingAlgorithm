package com.xwx;

import com.xwx.list.DoubleLinkedList;
import com.xwx.list.List;

/**
 * @author xwx
 * @create 2020/5/31 20:02
 **/
public class Queue<E> {
    private List<E> list = new DoubleLinkedList<>();
    /**
     * 入队
     * 添加到尾部
     */
    public void enQueue(E element){
        list.add(element);
    }
    /**
     * 出队
     * 移除队头
     */
    public E deQueue(){
        return list.remove(0);
    }
    /**
     * 元素的数量
     */
    public int size(){
        return list.size();
    }
    /**
     * 清空
     */
    public void clear(){
        list.clear();
    }
    /**
     * 队头元素
     */
    public E top(){
        return list.get(0);
    }
    /**
     * 是否为空
     */
    public boolean isEmpty(){
        return list.isEmpty();
    }

}
