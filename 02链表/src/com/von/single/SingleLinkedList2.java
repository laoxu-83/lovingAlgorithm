package com.von.single;

import com.von.AbstractList;

/**添加虚拟头结点
 * @author xwx
 * @create 2020/5/30 10:56
 **/
public class SingleLinkedList2<E> extends AbstractList<E> {

    private Node<E> first; //第一个节点
    public SingleLinkedList2(){
        this.first = new Node<>(null,null);
    }

    @Override
    public void clear() {
        size = 0;
        first = null;
    }

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
//          if (index ==0){
//              first = new Node<>(element,first);
//          } else{
//
//          }

          Node<E> prev = (index == 0) ? first : node(index-1);
          prev.next = new Node<>(element,prev.next);
          size++;

    }

    @Override
    public E remove(int index) {
        //没有马上调用node()方法
        rangeCheck(index);
 //        node = first;
//        if (index ==0){
//            first = first.next;
//        } else{
//
//        }
        Node<E> prev = (index == 0) ? first : node(index-1);
        Node<E> node = prev.next;
        prev.next = node.next;
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
//        int i = 0;
//        if (element == null){
//            while(node.next!=null){
//                if (node.element == null){
//                    return i;
//                }
//                node = node.next;
//                i++;
//            }
//        } else{
//            while(node.next!=null){
//                if (element.equals(node.element)){
//                    return i;
//                }
//                node = node.next;
//                i++;
//            }
//        }
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
            string.append(node.element);
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
        Node<E> node = first.next;
        //找到index节点前面的节点
        for (int i = 0; i<index; i++){
            node = node.next;
        }
        return node;
    }


    public static class Node<E>{
        E element;
        Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }
}
