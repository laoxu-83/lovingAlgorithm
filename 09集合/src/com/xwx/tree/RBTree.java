package com.xwx.tree;

import java.util.Comparator;

/**
 * @author xwx
 * @create 2020/6/6 10:06
 **/
public class RBTree<E> extends BBST<E> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    public RBTree(Comparator<E> comparator) {
        super(comparator);
    }

    public RBTree() {
        this(null);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;

        //添加的是根结点,或者上溢到根结点
        if(parent==null){
            black(node);
            return;
        }
        //********父节点是黑色，不做处理
        if(isBlack(parent)) return;

        //*************父节点是红色
        // 叔父节点
        Node<E> uncle = parent.sibling();
        // 祖父节点
        Node<E> grand = red(parent.parent);

        //-------叔父节点是红色【B树节点上溢】
        if (isRed(uncle)) {
            black(parent);
            black(uncle);
            // 把祖父节点当做是新添加的节点
            afterAdd(grand);
            return;
        }


        //--------叔父节点不是红色
        if (parent.isLeftChild()){ //L
            red(grand);
            if(node.isLeftChild()){ //LL
                black(parent);
            }else { //LR
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);

        }
        if (parent.isRightChild()){ //R
            red(grand);
            if(node.isRightChild()){ //RR
                black(parent);
            } else{ //RL
                black(node);
                rotateRight(parent);
            }
            rotateLeft(grand);

        }

    }

    @Override
    protected void afterRemove(Node<E> node) {
        //删除结点是红色
       // if(isRed(node)) return;

        //用以取代node的结点是红色
        if (isRed(node)){
            black(node);
            return;
        }

        Node<E> parent = node.parent;
        // 删除的是根节点
        if (parent == null) return;

        // 删除的是黑色子结点
        // 判断被删除的node是左和右
        boolean left = parent.left == null || node.isLeftChild();
        Node<E> sibling = left? parent.right:parent.left;
        if (left) { // 被删除的节点在左边，兄弟节点在右边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                // 更换兄弟
                sibling = parent.right;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }

                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        }else { // 被删除的节点在右边，兄弟节点在左边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateRight(parent);
                // 更换兄弟
                sibling = parent.left;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }

                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }

    }
//    protected void afterRemove(Node<E> node, Node<E> replacement) {
//        //删除结点是红色
//        if(isRed(node)) return;
//
//        //用以取代node的结点是红色
//        if (isRed(replacement)){
//            black(replacement);
//            return;
//        }
//
//        Node<E> parent = node.parent;
//        // 删除的是根节点
//        if (parent == null) return;
//
//        // 删除的是黑色子结点
//        // 判断被删除的node是左和右
//        boolean left = parent.left == null || node.isLeftChild();
//        Node<E> sibling = left? parent.right:parent.left;
//        if (left) { // 被删除的节点在左边，兄弟节点在右边
//            if (isRed(sibling)) { // 兄弟节点是红色
//                black(sibling);
//                red(parent);
//                rotateLeft(parent);
//                // 更换兄弟
//                sibling = parent.right;
//            }
//
//            // 兄弟节点必然是黑色
//            if (isBlack(sibling.left) && isBlack(sibling.right)) {
//                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
//                boolean parentBlack = isBlack(parent);
//                black(parent);
//                red(sibling);
//                if (parentBlack) {
//                    afterRemove(parent,null);
//                }
//            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
//                // 兄弟节点的左边是黑色，兄弟要先旋转
//                if (isBlack(sibling.right)) {
//                    rotateRight(sibling);
//                    sibling = parent.right;
//                }
//
//                color(sibling, colorOf(parent));
//                black(sibling.right);
//                black(parent);
//                rotateLeft(parent);
//            }
//        }else { // 被删除的节点在右边，兄弟节点在左边
//            if (isRed(sibling)) { // 兄弟节点是红色
//                black(sibling);
//                red(parent);
//                rotateRight(parent);
//                // 更换兄弟
//                sibling = parent.left;
//            }
//
//            // 兄弟节点必然是黑色
//            if (isBlack(sibling.left) && isBlack(sibling.right)) {
//                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
//                boolean parentBlack = isBlack(parent);
//                black(parent);
//                red(sibling);
//                if (parentBlack) {
//                    afterRemove(parent,null);
//                }
//            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
//                // 兄弟节点的左边是黑色，兄弟要先旋转
//                if (isBlack(sibling.left)) {
//                    rotateLeft(sibling);
//                    sibling = parent.left;
//                }
//
//                color(sibling, colorOf(parent));
//                black(sibling.left);
//                black(parent);
//                rotateRight(parent);
//            }
//        }
//
//    }

    private static class RBNode<E> extends Node<E>{
        boolean color;

        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
            String str = "";
            if (color == RED) {
                str = "R_";
            }
            return str + element.toString();
        }
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<>(element, parent);
    }

    //****************辅助方法******************************
    // 染色
    private Node<E> color(Node<E> node, boolean color) {
        if (node == null) return node;
        ((RBNode<E>)node).color = color;
        return node;
    }
    // 将该节点染为红色
    private Node<E> red(Node<E> node) {
        return color(node, RED);
    }
    // 将该节点染为黑色
    private Node<E> black(Node<E> node) {
        return color(node, BLACK);
    }
    // 返回该节点的颜色
    private boolean colorOf(Node<E> node) {
        return node == null ? BLACK : ((RBNode<E>)node).color;
    }
    // 该节点是否为黑色
    private boolean isBlack(Node<E> node) {
        return colorOf(node) == BLACK;
    }
    // 该节点是否为红色
    private boolean isRed(Node<E> node) {
        return colorOf(node) == RED;
    }
}
