package com.xwx.set;

/**
 * @author xwx
 * @create 2020/6/7 15:56
 **/
public interface Set<E> {
    int size();
    boolean isEmpty();
    void clear();
    boolean contains(E element);
    void add(E element);
    void remove(E element);
    void traversal(Visitor<E> visitor);

    public static abstract class Visitor<E>{
        boolean stop;
        public abstract boolean visit(E element);
    }
}
