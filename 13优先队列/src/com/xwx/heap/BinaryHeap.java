package com.xwx.heap;



import java.util.Comparator;


/**最大堆
 * @author xwx
 * @create 2020/6/11 11:10
 **/
public class BinaryHeap<E> extends AbstractHeap<E>  {
    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;
    private Comparator<E> comparator;
    private int size;

    public BinaryHeap( Comparator<E> comparator) {
        super(comparator );
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public BinaryHeap() {
        this(null,null);
    }

    public BinaryHeap(E[] elements, Comparator<E> comparator){
        super(comparator);

        if(elements==null || elements.length==0){
            this.elements = (E[])new Object[DEFAULT_CAPACITY];
        } else{
            int capacity = Math.max(elements.length,DEFAULT_CAPACITY);
            for(int i=0;i<elements.length;i++){
                this.elements[i] = elements[i];
            }
            heapify();
        }



    }

    private void heapify() {
        // 自上而下的上滤
//		for (int i = 1; i < size; i++) {
//			siftUp(i);
//		}

        // 自下而上的下滤
        for (int i = (size >> 1) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    public BinaryHeap(E[] elements){
        this(elements,null);
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public void add(E element) {
        elementNotNullCheck(element);
        ensureCapacity(size + 1);
        elements[size++] = element;
        siftUp(size - 1);
    }

    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;

        // 新容量为旧容量的1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }

    /**
     * 上滤
     * @param index
     */
    private void siftUp(int index){
//        E e = elements[index];
//        while (index > 0){
//            int pindex = (index-1) >> 1;
//            E p = elements[pindex];
//            if (compare(p,e) <=0) return;
//            //将父元素存储在index位置
//            E tmp = elements[index];
//            elements[index] = elements[pindex];
//            elements[pindex] = tmp;
//
//            index = pindex;
//        }
        //确定位置后再摆放上去
        E e = elements[index];
        while (index >0) {
            int pindex = (index-1) >> 1;
            E p = elements[pindex];
            if (compare(e,p)<=0) break;
            elements[index] = p;
            index = pindex;
        }
        elements[index] = e;
    }
    @Override
    public E get() {
        emptyCheck();
        return elements[0];
    }

    private void emptyCheck() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }
    }

    @Override
    public E remove() {
        emptyCheck();
        E root = elements[0];
        elements[0] = elements[size-1];
        elements[size-1] = null;
        size--;
        siftDown(0);

        return root;
    }

    private void siftDown(int index) {
        E e = elements[index];
        int half = size >> 1; // 非叶子节点的数量
        // 第一个叶子节点的索引 == 非叶子节点的数量
        // index < 第一个叶子节点的索引
        // 必须保证index位置是非叶子节点
        while (index < half){
            // index的节点有2种情况
            // 1.只有左子节点
            // 2.同时有左右子节点

            // 默认为左子节点跟它进行比较
            int childIndex = index<<1 + 1;
            E child = elements[childIndex];

            // 右子节点
            int rightIndex = childIndex + 1;
            // 选出左右子节点最大的那个
            if(rightIndex<size && compare(elements[rightIndex],child)>0){
                childIndex = rightIndex;
                child = elements[childIndex];
            }

            if(compare(e,child) >= 0) break;

            // 将子节点存放到index位置
            elements[index] = child;
            // 重新设置index
            index = childIndex;


        }
        elements[index] = e;
    }


    @Override
    public E replace(E element) {
        elementNotNullCheck(element);
        E root = null;
        if(size == 0){
            elements[0] = element;
            size++;
        } else{
            root = elements[0];
            elements[0] = element;
            siftDown(0);
        }
        return root;
    }
    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }


}
