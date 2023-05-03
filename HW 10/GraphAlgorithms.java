import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.HashMap;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Nandini Ramakrishnan
 * @userid nramakri6
 * @GTID 903805663
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start is null.");
        } else if (graph == null) {
            throw new IllegalArgumentException("Graph is null.");
        }
        if (!(graph.getAdjList().containsKey(start))) {
            throw new IllegalArgumentException("Start doesn't exist in the graph.");
        }
        List<Vertex<T>> visitedSet = new LinkedList<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        visitedSet.add(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            Vertex<T> v = queue.remove();
            for (VertexDistance<T> w : graph.getAdjList().get(v)) {
                if (!(visitedSet.contains(w.getVertex()))) {
                    queue.add(w.getVertex());
                    visitedSet.add(w.getVertex());
                }
            }
        }
        return (visitedSet);
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start is null.");
        } else if (graph == null) {
            throw new IllegalArgumentException("Graph is null.");
        }
        if (!(graph.getAdjList().containsKey(start))) {
            throw new IllegalArgumentException("Start doesn't exist in the graph.");
        }
        List<Vertex<T>> list = new ArrayList<>();
        Set<Vertex<T>> visitedSet = new HashSet<>();
        dfsHelper(start, graph, visitedSet, list);
        return (list);
    }

    /**
     * Helper method for dfs
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @param visitedSet list if vertices that have been visited already
     * @param list list of vertices in visited order
     */
    private static <T> void dfsHelper(Vertex<T> start, Graph<T> graph,
                                Set<Vertex<T>> visitedSet, List<Vertex<T>> list) {
        visitedSet.add(start);
        list.add(start);
        for (VertexDistance<T> distance : graph.getAdjList().get(start)) {
            if (!visitedSet.contains(distance.getVertex())) {
                dfsHelper(distance.getVertex(), graph, visitedSet, list);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start is null.");
        } else if (graph == null) {
            throw new IllegalArgumentException("Graph is null.");
        }
        if (!(graph.getAdjList().containsKey(start))) {
            throw new IllegalArgumentException("Start doesn't exist in the graph.");
        }
        Set<Vertex<T>> visitedSet = new HashSet<>();
        Map<Vertex<T>, Integer> returnMap = new HashMap<>();
        Queue<VertexDistance<T>> priorityQueue = new PriorityQueue<>();
        for (Vertex<T> v : graph.getVertices()) {
            returnMap.put(v, Integer.MAX_VALUE);
        }
        priorityQueue.add(new VertexDistance<>(start, 0));
        while ((priorityQueue.size() != 0) && !(visitedSet.size() == graph.getVertices().size())) {
            VertexDistance<T> temp = priorityQueue.remove();
            if (!(visitedSet.contains(temp.getVertex()))) {
                visitedSet.add(temp.getVertex());
                returnMap.put(temp.getVertex(), temp.getDistance());
            }
            for (VertexDistance<T> distance : graph.getAdjList().get(temp.getVertex())) {
                if (!(visitedSet.contains(distance.getVertex()))) {
                    priorityQueue.add(new VertexDistance<>(distance.getVertex(),
                        distance.getDistance() + temp.getDistance()));
                }
            }
        }
        return (returnMap);
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use PriorityQueue, java.util.Set, and any class that 
     * implements the aforementioned interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start is null.");
        } else if (graph == null) {
            throw new IllegalArgumentException("Graph is null.");
        }
        if (!(graph.getAdjList().containsKey(start))) {
            throw new IllegalArgumentException("Start doesn't exist in the graph.");
        }
        Queue<Edge<T>> priorityQueue = new PriorityQueue<>();
        Set<Vertex<T>> visitedSet = new HashSet<>();
        Set<Edge<T>> edgeSet = new HashSet<>();
        Map<Vertex<T>, List<VertexDistance<T>>> map = graph.getAdjList();
        for (VertexDistance<T> distance : map.get(start)) {
            priorityQueue.add(new Edge<>(start, distance.getVertex(), distance.getDistance()));
        }
        visitedSet.add(start);
        while ((priorityQueue.size() != 0) && (visitedSet.size() < graph.getVertices().size())) {
            Edge<T> edge = priorityQueue.remove();
            if (!visitedSet.contains(edge.getV())) {
                edgeSet.add(new Edge<>(edge.getU(), edge.getV(), edge.getWeight()));
                edgeSet.add(new Edge<>(edge.getV(), edge.getU(), edge.getWeight()));
                visitedSet.add(edge.getV());
                for (VertexDistance<T> e: map.get(edge.getV())) {
                    if (!visitedSet.contains(e.getVertex())) {
                        priorityQueue.add(new Edge<>(edge.getV(), e.getVertex(), e.getDistance()));
                    }
                }
            }
        }
        if (edgeSet.size() / 2 == graph.getVertices().size() - 1) {
            return (edgeSet);
        }
        return (null);
    }
}
