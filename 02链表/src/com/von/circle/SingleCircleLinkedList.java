package com.von.circle;

import com.von.AbstractList;

/**循环链表，区别主要在于第一个元素增加删除时，last.next的区别
 * @author xwx
 * @create 2020/5/30 10:56
 **/
public class SingleCircleLinkedList<E> extends AbstractList<E> {

    private Node<E> first; //第一个节点

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
          if (index ==0){
              Node<E> newFirst = new Node<>(element,first);
              //空链表的特殊处理
              Node<E> last = (size==0)? newFirst:node(size-1);
              last.next = newFirst;
              first = newFirst;
//             Node<E> last = (size==0)? first : node(size-1);
//             first = new Node<>(element,first);
//             last.next = first;
          } else{
              Node<E> prev = node(index-1);
              prev.next = new Node<>(element,prev.next);
          }
          size++;

    }

    @Override
    public E remove(int index) {
        //没有马上调用node()方法
        rangeCheck(index);
        Node<E> node = first;
        if (index ==0){
            if (size==1){
                first = null;
            }else{
                //注意在first变更前查找尾结点
                Node<E> last = (size==0)? first : node(size-1);
                first = first.next;
                last.next = first;
            }
        } else{
            Node<E> prev = node(index - 1);
            node = prev.next;
            prev.next = node.next;
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
        Node<E> node = first;
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

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(element).append("_").append(next.element);
            return builder.toString();
        }
    }
}
