package com.von.circle;

import com.von.AbstractList;

/**
 * @author xwx
 * @create 2020/5/30 10:56
 **/
public class DoubleCircleLinkedList<E> extends AbstractList<E> {

    private Node<E> first; //第一个节点
    private Node<E> last; //最后一个节点
    private Node<E> current; // 指针访问当前节点

    @Override
    public void clear() {
        //只要不被gc root对象引用，就会被回收
        size = 0;
        first = null;
        last = null;
    }

    //****************约瑟夫问题***********************
    public void reset(){
        current = first;
    }

    public E next(){
        if(current==null) return null;

        current = current.next;
        return current.element;
    }

    public E remove(){
        if(current==null) return null;
        E old = remove(current);
        if(size == 0){
            current = null;
        } else{
            current = current.next;
        }
        return old;
    }

    private E remove(Node<E> node){

        if(size==1){
            first = null;
            last = null;
        }
        else{
            Node<E> prev = node.prev;
            Node<E> next = node.next;
            prev.next = next;
            next.prev = prev;

            if(node==first){ //删除第一个
                first = next;
            }
            if(node==last){ //删除最后一个
                last = prev;
            }

        }
        size--;
        return node.element;
    }
    //************************************************

    @Override
    public E get(int index) {
        return node(index).element;
    }

    @Override
    public E set(int index, E element) {
        Node<E> node = node(index);
        E old = node.element;
        node.element = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
          rangeCheckForAdd(index);

          if (size == index){
              //往最后添加元素
              Node<E> oldLast = last;
              last = new Node<>(element,first,oldLast);
              if (oldLast == null){
                  //空链表添加第一个元素时
                  first = last;
                  //*****
                  first.next = first;
                  first.prev = first;
              } else {
                  //****
                  first.prev = last;
                  //***
                  oldLast.next = last; //凡是要调用方法时，都要考虑该节点是否可能为空
              }
          } else{
              Node<E> next = node(index);
              Node<E> prev = next.prev;
              Node<E> node = new Node<>(element,next,prev);

              next.prev = node;
              prev.next = node;
              if(index == 0) { //index=0
                  first = node;
              }

          }
          size++;
    }

    @Override
    public E remove(int index) {
        //没有马上调用node()方法
        rangeCheck(index);
        Node<E> node = first;
        if(size==1){
            first = null;
            last = null;
        }
        else{
            node = node(index);
            Node<E> prev = node.prev;
            Node<E> next = node.next;
            prev.next = next;
            next.prev = prev;

            if(index==0){ //删除第一个
                first = next;
            }
            if(index == size-1){ //删除最后一个
                last = prev;
            }

        }
        size--;
        return node.element;

    }

    @Override
    public int indexOf(E element) {

        Node<E> node = first;
        if (element == null){
            for (int i = 0; i<size;i++){
                if (node.element == null) return i;
                node = node.next;
            }
        } else{
            for (int i=0; i<size;i++){
                if(element.equals(node.element)) return i;
                node = node.next;
            }
        }

        return ELEMENT_NOT_FOUND;
    }

    @Override
    public String toString() {
        // 打印形式为: size=5, [99, 88, 77, 66, 55]
        StringBuilder string = new StringBuilder();
        string.append("size=").append(size).append(", [");
        Node<E> node = first;
        for (int i = 0; i < size; i++) {
            if(0 != i) string.append(", ");
            string.append(node);
            node = node.next;
        }
        string.append("]");
        return string.toString();
    }
    /**
     * 获取index位置对应的节点对象
     * @param index
     * @return
     */
    private Node<E> node(int index){
        rangeCheck(index);
        if(index < (size<<1)){
            Node<E> node = first;
            //找到index节点前面的节点,index-1
            for (int i = 0; i<index; i++){
                node = node.next;
            }
            return node;
        } else {
            Node<E> node = last;
            //找到index节点后面的节点,index+1
            for (int i = size-1; i>index; i--){
                node = node.prev;
            }
            return node;
        }


    }


    public static class Node<E>{
        E element;
        Node<E> next;
        Node<E> prev;

        public Node(E element, Node<E> next, Node<E> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;

        }

        public String toString(){
            StringBuilder builder = new StringBuilder();


            if(prev!=null){
                builder.append(prev.element);
            }
            builder.append("_").append(element).append("_");
            if(next !=null){
                builder.append(next.element);
            }

            return builder.toString();
        }
    }
}
