package com.xwx;

/**
 * @author xwx
 * @create 2020/5/28 16:07
 **/
public class ArrayList1 {
    private int size;
    private int [] elements;

    private static final int DEFAULT_CAPACITY = 10; // 初始容量
    private static final int ELEMENT_NOT_FOUND = -1;

    public ArrayList1(int capacity){
        this.size = DEFAULT_CAPACITY;

    }
    public int size(){
        return this.size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int get(int index){
        rangeCheck(index);
        return elements[index];
    }

    public int set(int index, int element){
        rangeCheck(index);
        int old = elements[index];
        elements[index] = element;
        return old;
    }

    public int indexOf(int element){
        for (int i =0;i<size;i++){
            if (elements[i]==element){
                return i;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    public boolean contains(int element){

        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    /**
     * 插入元素
     * @param index
     * @param element
     */
    public void add(int index, int element){
        rangeCheck(index);
        //注意是判断size+1
        ensureCapacity(size+1);

        for(int i = size-1;i>=index;i--){
            elements[i+1] = elements[i];
        }
        elements[index] = element;
        size++;
    }

    /**
     * 在末尾加入元素
     * @param element
     */
    public void append(int element){
        add(size,element);
    }

    public int remove(int index){
        rangeCheck(index);
        int old = elements[index];
        for(int i = index;i<size;i++){
            elements[i] = elements[i+1];
        }
        size--;
        return old;
    }



    public void clear(){
//        for(int i=0;i<size;i++){
//            elements[i] = null;
//        }
        size = 0;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        // 打印形式为: size=5, [99, 88, 77, 66, 55]
        builder.append("size=").append(size).append(", [");
        for (int i = 0; i < this.size; i++){
            if(i != 0) builder.append(" ,");
            builder.append(elements[i]);
        }
        builder.append("]");
        return builder.toString();
    }

    /*************************功能函数**************************************/
    private void ensureCapacity(int capacity){
        int oldCapacity = elements.length;
        if(capacity <= oldCapacity ) return;
        //有符号位移运算符：  左移：负数，正数，高位舍弃，低位补0.
        // 右移：低位舍弃，负数高位补1，正数高位补0
        int newCapacity = oldCapacity+(oldCapacity >> 1);
        int[] newElements = new int[newCapacity];
        for (int i=0; i<size;i++){
            newElements[i] = elements[i];
        }
        elements = newElements;
    }

    private void outOfBounds(int index){
        throw new IndexOutOfBoundsException("index:"+index+", size:"+size);
    }

    // 检查下标越界(不可访问或删除size位置)
    private void rangeCheck(int index){
        if (index < 0 || index >= size){
            outOfBounds(index);
        }
    }

    private void rangeCheckAdd(int index){
        if (index < 0 || index > size){
            outOfBounds(index);
        }
    }

}
