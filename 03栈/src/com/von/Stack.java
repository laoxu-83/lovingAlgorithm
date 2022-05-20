package com.von;

import com.von.list.List;
import com.von.list.MyArrayList;



/**
 * @author xwx
 * @create 2020/5/31 16:42
 **/
public class Stack<E> {
    private List<E> list = new MyArrayList<>();

    public void clear(){
        list.clear();
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public void push(E element){
        list.add(element);
    }

    public E pop(){
        return list.remove(list.size() - 1);
    }

    public E top(){
        return list.get(list.size() - 1);
    }


}
