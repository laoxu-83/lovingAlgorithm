package com.von.map;

import com.von.printer.BinaryTreeInfo;
import com.von.printer.BinaryTrees;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;



/**
 * @author xwx
 * @create 2020/6/8 20:57
 **/
public class HashMap<K,V> implements Map<K,V> {
    private  int size;
    private Node<K, V>[] table;  //存放每一颗红黑树的根结点
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private static final int DEFAULT_CAPACITY = 1 << 4;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public HashMap(){
        table = new Node[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public void clear() {
        if (size == 0) return;
        size = 0;
        for (int i=0;i<table.length;i++){
            table[i] = null;
        }

    }

    @Override
    public V put(K key, V value) {
        int index = index(key);
        //取出index位置的红黑树结点
        Node<K,V> root = table[index];
        if (root==null){
            root = createNode(key,value,null);
            table[index] = root;
            size++;
            fixAfterPut(root);
            return null;
        }

        //添加新结点到红黑树
        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp = 0;
        K k1 = key;
        int h1 = hash(k1);
        Node<K, V> result = null;
        boolean searched = false; // 是否已经搜索过这个key
        do {
            parent = node;
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2) {
                cmp = 1;
            } else if (h1 < h2) {
                cmp = -1;
            } else if (Objects.equals(k1, k2)) {
                //进入这里才表明两者是同一个对象
                cmp = 0;
            } else if (k1 != null && k2 != null
                    && k1 instanceof Comparable
                    && k1.getClass() == k2.getClass()
                    && (cmp = ((Comparable)k1).compareTo(k2)) != 0) {
                //compareTo不等于0，不做任何事。等于0时，进行扫描
            } else if (searched) { // 已经扫描了
                //利用内存地址算出来的hashcode
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            } else {
                // searched == false; 还没有扫描，然后再根据内存地址大小决定左右
                //左边不等于空，并且在左边找到了 || 右边不等于空，右边找到了
                if ((node.left != null && (result = node(node.left, k1)) != null)
                        || (node.right != null && (result = node(node.right, k1)) != null)) {
                    // 已经存在这个key
                    node = result;
                    cmp = 0;
                } else { // 不存在这个key
                    searched = true;
                    cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
                }
            }

            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else { // 相等
                V oldValue = node.value;
                node.key = key;
                node.value = value;
                node.hash = h1;
                return oldValue;
            }
        } while (node != null);

        // 看看插入到父节点的哪个位置
        Node<K, V> newNode = createNode(key, value, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;

        // 新添加节点之后的处理
        fixAfterPut(newNode);
        return null;


    }

    /**
     * 根据key找结点
     * @param key
     * @return
     */
    private Node<K,V> node(K key){
      Node<K,V> root = table[index(key)];
      return root == null ? null : node(root,key);
    }

    /**
     * 在根结点为node的树中，找寻key为k1的结点
     * @param node 根结点
     * @param k1
     * @return
     */
    private Node<K,V> node(Node<K,V> node,K k1){
        int h1 = (k1 == null) ? 0:k1.hashCode();
        // 存储查找结果
        Node<K, V> result = null;
        int cmp = 0;

        while (node != null){
            K k2 = node.key;
            int h2 = node.hash;
            //比较哈希值
            //**************哈希值不同***************
            if (h1>h2){
                node = node.right;
            } else if (h1<h2){
                node = node.left;
            }
            //**********哈希值相等******************
            else if (Objects.equals(k1, k2)) {
                return node;
            }
            //-----------哈希值相等，同一类并且具有可比性--------
            else if (k1 != null && k2 != null
                    && k1 instanceof Comparable
                    && k1.getClass() == k2.getClass()
                    && (cmp = ((Comparable)k1).compareTo(k2)) != 0) {
                //cmp不等于0，进行下列操作
                node = (cmp > 0) ? node.right : node.left;
            }
//            else if (k1 != null && k2 != null
//                    && k1 instanceof Comparable
//                    && k1.getClass() == k2.getClass()){
//                 cmp = ((Comparable)k1).compareTo(k2);
//                 if (cmp>0){
//                     node = node.right;
//                 } else if(cmp<0){
//                     node = node.left;
//                 } else {
            //不能将compareTo 为0的情况当做相同
//                     return node;
//                 }
//            }
            //------------同一种类型，并且不具备可比性,扫描---------
            else if (node.right != null && (result = node(node.right, k1)) != null) {
                return result;
            } else {
                node = node.left;
            }
//            } else if (node.left != null && (result = node(node.left, k1)) != null){ // 只能往左边找
//                return result;
//            } else {
//                return null;
//            }
        }
        return null;
    }

    //计算索引
    private int index(K key){
        return hash(key) & (table.length-1);
    }

    private int index(Node<K,V> node){
        K key = node.key;
        return index(key);
    }

    private int hash(K key){
        if (key == null) return 0;
        int hash = key.hashCode();
        return hash ^ (hash >>> 16);
    }

    private int compare(K k1, K k2,int h1, int h2){
        //**************哈希值不同***************
        int result = h1 - h2;
        if (result != 0) return result;

        //**********哈希值相等******************
        //equals
        if(Objects.equals(k1,k2)) return 0;

        //**********哈希值相等，但是不equals****************
        if (k1 != null && k2 != null){
            //比较类名
            String k1Cls = k1.getClass().getName();
            String k2Cls = k2.getClass().getName();
            result = k1Cls.compareTo(k2Cls);
            if(result != 0) return result;  //不同类

            //同一类并且具有可比性
            if (k1 instanceof Comparable){
                return ((Comparable) k1).compareTo(k2);
            }
        }

        //同一种类型，哈希值相等并且不具备可比性
        return System.identityHashCode(k1) - System.identityHashCode(k2);
    }

    @Override
    public V get(K key) {
        Node<K,V> node = node(key);
        return node != null ? node.value:null;
    }

    @Override
    public V remove(K key){
        return remove(node(key));
    }

    private V remove(Node<K,V> node) {
        if (node == null) return null;

        Node<K, V> willNode = node;

        size--;

        V oldValue = node.value;
        //*************度为2的节点*****************
        if (node.hasTwoChildren()) {
            // 找到后继节点
            Node<K, V> s = successor(node);
            // 用后继节点的值覆盖度为2的节点的值
            node.key = s.key;
            node.value = s.value;
            node.hash = s.hash;
            // 删除后继节点
            node = s;
        }

        //*****************node的度必然是1或者0**************
        Node<K, V> replacement = node.left != null ? node.left : node.right;
        int index = index(node);

        if (replacement != null) { // node是度为1的节点
            // 更改parent
            replacement.parent = node.parent;
            // 更改parent的left、right的指向
            if (node.parent == null) { // node是度为1的节点并且是根节点
                table[index] = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else { // node == node.parent.right
                node.parent.right = replacement;
            }

            // 删除节点之后的处理
            fixAfterRemove(replacement);
        } else if (node.parent == null) { // node是叶子节点并且是根节点
            table[index] = null;
        } else { // node是叶子节点，但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else { // node == node.parent.right
                node.parent.right = null;
            }

            // 删除节点之后的处理
            fixAfterRemove(node);
        }

        // 交给子类去处理
        afterRemove(willNode, node);

        return oldValue;
    }
    private Node<K, V> successor(Node<K, V> node) {
        if(node == null) return null;

        if (node.right != null){
            Node<K,V> p = node.right;
            while (p!=null){
                p = p.left;
            }
            return p;
        }

        while (node.parent == null && node.parent.right==node){
            node = node.parent;
        }

        return node.parent;
    }
    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if(size == 0) return false;
        Queue<Node<K,V>> queue = new LinkedList<>();
        for (int i=0;i<size;i++){
            //table[i]可能为空，此时要跳过
            if(table[i]==null) continue;

            //层序遍历
            queue.offer(table[i]);
            while (!queue.isEmpty()){
                Node<K,V> node = queue.poll();
                if (Objects.equals(value,node.value)) return true;
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }

        return false;
    }

    @Override
    public void traversal(Visitor visitor) {
        if(size ==0 || visitor == null) return;

        Queue<Node<K,V>> queue = new LinkedList<>();
        for (int i =0;i<size;i++){
            if (table[i] == null) continue;
            queue.offer(table[i]);
            while (!queue.isEmpty()){
                Node<K,V> node = queue.poll();
                if(visitor.visit(node.key,node.value)) return;
                if (node.left != null){
                    queue.offer(node.left);
                }
                if (node.right !=null ){
                    queue.offer(node.right);
                }
            }
        }
    }

    public void print() {
        if (size == 0) return;
        for (int i = 0; i < table.length; i++) {
            final Node<K, V> root = table[i];
            System.out.println("【index = " + i + "】");
            BinaryTrees.println(new BinaryTreeInfo() {
                @Override
                public Object string(Object node) {
                    return node;
                }

                @Override
                public Object root() {
                    return root;
                }

                @Override
                public Object right(Object node) {
                    return ((Node<K, V>)node).right;
                }

                @Override
                public Object left(Object node) {
                    return ((Node<K, V>)node).left;
                }
            });
            System.out.println("---------------------------------------------------");
        }
    }

    protected static class Node<K, V> {
        int hash;
        K key;
        V value;
        boolean color = RED;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;
        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            int hash = key == null ? 0 : key.hashCode();
            this.hash = hash ^ (hash >>> 16);
            this.value = value;
            this.parent = parent;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        public Node<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }

            if (isRightChild()) {
                return parent.left;
            }

            return null;
        }

        @Override
        public String toString() {
            return "Node_" + key + "_" + value;
        }
    }


    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        return new Node<>(key, value, parent);
    }

    /**
     *
     * @param willNode  本来想删的节点
     * @param removedNode 真正删除的节点
     */
    protected void afterRemove(Node<K, V> willNode, Node<K, V> removedNode) { }

    private void resize(){
        //装填因子<=0.75
        if (size / table.length <= DEFAULT_LOAD_FACTOR) return;

        Node<K, V>[] oldTable = table;
        table = new Node[oldTable.length >> 1]; //扩容2倍
        //扩容两倍后，节点索引有两种情况 1.不变 2. index = index + 旧容量

        //层序遍历，挪动节点
        Queue<Node<K,V>> queue = new LinkedList<>();
        for(int i=0;i<oldTable.length;i++){
            if(oldTable[i] == null) continue;
            queue.offer(oldTable[i]);
            while (!queue.isEmpty()){
                Node<K,V> node = queue.poll();

                if(node.left!=null){
                    queue.offer(node.left);
                } if (node.right!=null){
                    queue.offer(node.right);
                }
                moveNode(node);
            }

        }
    }

    private void moveNode(Node<K,V> newNode){
        // 重置
        newNode.parent = null;
        newNode.left = null;
        newNode.right = null;
        newNode.color = RED; //******新结点****

        int index = index(newNode);
        // 取出index位置的红黑树根节点
        Node<K, V> root = table[index];
        if (root == null) {
            root = newNode;
            table[index] = root;
            fixAfterPut(root);
            return;
        }

        // 添加新的节点到红黑树上面
        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp = 0;
        K k1 = newNode.key;
        int h1 = newNode.hash;
        //************不会出现equals的情况*********
        do {
            parent = node;
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2) {
                cmp = 1;
            } else if (h1 < h2) {
                cmp = -1;
            } else if (k1 != null && k2 != null
                    && k1 instanceof Comparable
                    && k1.getClass() == k2.getClass()
                    && (cmp = ((Comparable)k1).compareTo(k2)) != 0) {
            } else {
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            }

            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            }
        } while (node != null);

        // 看看插入到父节点的哪个位置
        newNode.parent = parent;
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }

        // 新添加节点之后的处理
        fixAfterPut(newNode);
    }
    private void fixAfterRemove(Node<K, V> node) {
        // 如果删除的节点是红色
        // 或者 用以取代删除节点的子节点是红色
        if (isRed(node)) {
            black(node);
            return;
        }

        Node<K, V> parent = node.parent;
        if (parent == null) return;

        // 删除的是黑色叶子节点【下溢】
        // 判断被删除的node是左还是右
        boolean left = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = left ? parent.right : parent.left;
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
                    fixAfterRemove(parent);
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
        } else { // 被删除的节点在右边，兄弟节点在左边
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
                    fixAfterRemove(parent);
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

    private void fixAfterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;

        // 添加的是根节点 或者 上溢到达了根节点
        if (parent == null) {
            black(node);
            return;
        }

        // 如果父节点是黑色，直接返回
        if (isBlack(parent)) return;

        // 叔父节点
        Node<K, V> uncle = parent.sibling();
        // 祖父节点
        Node<K, V> grand = red(parent.parent);
        if (isRed(uncle)) { // 叔父节点是红色【B树节点上溢】
            black(parent);
            black(uncle);
            // 把祖父节点当做是新添加的节点
            fixAfterPut(grand);
            return;
        }

        // 叔父节点不是红色
        if (parent.isLeftChild()) { // L
            if (node.isLeftChild()) { // LL
                black(parent);
            } else { // LR
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        } else { // R
            if (node.isLeftChild()) { // RL
                black(node);
                rotateRight(parent);
            } else { // RR
                black(parent);
            }
            rotateLeft(grand);
        }
    }

    private void rotateLeft(Node<K, V> grand) {
        Node<K, V> parent = grand.right;
        Node<K, V> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }

    private void rotateRight(Node<K, V> grand) {
        Node<K, V> parent = grand.left;
        Node<K, V> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }

    private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
        // 让parent称为子树的根节点
        parent.parent = grand.parent;
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else { // grand是root节点
            table[index(grand)] = parent;
        }

        // 更新child的parent
        if (child != null) {
            child.parent = grand;
        }

        // 更新grand的parent
        grand.parent = parent;
    }

    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null) return node;
        node.color = color;
        return node;
    }

    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }

    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }
}
