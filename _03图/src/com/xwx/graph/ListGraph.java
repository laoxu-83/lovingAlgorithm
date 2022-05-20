package com.xwx.graph;





import com.xwx.MinHeap;
import com.xwx.UnionFind;

import java.util.*;


/**
 * @author xwx
 * @create 2020/6/16 9:08
 **/
public class ListGraph<V, E> extends Graph<V, E> {
    public ListGraph() {
    }

    public ListGraph(WeightManager<E> weightManager) {
        super(weightManager);
    }

    // 传入的V与顶点类Vertex的映射
    private Map<V, Vertex<V, E>> vertices = new HashMap<>();
    // 边的Set集合
    private Set<Edge<V, E>> edges = new HashSet<>();
    // 边的比较器（通过权重比较）
    private Comparator<Edge<V, E>> edgeComparator = (Edge<V, E> e1, Edge<V, E> e2) -> {
        return weightManager.compare(e1.weight, e2.weight);
    };

    @Override
    public int edgesSize() {
        return edges.size();
    }

    @Override
    public int verticesSize() {
        return vertices.size();
    }

    @Override
    public void addVertex(V v) {
        if(vertices.containsKey(v)) return;
        vertices.put(v,new Vertex<>(v));
    }

    @Override
    public void addEdge(V from, V to) {
        addEdge(from,to,null);
    }

    @Override
    public void addEdge(V from, V to, E weight) {
        // 根据传入的参数from找到起点,如果不存在则创建
        Vertex<V,E> fromVertex = vertices.get(from);
        if(fromVertex == null){
            fromVertex = new Vertex<>(from);
            vertices.put(from,fromVertex);
        }
        // 根据传入的参数to找到终点,如果不存在则创建
        Vertex<V,E> toVertex = vertices.get(to);
        if(toVertex == null){
            toVertex = new Vertex<>(to);
            vertices.put(to,toVertex);
        }

        // 根据出发点与终点,创建边
        Edge<V,E> edge = new Edge<>(fromVertex,toVertex);
        edge.weight = weight; // 有权值则加上权值,无权值则为null

        // 不管原来是否存在,都先删除此边,再添加进去
        if(fromVertex.outEdges.remove(edge)){  //如果有edge边，会返回true
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }
        fromVertex.outEdges.add(edge);
        toVertex.outEdges.add(edge);
        edges.add(edge);
    }

    @Override
    public void removeVertex(V v) {
        // 根据传入的值找到点并删除,不存在则不做操作
        Vertex<V,E> vertex = vertices.remove(v);
        if(vertex == null) return;

        //删除v顶点所有的出边和入边

        //一定要避免一边遍历一边删除，使用迭代器
//        vertex.outEdges.forEach((Edge<V,E> edge) ->{
//            removeEdge(edge.from.value,edge.to.value);
//        });
//        vertex.inEdges.forEach((Edge<V,E> edge) ->{
//            removeEdge(edge.from.value,edge.to.value);
//        });
        //v的出边

        for(Iterator<Edge<V, E>> iterator = vertex.outEdges.iterator(); iterator.hasNext();){
            Edge<V,E> edge = iterator.next();
            //找到该边的终点，该边是终点的入边，删除
            edge.to.inEdges.remove(edge);
            //将当前遍历到的元素edge从集合vertex.outEdges中删除
            iterator.remove();
            edges.remove(edge);
        }
        //v的入边

        for(Iterator<Edge<V, E>> iterator = vertex.inEdges.iterator(); iterator.hasNext();){
            Edge<V,E> edge = iterator.next();
            //找到该边的起点，该边是起点的出边，删除
            edge.from.outEdges.remove(edge);
            //将当前遍历到的元素edge从集合vertex.outEdges中删除
            iterator.remove();
            edges.remove(edge);
        }
    }

