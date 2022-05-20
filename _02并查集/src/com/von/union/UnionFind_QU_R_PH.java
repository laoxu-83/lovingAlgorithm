package com.von.union;
/**
 * Quick Union - 基于rank的优化 - 路径减半(Path Halving)
 * @author yusael
 */
public class UnionFind_QU_R_PH extends UnionFind_QU_R {

	public UnionFind_QU_R_PH(int capacity) {
		super(capacity);
	}
	
	public int find(int v){
		rangeCheck(v);
		while(v != parents[v]){
			//该节点指向祖父节点
			parents[v] = parents[parents[v]];
			//v指向祖父节点（跳过了原父节点)
			v = parents[v];
		}
		return v;
	}
	
}
