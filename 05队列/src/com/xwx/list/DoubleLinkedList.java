package com.xwx.list;

/**
 * @author xwx
 * @create 2020/5/30 10:56
 **/
public class DoubleLinkedList<E> extends AbstractList<E> {

    private Node<E> first; //第一个节点
    private Node<E> last; //最后一个节点

    @Override
    public void clear() {
        //只要不被gc root对象引用，就会被回收
        size = 0;
        first = null;
        last = null;
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

          if (size == index){
              //往最后添加元素
              Node<E> oldLast = last;
              last = new Node<>(element,first,oldLast);
              if (oldLast == null){
                  //空链表添加第一个元素时
                  first = last;
              } else {
                  oldLast.next = last; //凡是要调用方法时，都要考虑该节点是否可能为空
              }
          } else{
              Node<E> next = node(index);
              Node<E> prev = next.prev;
              Node<E> node = new Node<>(element,next,prev);

              next.prev = node;
              if(prev == null) { //index=0
                  first = node;
              } else{
                  prev.next = node;
              }

          }
          size++;
    }

    @Override
    public E remove(int index) {
        //没有马上调用node()方法
        rangeCheck(index);
        Node<E> node = node(index);
        Node<E> prev = node.prev;
        Node<E> next = node.next;
        if(prev==null){
            first = next;
        } else{
            prev.next = next;
        }
        if(next == null){
            last = prev;
        } else {
            next.prev = prev;
        }
//        Node<E> node = node(index);
//        if(index==0){ //第一个元素
//            first = first.next;
//            first.prev = null;
//        } else if(index==size-1){ //最后一个元素
//            last = last.prev;
//            last.next = null;
//        } else {
//            node.prev.next = node.next;
//            node.next.prev = node.prev;
//        }
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