    @Override
    public void removeEdge(V from, V to) {
        // 根据传入的from获得起点,不存在则不需要删除
        Vertex<V, E> fromVertex = vertices.get(from);
        if (fromVertex == null) return;
        // 根据传入的to找到终点,不存在则不需要删除
        Vertex<V, E> toVertex = vertices.get(to);
        if (toVertex == null) return;

        // 根据起点和终点获得边,然后删除
        Edge<V, E> edge = new Edge<>(fromVertex, toVertex);
        if (fromVertex.outEdges.remove(edge)) { //如果有edge边，会返回true
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }
    }

    @Override
    public void bfs(V begin,VertexVisitor<V> visitor) {
        if (visitor == null)
            return;

        Vertex<V,E> beginVertex = vertices.get(begin);
        if(beginVertex == null) return;

        Set<Vertex<V,E>> visitedVertex = new HashSet<>();
        Queue<Vertex<V,E>> queue = new LinkedList<>();
        queue.offer(beginVertex);
        visitedVertex.add(beginVertex);
        
        while(!queue.isEmpty()){
            //取出队列中的顶点
            Vertex<V, E> vertex = queue.poll();
            if (visitor.visit(vertex.value))  return;

            // 遍历[队列中取出的顶点]的出去的边,将[这些边的终点]入队,并且标记为已经访问过
            for(Edge edge:vertex.outEdges){
                //如果该边的终点已经访问过，则跳过去
                if(visitedVertex.contains(edge.to)) continue;
                //将终点入队
                queue.offer(edge.to);
                //将新加入的顶点加入HashSet中，不会再重复入队
                visitedVertex.add(edge.to);
            }

        }

    }


    public void dfs(V begin,VertexVisitor<V> visitor) {
        if (visitor == null)   return;
        Vertex<V,E> beginVertex = new Vertex<>(begin);
        if(beginVertex==null) return;

        Set<Vertex<V,E>> visitedVertices = new HashSet<>();
        Stack<Vertex<V,E>> stack = new Stack<>();

        stack.push(beginVertex);
        visitedVertices.add(beginVertex);
        if (visitor.visit(begin))  return;

        while (!stack.isEmpty()){
            //弹出一个顶点
            Vertex<V,E> vertex = stack.pop();

            //1.从outEdges中选择一条边
            for(Edge<V,E> edge:vertex.outEdges){
                stack.push(edge.from);
                stack.push(edge.to);
                visitedVertices.add(edge.to);
                if (visitor.visit(edge.to.value))   return;
                break;
            }
        }
        //2.将选择边的from, to按顺序入栈
        //3.访问选择的to结点
        //4.将to加入已经访问的范围中
        //5.break
    }
    /**
     * 递归实现
     * @param begin
     */
    public void dfs2(V begin) {
        //根据传入的值找到顶点
        Vertex<V,E> beginVertex = vertices.get(begin);
        //顶点不存在，不操作
        if(beginVertex == null) return;
        //传入的集合，用来记录访问过的值
        dfs2(beginVertex,new HashSet<>());
    }

    public void dfs2(Vertex<V,E> vertex, Set<Vertex<V,E>> visitedVertex){
        System.out.println(vertex.value);
        visitedVertex.add(vertex);
        for(Edge<V,E> edge :vertex.outEdges){
            if(visitedVertex.contains(edge.to)) continue;
            dfs2(edge.to,visitedVertex);
        }
    }

    @Override
    public List<V> topologicalSort() {
        List<V> list = new ArrayList<>();  //存放排序后的结果
        Queue<Vertex<V,E>> queue = new LinkedList<>(); //用来度为0的节点的出入队
        Map<Vertex<V,E>, Integer> ins = new HashMap<>();  //存放每个节点入度

        //初始化，（将度为0的节点都放入队列）
        vertices.forEach((V v, Vertex<V,E> vertex)->{
            int in = vertex.inEdges.size();  //每个节点的入度
            if (in==0){
                queue.offer(vertex);
            } else {
                ins.put(vertex,in);
            }
        });

        while(!queue.isEmpty()){
            Vertex<V, E> vertex = queue.poll();
            list.add(vertex.value); // 放入返回结果中

            for (Edge<V,E> edge:vertex.outEdges){
                // 队列中取出节点所通向节点的入度
                int toIndegree = ins.get(edge.to)-1;
                if (toIndegree==0){  // 入度为0，放入队列
                    queue.offer(edge.to);
                } else {  // 入度不为0，用map记录它的入度
                    ins.put(edge.to,toIndegree);
                }
            }
        }
        return list;
    }

