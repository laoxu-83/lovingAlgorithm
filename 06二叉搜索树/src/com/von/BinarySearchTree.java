package com.von;

import com.von.printer.BinaryTreeInfo;
import sun.reflect.generics.visitor.Visitor;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**注意强制转换成可比较性，这样就不用在类泛型那里写死了
 * @author xwx
 * @create 2020/6/1 15:01
 **/
public class BinarySearchTree<E> implements BinaryTreeInfo {
    private int size;
    private Node<E> root;
    private Comparator<E> comparator;

    public BinarySearchTree(){
        this(null);
    }
    public BinarySearchTree(Comparator<E> comparator){
        this.comparator = comparator;
    }


//******************************内部类***********************
    private static class Node<E>{
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        public boolean isLeaf(){
            return left==null && right==null;
        }

        public boolean hasTwoChildren(){
            return left!=null && right!=null;
        }
    }

    public static abstract class Visitor<E>
    {
        boolean stop;

        /**
         * 如果返回true，表示停止遍历
         * @param element
         * @return
         */
        abstract boolean visit(E element);
    }

//******************************内部类***********************
    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public void add(E element){
        elementNullCheck(element);
        //添加第一个节点
        if(root==null){
            root = new Node<>(element,null);
            size++;
            return;
        }

        //比大小，找到目标父节点
        Node<E> node = root;
        Node<E> parentNode = null;
        int cmp = 0;
        while(node!=null) {
            cmp = compare(element, node.element);
            parentNode = node;
            if (cmp > 0) {
                //插入的值大，往右找
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                node.element = element;
                return;
            }
        }
        //找到父节点后，插入相应位置
        Node<E> newNode = new Node<>(element,parentNode);
        if(cmp >0){
            parentNode.right = newNode;
        } else if(cmp<0){
            parentNode.left = newNode;
        }
        size++;
    }

    public void clear(){
        size=0;
        root=null;
    }

    public void remove(E element){
        remove(node(element));
    }

    public void remove(Node<E> node){
        if(node==null) return;
        size--;

        // 度为2的节点
        if (node.hasTwoChildren()) {
            // 找到后继节点
            Node<E> s = successor(node);
            // 用后继节点的值覆盖度为2的节点的值
            node.element = s.element;
            // 删除后继节点
            node = s;
        }

        // 删除node节点（node的度必然是1或者0）
        Node<E> replacement = node.left != null ? node.left : node.right;


        if (replacement != null) { // node是度为1的节点
            // 更改parent
            replacement.parent = node.parent;
            // 更改parent的left、right的指向
            if (node.parent == null) { // node是度为1的节点并且是根节点
                root = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else { // node == node.parent.right
                node.parent.right = replacement;
            }
        }
        else if (node.parent == null) { // node是叶子节点并且是根节点
            root = null;
        } else { // node是叶子节点，但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else { // node == node.parent.right
                node.parent.right = null;
            }
        }
    }

    private Node<E> node(E element){
        Node<E> node = root;
        while(node !=null){
            int cmp = compare(element,node.element);
            if(cmp==0) return node;
            if(cmp>0){
                node = node.right;
            } else {
                node = node.right;
            }
        }
        return null;
    }

    public boolean contains(E element){
        return false;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        toString(root,builder,"");
        return builder.toString();
    }

    /**
     * 前序遍历的方式打印
     */
    public void toString(Node<E> node,StringBuilder  builder,String prefix){
        if(node==null) return;
        builder.append(prefix).append(node.element).append("\n");
        toString(node.left,builder,prefix+"L----");
        toString(node.right,builder,prefix+"R----");
    }

//***********************练习**************************
// 递归求高度
public int height1(Node<E> node){
    if(node == null) return 0;
    return 1 + Math.max(height1(node.left), height1(node.right));
}
    public int height1(){
        return height1(root);
    }

