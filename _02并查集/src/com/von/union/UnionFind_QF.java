package com.von.union;

/**
 * @author xwx
 * @create 2020/6/15 14:19
 **/
public class UnionFind_QF extends UnionFind {
    public UnionFind_QF(int capacity) {
        super(capacity);
    }

    /**
     * 父节点就是根节点
     * @param v
     * @return
     */
    @Override
    public int find(int v) {
        rangeCheck(v);

        return parents[v];
    }
    /**
     *  将v1所在集合的所有元素都嫁接到v2的父节点上
     */
    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);

        for(int i = 0;i<parents.length;i++){
            if(parents[i]==p1){
                parents[i] = p2;
            }
        }
    }
}