    @Override
    public Set<EdgeInfo<V, E>> mst() {
        return Math.random() > 0.5 ? prim() : kruskal();
    }

    @Override
    public Map<V, PathInfo<V, E>> shortestPath(V begin){
        return dijkstra(begin);
    }

    private Map<V, PathInfo<V, E>> bellmanFord(V begin) {
        Vertex<V, E> beginVertex = vertices.get(begin); // 源点
        if(beginVertex == null) return null;

        // 存放当前最短路径信息（不断的进行松弛操作, 会变成最终的最短路径）
        Map<V, PathInfo<V, E>> selectedPaths = new HashMap<>();
        // 初始化源点的最短路径信息
        selectedPaths.put(begin, new PathInfo<>(weightManager.zero()));

        int count = vertices.size() - 1; // 进行 V -1 次松弛操作, 必然能找到最短路径
        for (int i = 0; i < count; i++) {
            for (Edge<V, E> edge : edges) { // 对所有边进行松弛操作
                // 获取该边的始点的最短路径信息, 用于后面进行松弛操作
                PathInfo<V, E> fromPath = selectedPaths.get(edge.from.value);
                // 如果该点的始点没有最短路径信息,松弛必然失败,直接进入下一轮
                if(fromPath == null) continue;
                relax(edge, fromPath, selectedPaths); // 松弛操作
            }
        }

        // 检测负权环, 前面已经松弛了V-1次,这里如果松弛第 V 次仍然可以成功, 说明有负权环
        for (Edge<V, E> edge : edges) {
            PathInfo<V, E> fromPath = selectedPaths.get(edge.from.value);
            if(fromPath == null) continue;
            if(relax(edge, fromPath, selectedPaths)) {
                System.out.println("有负权环, 不存在最短路径");
                return null;
            }
        }

        selectedPaths.remove(begin); // 从最短路径中移除源点的最短路径信息
        return selectedPaths;
    }

    /**
     * 松弛
     * @param edge 需要进行松弛的边
     * @param fromPath edge的from的最短路径信息
     * @param paths 存放着其他的最短路径信息
     */
    private boolean relax(Edge<V, E> edge, PathInfo<V, E> fromPath, Map<V, PathInfo<V, E>> paths) {
        // 新的可选择的最短路径: beginVertex到edge.from的最短路径 + edge.weight
        E newWeight = weightManager.add(fromPath.weight, edge.weight);
        // 以前的最短路径: beginVertex到edge.to的最短路径
        PathInfo<V, E> oldPath = paths.get(edge.to.value);
        if(oldPath != null && weightManager.compare(newWeight, oldPath.weight) >= 0) return false;
        if(oldPath == null) { // 新创建的边
            oldPath = new PathInfo<>();
            paths.put(edge.to.value, oldPath);
        } else { // 以前就存在的边
            oldPath.edgeInfos.clear();
        }
        oldPath.weight = newWeight;
        oldPath.edgeInfos.addAll(fromPath.edgeInfos);
        oldPath.edgeInfos.add(edge.info());
        return true;
    }