    /**
     * 迭代求高度
     * 层序遍历，每一次遍历完高度加1，
     * 通过levelsize计数，判断这层是否遍历完
     * @return
     */
    public int height(){
        if(root == null) return 0;
        int levelSize = 1; // 存储每一层的元素数量
        int height = 0; // 树的高度
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            Node<E> node = queue.poll();
            levelSize--;
            if(node.left != null) {
                queue.offer(node.left);
            }
            if(node.right != null) {
                queue.offer(node.right);
            }
            if(levelSize == 0){ // 即将要访问下一层
                levelSize = queue.size();
                height++;
            }
        }
        return height;
    }


    /**
     * 是否是完全二叉树
     * 层序遍历二叉树（用队列）
     * 如果 node.left!=null，将 node.left 入队
     * 如果 node.left==null && node.right!=null，返回 false
     * 如果 node.right!=null，将 node.right 入队
     * 如果 node.right==null
     * ✓ 那么后面遍历的节点应该都为叶子节点，才是完全二叉树
     * ✓ 否则返回 false
     * 遍历结束，返回 true
     * @return
     */
    public boolean isComplete(){
        if(root==null) return false;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        boolean leaf = false;
        while(!queue.isEmpty()){
            Node<E> node = queue.poll();
            if(leaf && !node.isLeaf()){ // 要求是叶子结点，但是当前节点不是叶子结点
                return false;
            }
            if(node.left != null){
                queue.offer(node.left);
            }else if(node.right != null){
                // node.left==null && node.right!=null
                return false;
            }
            if(node.right != null){
                queue.offer(node.right);
            }else{
                // node.left==null && node.right==null
                // node.left!=null && node.right==null
                leaf = true; // 要求后面都是叶子节点
            }
        }
        return true;
    }

    /**
     * 前驱结点
     * ◼ node.left != null
     * ✓ 终止条件： right 为 null
     * ◼ node.left == null && node.parent != null
     *    node 在 parent 的右子树中
     * ◼ node.left == null && node.parent == null
     *    没有前驱节点
     * @param node
     * @return
     */
    public Node<E> predecessor(Node<E> node){
        if(node == null) return null;
        Node<E> p = node.left;

        if(p!=null){
            while(p.right!=null){
                p = p.right;
            }
            return p;
        }

        while(node.parent!=null && node == node.parent.left){
            node = node.parent;
        }
        return node.parent;
    }

    public Node<E> successor(Node<E> node){
        if(node == null) return null;
        Node<E> p = node.right;

        if(p!=null){
            while(p.left!=null){
                p = p.left;
            }
            return p;
        }

        while(node.parent!=null && node == node.parent.right){
            node = node.parent;
        }
        return node.parent;
    }
//*************************带访问器的遍历************
    public void levelOrder(Visitor<E> visitor){
        if(root == null) return;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            Node<E> node = queue.poll();
            if (visitor.stop) return;
            visitor.stop = visitor.visit(node.element);
            if(node.left!=null){
                queue.offer(node.left);
            }
            if (node.right!=null){
                queue.offer(node.right);
            }
        }
    }
    // 前序遍历
    public void preOrder(Visitor<E> visitor){
        preOrder(root,visitor);
    }

    public void preOrder(Node<E> node, Visitor<E> visitor){
        if(node==null|| visitor.stop) return;

        visitor.stop = visitor.visit(node.element);
        preOrderTraversal(node.left);
        preOrderTraversal(node.right);
    }

    // 中序遍历
    public void inorder(Node<E> node, Visitor visitor){
        if(node == null || visitor.stop ) return;
        inorderTraversal(node.left);

        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
        inorderTraversal(node.right);
    }
    public void inorder(Visitor visitor){
        inorder(root, visitor);
    }
    // 后序遍历
    public void postorder(Node<E> node, Visitor visitor){
        //终止递归调用
        if(node == null || visitor.stop) return;

        postorderTraversal(node.left);
        postorderTraversal(node.right);
        //终止本次调用的访问功能
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
    }

    public void postorder(Visitor visitor){
        postorder(root, visitor);
    }
//*******************************遍历*********************
    // 前序遍历
    public void preOrderTraversal(){
        preOrderTraversal(root);
    }

    public void preOrderTraversal(Node<E> node){
        if(node==null) return;
        System.out.print(node.element+",");
        preOrderTraversal(node.left);
        preOrderTraversal(node.right);
    }

    // 中序遍历
    public void inorderTraversal(Node<E> node){
        if(node == null) return;
        inorderTraversal(node.left);
        System.out.print(node.element + " ");
        inorderTraversal(node.right);
    }
    public void inorderTraversal(){
        inorderTraversal(root);
    }
    // 后序遍历
    public void postorderTraversal(Node<E> node){
        if(node == null) return;
        postorderTraversal(node.left);
        postorderTraversal(node.right);
        System.out.print(node.element + " ");
    }

    public void postorderTraversal(){
        postorderTraversal(root);
    }
    // 层次遍历
    public void levelOrderTraversal(){
        if(root == null) return;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            Node<E> node = queue.poll();
            System.out.println(node.element);
            if(node.left!=null){
                queue.offer(node.left);
            }
            if (node.right!=null){
                queue.offer(node.right);
            }
        }
    }

//**************************************************************

    /**
     * 既可以传比较器
     * 也可以按照默认的比较接口
     * @param e1
     * @param e2
     * @return
     */
    private int compare(E e1, E e2){
        if(comparator != null){
            return comparator.compare(e1,e2);
        }
        return ((Comparable<E>)e1).compareTo(e2);
    }

    private void elementNullCheck(E element){
        if(element==null){
            throw new IllegalArgumentException("element must not be null");
        }

    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>)node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>)node).right;
    }

    @Override
    public Object string(Object node) {
        Node<E> myNode = (Node<E>)node;
        String parentStr = "null";
        if(myNode.parent != null){
            parentStr = myNode.parent.element.toString();
        }
        return myNode.element + "_p(" + parentStr + ")";
    }
}
