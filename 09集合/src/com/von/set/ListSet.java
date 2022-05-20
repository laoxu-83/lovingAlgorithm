package com.von.set;

import com.von.list.DoubleLinkedList;
import com.von.list.List;

/**
 * @author xwx
 * @create 2020/6/7 15:58
 **/
public class ListSet<E> implements Set<E> {
    private List<E> list = new DoubleLinkedList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean contains(E element) {
        return list.contains(element);
    }

    @Override
    public void add(E element) {
        int index = list.indexOf(element);
        if(index != list.ELEMENT_NOT_FOUND){
            list.set(index,element);
        } else {
            list.add(element);
        }
    }

    @Override
    public void remove(E element) {
        int index = list.indexOf(element);
        if(index != List.ELEMENT_NOT_FOUND){
            list.remove(index);
        }
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        int size = list.size();
        for (int i=0;i<size;i++){
            visitor.visit(list.get(i));
        }
    }
}