    public Map<V, PathInfo<V, E>> dijkstra(V begin) {
        Vertex<V,E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return null;

        Map<V,PathInfo<V, E>> selectedPaths = new HashMap<>(); //存放结果集
        Map<Vertex<V,E>,PathInfo<V, E>> paths = new HashMap<>();  //当前最短路径，用来松弛
        paths.put(beginVertex, new PathInfo<>(weightManager.zero())); //起点就是A
        // 初始化paths
//        for(Edge<V,E> edge : beginVertex.outEdges){
//            PathInfo<V, E> path = new PathInfo<>();
//            path.weight = edge.weight;
//            path.edgeInfos.add(edge.info());
//            paths.put(edge.to,path);
//        }

        while(!paths.isEmpty()){
            // 挑选出当前最短路径中最短的点
            Map.Entry<Vertex<V, E>, PathInfo<V, E>> minEntry = getMinPath(paths);
            // minVertex 离开桌面，被确定为最终的最短路径
            Vertex<V, E> minVertex = minEntry.getKey();
            PathInfo<V,E> minPath = minEntry.getValue();
            selectedPaths.put(minVertex.value, minPath); // 放入最终的最短路径
            paths.remove(minVertex); // 从当前的最短路径中移除

            // 对它的minVertex的outEdges进行松弛操作
            for(Edge<V, E> edge : minVertex.outEdges){
                // 如果edge.to已经离开桌面（或者edge.to是源点），就没必要进行松弛操作
                if(selectedPaths.containsKey(edge.to.value)) continue;
                relaxForDijkstra(edge,minPath,paths);
            }
        }
        selectedPaths.remove(begin);
        return selectedPaths;
    }

    /**
     * 松弛
     * @param edge 需要进行松弛的边
     * @param fromPath edge的from的最短路径信息
     * @param paths 存放着其他（对于dijkstra来说, 就是还没有离开桌面的点）的最短路径信息
     */
    private void relaxForDijkstra(Edge<V, E> edge, PathInfo<V, E> fromPath, Map<Vertex<V, E>, PathInfo<V, E>> paths){
        // 新的可选择的最短路径：beginVertex到edge.from的最短路径 + edge.weight
        E newWeight = weightManager.add(fromPath.weight, edge.weight);
        // 以前的最短路径：beginVertex到edge.to的最短路径
        PathInfo<V, E> oldPath = paths.get(edge.to);
        if(oldPath == null || weightManager.compare(newWeight, oldPath.weight) >= 0) return;


        if(oldPath == null) { // 新创建的边
            oldPath = new PathInfo<>();
            paths.put(edge.to, oldPath);
        } else { // 以前就存在的边
            oldPath.edgeInfos.clear();
        }
        oldPath.weight = newWeight;
        oldPath.edgeInfos.addAll(fromPath.edgeInfos);
        oldPath.edgeInfos.add(edge.info());

    }

//    public Map<V, E> shortestPath(V begin) {
//        Vertex<V,E> beginVertex = vertices.get(begin);
//        if (beginVertex == null) return null;
//
//        Map<V,E> selectedPaths = new HashMap<>(); //存放结果集
//        Map<Vertex<V,E>,E> paths = new HashMap<>();  //当前最短路径，用来松弛
//        // 初始化paths
//        for(Edge<V, E> edge : beginVertex.outEdges){
//            paths.put(edge.to, edge.weight);
//        }
//
//        while(!paths.isEmpty()){
//            // 挑选出当前最短路径中最短的点
//            Map.Entry<Vertex<V, E>, E> minEntry = getMinPath(paths);
//            // minVertex 离开桌面，被确定为最终的最短路径
//            Vertex<V, E> minVertex = minEntry.getKey();
//            selectedPaths.put(minVertex.value, minEntry.getValue()); // 放入最终的最短路径
//            paths.remove(minVertex); // 从当前的最短路径中移除
//            // 对它的minVertex的outEdges进行松弛操作
//            for(Edge<V, E> edge : minVertex.outEdges){
//                // 如果edge.to已经离开桌面（或者edge.to是源点），就没必要进行松弛操作
//                if(selectedPaths.containsKey(edge.to.value) || edge.to.equals(beginVertex)) continue;
//                // 新的可选择的最短路径：beginVertex到edge.from的最短路径 + edge.weight
//                E newWeight = weightManager.add(minEntry.getValue(), edge.weight);
//                // 以前的最短路径：beginVertex到edge.to的最短路径
//                E oldWeight = paths.get(edge.to);
//                if(oldWeight == null || weightManager.compare(newWeight, oldWeight) < 0) {
//                    paths.put(edge.to, newWeight);
//                }
//            }
//        }
//        return selectedPaths;
//    }

