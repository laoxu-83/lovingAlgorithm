package com.von.map;

import java.util.Objects;

/**
 * @author xwx
 * @create 2020/6/10 16:24
 **/
public class LinkedHashMap<K,V> extends HashMap<K,V> {
    private LinkNode<K,V> first;
    private LinkNode<K,V> last;

    @Override
    public void clear() {
        super.clear();
        first = null;
        last = null;
    }

    @Override
    public boolean containsValue(V value) {
        LinkNode<K,V> node = first;
        while(node != null){
            if (Objects.equals(value,node.value)) return true;
            node = node.next;
        }
        return false;
    }

    @Override
    protected void afterRemove(Node<K, V> willNode, Node<K, V> removedNode) {
        LinkNode<K, V> node1 = (LinkNode<K, V>) willNode;
        LinkNode<K, V> node2 = (LinkNode<K, V>) removedNode;

        //对于删除度为2的节点的情况
        if (node1 != node2) {
            // 交换linkedWillNode和linkedRemovedNode在链表中的位置
            // 交换prev
            LinkNode<K, V> tmp = node1.prev;
            node1.prev = node2.prev;
            node2.prev = tmp;
            if (node1.prev == null) {
                first = node1;
            } else {
                node1.prev.next = node1;
            }
            if (node2.prev == null) {
                first = node2;
            } else {
                node2.prev.next = node2;
            }

            // 交换next
            tmp = node1.next;
            node1.next = node2.next;
            node2.next = tmp;
            if (node1.next == null) {
                last = node1;
            } else {
                node1.next.prev = node1;
            }
            if (node2.next == null) {
                last = node2;
            } else {
                node2.next.prev = node2;
            }
        }

        LinkNode<K, V> prev = node2.prev;
        LinkNode<K, V> next = node2.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }
    }

    @Override
    public void traversal(Visitor visitor) {
        if (visitor == null) return;
        LinkNode<K,V> node = first;
        while(node != null){
            if (visitor.visit(node.key,node.value)) return;
            node = node.next;
        }

    }

    @Override
    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        LinkNode<K,V> node = new LinkNode<>(key,value,parent);
        if(first == null){
            first = last = node;
        } else{
            last.next = node;
            node.prev = last;
            last = node;
        }
        return node;
    }

    private static class LinkNode<K,V> extends Node<K,V>{
        private LinkNode<K,V> prev;
        private LinkNode<K,V> next;

        public LinkNode(K key, V value, Node<K, V> parent) {
            super(key, value, parent);
        }
    }
}
