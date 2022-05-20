package com.von;

/**链表与动态数组之间的公共代码
 *
 * 抽象类可以不实现接口中的所有方法，交给子类去实现
 *
 * protected 声明的变量和方法表示有子类继承
 * @author xwx
 * @create 2020/5/30 11:06
 **/
public abstract class AbstractList<E> implements List<E> {
    protected int size;

    // 下标越界抛出的异常
    protected void outOfBounds(int index) {
        throw new IndexOutOfBoundsException("Index:" + index + ", Size:" + size);
    }
    // 检查下标越界(不可访问或删除size位置)
    protected void rangeCheck(int index){
        if(index < 0 || index >= size){
            outOfBounds(index);
        }
    }
    // 检查add()的下标越界(可以在size位置添加元素)
    protected void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) {
            outOfBounds(index);
        }
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element)!=ELEMENT_NOT_FOUND;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void add(E element) {
        add(size, element);
    }
}
