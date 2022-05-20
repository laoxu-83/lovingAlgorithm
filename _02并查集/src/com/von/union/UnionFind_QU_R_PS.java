package com.von.union;
/**
 * Quick Union - 基于rank的优化 - 路径分裂(Path Spliting)
 * @author yusael
 */
public class UnionFind_QU_R_PS extends UnionFind_QU_R {

	public UnionFind_QU_R_PS(int capacity) {
		super(capacity);
	}
	
	public int find(int v){
		rangeCheck(v);
		while(v != parents[v]){
			//记录原父节点
			int p = parents[v];
			//该节点指向祖父节点
			parents[v] = parents[parents[v]];
			//v指向原父节点
			v = p;
		}
		return parents[v];
	}
	
}
