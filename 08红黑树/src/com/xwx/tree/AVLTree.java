package com.xwx.tree;

import java.util.Comparator;

/**
 * @author xwx
 * @create 2020/6/3 18:23
 **/
public class AVLTree<E> extends BBST<E> {

    public AVLTree(){}

    public AVLTree(Comparator<E> comparator){
        super(comparator);
    }

    @Override
    protected void afterRemove(Node<E> node) {
        //传进来的结点一定是叶子结点
        //往祖先节点中找失衡结点
        while ((node = node.parent) != null){
            if(isBalanced(node)){
                //更新高度
                updateHeight(node);
            }else{
                //恢复平衡
                rebalance(node);
            }
        }
    }

    protected void afterAdd(Node<E> node){
        //传进来的结点一定是叶子结点
        //往祖先节点中找失衡结点
        while ((node = node.parent) != null){
            if(isBalanced(node)){
                //更新高度
                updateHeight(node);
            }else{
                //恢复平衡
                rebalance(node);
                break; //整棵树恢复平衡
            }
        }
    }

    /**
     *grand是高度最低，失衡的祖先节点
     * 三个结点参与旋转，g,p,n(插入结点)
     * @param grand
     */

//    private void rebalance(Node<E> grand){
//        Node<E> parent = ((AVLNode<E>)grand).tallerChild();
//        Node<E> node = ((AVLNode<E>)parent).tallerChild();
//        if(parent.isLeftChild()){//L
//            if(node.isLeftChild()){//LL
//                rotate(grand, node, node.right, parent, parent.right, grand);
//            }else{//LR
//                rotate(grand, parent, node.left, node, node.right, grand);
//            }
//        }else{//R
//            if(node.isLeftChild()){//RL
//                rotate(grand, grand, node.left, node, node.right, parent);
//            }else{//RR
//                rotate(grand, grand, parent.left, parent, node.left, node);
//            }
//        }
//    }
//    /**
//     * 统一旋转
//     */
//    private void rotate(
//            Node<E> r, // 子树的根节点
//            Node<E> b, Node<E> c,
//            Node<E> d,
//            Node<E> e, Node<E> f) {
//        // 让d成为这颗子树的根结点
//        d.parent = r.parent;
//        if(r.isLeftChild()){
//            r.parent.left = d;
//        }else if(r.isRightChild()){
//            r.parent.right = d;
//        }else{
//            root = d;
//        }
//        // b-c
//        b.right = c;
//        if(c!=null){
//            c.parent = b;
//        }
//        updateHeight(b);
//
//        // e-f
//        f.left = e;
//        if(e != null){
//            e.parent = f;
//        }
//        updateHeight(f);
//
//        // b-d-f
//        d.left = b;
//        d.right = f;
//        b.parent = d;
//        f.parent = d;
//        updateHeight(d);
//    }


    /**
     * rand是高度最低，失衡的祖先节点
     * @param grand
     */
    private void rebalance(Node<E> grand) {
        Node<E> parent = ((AVLNode<E>)grand).tallerChild();
        Node<E> node = ((AVLNode<E>)parent).tallerChild();

        //旋转方向的选择
        if(parent.isLeftChild()){ //L
            if (node.isLeftChild()){ //LL
                rotateRight(grand);
            } else{ //LR
                rotateLeft(parent);
                rotateRight(grand);
            }
        } else{   //R
            if(node.isLeftChild()){ //RL
                rotateRight(parent);
                rotateLeft(grand);
            } else{ //RR
                rotateLeft(grand);
            }

        }
    }

//    /**
//     * 旋转就是将要旋转的结点向下交换
//     * @param grand
//     */
//    protected void rotateLeft(Node<E> grand){
//        Node<E> parent = grand.right;
//        Node<E> child = parent.left;
//        grand.right = child;
//        parent.left = grand;
//
//        afterRotate(grand, parent, child);
//    }
//    /**
//     * 右旋转
//     */
//    protected void rotateRight(Node<E> grand){
//        Node<E> parent = grand.left;
//        Node<E> child = parent.right;
//        grand.left = child;
//        parent.right = grand;
//
//        afterRotate(grand, parent, child);
//    }
    protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child){
        super.afterRotate(grand,parent,child);
        // 更新高度
        updateHeight(grand);
        updateHeight(parent);
    }

    private void updateHeight(Node<E> node) {
        ((AVLNode<E>)node).updateHeight();
    }

    private boolean isBalanced(Node<E> node){
        return Math.abs(((AVLNode<E>)node).balanceFactor())<=1;
    }

    private static class AVLNode<E> extends Node<E>{
        int height=1;

        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        public int balanceFactor(){
            int leftHeight = (left == null)? 0:((AVLNode<E>)left).height;
            int rightHeight = (right == null)? 0:((AVLNode<E>)right).height;
            return leftHeight-rightHeight;
        }

        public void updateHeight(){
            int leftHeight = (left == null)? 0:((AVLNode<E>)left).height;
            int rightHeight = (right == null)? 0:((AVLNode<E>)right).height;
            height = 1 + Math.max(leftHeight,rightHeight);
        }

        public Node<E> tallerChild(){
            int leftHeight = left==null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right==null ? 0 : ((AVLNode<E>)right).height;
            if(leftHeight < rightHeight) return right;
            if(leftHeight > rightHeight) return left;
            // 高度一样则返回同方向的，左子节点则返回左，否则返回右
            return isLeftChild()? left:right;
        }

        @Override
        public String toString() {
            String parentString = "null";
            if(parent != null){
                parentString = parent.element.toString();
            }
            return element + "_p(" + parentString + ")_h(" + height + ")";
        }
    }

    protected   Node<E> createNode(E element, Node<E> parent){
        return new  AVLNode(element, parent);
    }
}