    /**
     * 从path中找到最小的路径
     * @param paths
     * @return
     */
    private Map.Entry<Vertex<V,E>,PathInfo<V, E>> getMinPath(Map<Vertex<V,E>,PathInfo<V, E>> paths){
        Iterator<Map.Entry<Vertex<V,E>,PathInfo<V, E>>> it = paths.entrySet().iterator();
        Map.Entry<Vertex<V,E>,PathInfo<V, E>> minEntry = it.next();
        while(it.hasNext()){
            Map.Entry<Vertex<V,E>,PathInfo<V, E>> entry = it.next();
            if(weightManager.compare(entry.getValue().weight, minEntry.getValue().weight)<0){
                minEntry = entry;
            }
        }
        return minEntry;
    }

    /**
     * 多源最短路径: Floyd
     */
    @Override
    public Map<V, Map<V, PathInfo<V, E>>> shortestPath() {
        // 最终的返回值, 存放着每个顶点, 以及每个顶点到其他顶点的最短路径
        Map<V, Map<V, PathInfo<V, E>>> paths = new HashMap<>();
        // 初始化: 遍历所有顶点, 初始化每个顶点到它的出去的点的最短路径信息
        for (Edge<V, E> edge : edges) {
            // 取出当前边的最短路径信息, 为空则创建一个并赋值
            Map<V, PathInfo<V, E>> map = paths.get(edge.from.value);
            if (map == null) {
                map = new HashMap<>();
                paths.put(edge.from.value, map);
            }

            PathInfo<V, E> pathInfo = new PathInfo<>(edge.weight);
            pathInfo.edgeInfos.add(edge.info());
            map.put(edge.to.value, pathInfo);
        }

        vertices.forEach((V v2, Vertex<V, E> vertex2) -> {
            vertices.forEach((V v1, Vertex<V, E> vertex1) -> {
                vertices.forEach((V v3, Vertex<V, E> vertex3) -> {
                    if (v1.equals(v2) || v2.equals(v3) || v1.equals(v3)) return;

                    // v1 -> v2
                    PathInfo<V, E> path12 = getPathInfo(v1, v2, paths);
                    if (path12 == null) return;

                    // v2 -> v3
                    PathInfo<V, E> path23 = getPathInfo(v2, v3, paths);
                    if (path23 == null) return;

                    // v1 -> v3
                    PathInfo<V, E> path13 = getPathInfo(v1, v3, paths);

                    E newWeight = weightManager.add(path12.weight, path23.weight);
                    if (path13 != null && weightManager.compare(newWeight, path13.weight) >= 0) return;

                    if (path13 == null) {
                        path13 = new PathInfo<>();
                        paths.get(v1).put(v3, path13); // v1 到 v3的最短路径
                    } else {
                        path13.edgeInfos.clear();
                    }
                    // 将v1到v3的路径信息更新为, v1到v2再到v3
                    path13.weight = newWeight;
                    path13.edgeInfos.addAll(path12.edgeInfos);
                    path13.edgeInfos.addAll(path23.edgeInfos);
                });
            });
        });
        return paths;
    }

    private PathInfo<V, E> getPathInfo(V from, V to, Map<V, Map<V, PathInfo<V, E>>> paths) {
        Map<V, PathInfo<V, E>> map = paths.get(from);
        return map == null ? null : map.get(to);
    }

