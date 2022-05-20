package com.von;

/**缩容
 * @author xwx
 * @create 2020/5/28 9:15
 **/
//@SuppressWarnings("unchecked")
public class MyArrayList2<E> extends AbstractList<E> {
    private int size;		// 元素的数量
    private E[] elements; 	// 所有的元素

    private static final int DEFAULT_CAPACITY = 10; // 初始容量
    private static final int ELEMENT_NOT_FOUND = -1;

    public MyArrayList2(int capacity) { // 容量小于10一律扩充为10
        capacity = (capacity < DEFAULT_CAPACITY) ? DEFAULT_CAPACITY : capacity;
        /*
        注意泛型情况下，此处的创建方法
        将基类强转
         */
       elements = (E[]) new Object[capacity];
    }

    public MyArrayList2(){
        this(DEFAULT_CAPACITY);
    }

    @Override
    public String toString() {
        // 打印形式为: size=5, [99, 88, 77, 66, 55]
        StringBuilder string = new StringBuilder();
        string.append("size=").append(size).append(", [");
        for (int i = 0; i < size; i++) {
            if(0 != i) string.append(", ");
            string.append(elements[i]);
        }
        string.append("]");
        return string.toString();
    }
    /**
     * 元素的数量
     * @return
     */
    public int size(){
        return size;
    }
    /**
     * 是否为空
     * @return
     */
    public boolean isEmpty(){
        return size == 0;
    }
    /**
     * 是否包含某个元素
     * @param element
     * @return
     */
    public boolean contains(E element){
        return indexOf(element) != ELEMENT_NOT_FOUND; // 找的到该元素则返回True
    }
    /**
     * 在index位置插入一个元素
     * @param index
     * @param element
     */
    public void add(int index, E element){
        rangeCheckForAdd(index); // 检查下标越界
        ensureCapacity(size + 1); // 确保容量够大

        // 先从后往前开始, 将每个元素往后移一位, 然后再赋值
        for (int i = size - 1; i >= index; i--) {
            elements[i + 1] = elements[i];
        }
        elements[index] = element; // 复制
        size++;
    }

    /**
     * 添加元素到数组最后
     */
    public void add(E element){
        add(size, element);
    }
    /**
     * 获取index位置的元素
     * @param index
     * @return 原来的元素ֵ
     */
    public E get(int index){
        rangeCheck(index);
        return elements[index];
    }
    /**
     * 设置index位置的元素
     * @param index
     * @param element
     * @return 原来的元素ֵ
     */
    public E set(int index, E element){
        rangeCheck(index);
        E old = elements[index];
        elements[index] = element;
        return old;
    }
    /**
     * 删除index位置的元素
     * @param index
     * @return
     */
    public E remove(int index){
        rangeCheck(index);

        // 从前往后开始移, 用后面的元素覆盖前面的元素
        E old = elements[index];
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[--size] = null; // 删除元素后, 将最后一位设置为null
        trim();
        return old;
    }

    /**
     * 剩余空间占总空间的一半时缩容
     */
    private void trim() {
        int capacity = elements.length;
        int newCapacity = capacity>>1;
        if (size > newCapacity || capacity <= DEFAULT_CAPACITY ) return;

        //剩余空间还很多
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i<size;i++){
            newElements[i] = elements[i];
        }
        elements = newElements;
    }

    /**
     *
     * 查看元素的索引
     * @param element
     * @return
     */
    public int indexOf(E element){
        if(element == null){ // 对 null 进行处理
            for (int i = 0; i < size; i++) {
                if(elements[i] == null) return i;
            }
        }else{
            for (int i = 0; i < size; i++) {
                //if(elements[i].equals(element)) return i;
                //避免存储对象是null，造成空指针异常
                if(element.equals(elements[i])) return i;
            }
        }
        return ELEMENT_NOT_FOUND;
    }
    /**
     * 清除所有元素
     */
    public void clear(){
        // 使用泛型数组后要注意内存管理(将元素置null)
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        if(elements != null && elements.length>DEFAULT_CAPACITY){
            elements = (E[]) new Object[DEFAULT_CAPACITY];
        }
        size = 0;
    }
    /**
     * 扩容操作
     */
    private void ensureCapacity(int capacity){
        int oldCapacity = elements.length;
        if(oldCapacity >= capacity) return;
        // 新容量为旧容量的1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[])new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i]; // 拷贝原数组元素到新数组
        }
        elements = newElements;
        System.out.println("size="+oldCapacity+", 扩容到了"+newCapacity);
    }
//    /****************封装好的功能函数**************************/
//    // 下标越界抛出的异常
//    private void outOfBounds(int index) {
//        throw new IndexOutOfBoundsException("Index:" + index + ", Size:" + size);
//    }
//    // 检查下标越界(不可访问或删除size位置)
//    private void rangeCheck(int index){
//        if(index < 0 || index >= size){
//            outOfBounds(index);
//        }
//    }
//    // 检查add()的下标越界(可以在size位置添加元素)
//    private void rangeCheckForAdd(int index) {
//        if (index < 0 || index > size) {
//            outOfBounds(index);
//        }
//    }
    /****************封装好的功能函数***************************/

}
