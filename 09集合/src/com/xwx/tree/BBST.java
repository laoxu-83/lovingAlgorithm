package com.xwx.tree;

import java.util.Comparator;

/**
 * @author xwx
 * @create 2020/6/6 15:10
 **/
public class BBST<E> extends BinarySearchTree<E> {

    public BBST() {
        this(null);
    }

    public BBST(Comparator<E> comparator) {
        super(comparator);
    }

    /**
     * 左旋
     */
    protected void rotateLeft(Node<E> grand) {
        Node<E> parent = grand.right;
        Node<E> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }

    /**
     * 右旋
     */
    protected void rotateRight(Node<E> grand) {
        Node<E> parent = grand.left;
        Node<E> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }

    /**
     * 公共代码：不管是左旋、右旋，都要执行的
     * @param grand 失衡节点
     * @param parent 失衡节点的tallerChild
     * @param child g和p需要交换的子树（本来是p的子树，后来会变成g的子树）
     */
    protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
        // 让parent称为子树的根节点
        parent.parent = grand.parent;
        if (grand.isLeftChild()) { // grand是左子树
            grand.parent.left = parent;
        } else if (grand.isRightChild()) { // grand是右子树
            grand.parent.right = parent;
        } else { // grand是root节点
            root = parent;
        }

        // 更新child的parent
        if (child != null) {
            child.parent = grand;
        }

        // 更新grand的parent
        grand.parent = parent;
    }


}