    public Set<EdgeInfo<V, E>> prim(){
        // 最小生成树的顶点数量为: 图的顶点数 1
        int verticesSize = vertices.size();
        //
        Iterator<Vertex<V,E>> iterator = vertices.values().iterator();
        if(!iterator.hasNext()) return null;
        Vertex<V, E> vertex = iterator.next(); // 随机取出一个顶点

        Set<Vertex<V, E>> addedVertices = new HashSet<>(); // 标记已经添加的顶点
        addedVertices.add(vertex);
        //存放最小生成树
        Set<EdgeInfo<V,E>> edgeInfos = new HashSet<>();

        //批量建堆(小顶堆),在堆中切分
        MinHeap<Edge<V,E>> heap = new MinHeap<>(vertex.outEdges,edgeComparator);
        while(!heap.isEmpty() && addedVertices.size()<verticesSize){
            //切分，取出一个权值最小的边，并找到其终点
            Edge<V,E> edge = heap.remove();
            if(addedVertices.contains(edge.to)) continue;
            edgeInfos.add(edge.info());
            addedVertices.add(edge.to);
            //将该终点所有的出边加入heap中，为下次切分做准备
            heap.addAll(edge.to.outEdges);
        }
        return edgeInfos;
    }

    public Set<EdgeInfo<V, E>> kruskal(){
        int edgeSize = vertices.size()-1;
        if (edgeSize == -1) return null;

        Set<EdgeInfo<V,E>> edgeInfos = new HashSet<>(); //存放结果
        //批量建堆(小顶堆),找最小权值边
        MinHeap<Edge<V,E>> heap = new MinHeap<>(edges,edgeComparator);
        UnionFind<Vertex<V,E>> union = new UnionFind<>();

        //遍历vertices Map，将所有顶点元素加入并查集中，以便判断是否闭环
        vertices.forEach((V v, Vertex<V,E> vertex)->{
            union.makeSet(vertex);
        });

        while (!heap.isEmpty() && edgeInfos.size()<edgeSize){
            //找出最小权值边
            Edge<V,E> edge = heap.remove();
            //判断改边是否造成闭合
            if(union.isSame(edge.to,edge.from)) continue;

            //把满足条件的最小权值边信息加入结果集中
            edgeInfos.add(edge.info());
            //将该边两端的点合并
            union.union(edge.to,edge.from);
        }
        return edgeInfos;
    }
    // 打印图的信息
    public void print() {
        System.out.println("【顶点】-------------------");
        vertices.forEach((V v, Vertex<V, E> vertex) -> {
            System.out.println(v);
            System.out.println("out-----------");
            System.out.println(vertex.outEdges);
            System.out.println("int-----------");
            System.out.println(vertex.inEdges);
        });
        System.out.println("【边】-------------------");
        edges.forEach((Edge<V, E> edge) -> {
            System.out.println(edge);
        });
    }

    /**
     * 顶点
     */
    private static class Vertex<V, E> {
        V value;
        //使用set，方便查询顶点对应的边
        Set<Edge<V, E>> inEdges = new HashSet<>(); // 进来的边
        Set<Edge<V, E>> outEdges = new HashSet<>(); // 出去的边

        public Vertex(V value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            return Objects.equals(value, ((Vertex<V, E>) obj).value);
        }

        @Override
        public int hashCode() {
            return value == null ? 0 : value.hashCode();
        }

        @Override
        public String toString() {
            return value == null ? "null" : value.toString();
        }

    }

    /**
     * 边
     */
    private static class Edge<V, E> {
        Vertex<V, E> from; // 出发点
        Vertex<V, E> to; // 到达点
        E weight; // 权值

        Edge(Vertex<V, E> from, Vertex<V, E> to) {
            this.from = from;
            this.to = to;
        }

        EdgeInfo<V, E> info() {
            return new EdgeInfo<>(from.value, to.value, weight);
        }

        @Override
        public boolean equals(Object obj) {
            Edge<V, E> edge = (Edge<V, E>) obj;
            return Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
        }

        @Override
        public int hashCode() {
            return from.hashCode() * 31 + to.hashCode();
        }

        @Override
        public String toString() {
            return "Edge [from=" + from + ", to=" + to + ", weight=" + weight + "]";
        }

    }
}
