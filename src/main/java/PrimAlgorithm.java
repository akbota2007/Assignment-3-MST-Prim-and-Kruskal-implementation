import java.util.*;

public class PrimAlgorithm {

    public static class MSTResult {
        List<Graph.Edge> mstEdges;
        int totalCost;
        int operationsCount;
        double executionTimeMs;

        public MSTResult(List<Graph.Edge> mstEdges, int totalCost, int operationsCount, double executionTimeMs) {
            this.mstEdges = mstEdges;
            this.totalCost = totalCost;
            this.operationsCount = operationsCount;
            this.executionTimeMs = executionTimeMs;
        }
    }

    public static MSTResult findMST(Graph graph) {
        long startTime = System.nanoTime();
        int operationsCount = 0;

        List<String> nodes = graph.getNodes();
        List<Graph.Edge> edges = graph.getEdges();

        // Построение adjacency list
        Map<String, List<Graph.Edge>> adjList = new HashMap<>();
        for (String node : nodes) {
            adjList.put(node, new ArrayList<>());
            operationsCount++; // initialization
        }

        for (Graph.Edge edge : edges) {
            adjList.get(edge.getFrom()).add(edge);
            adjList.get(edge.getTo()).add(new Graph.Edge(edge.getTo(), edge.getFrom(), edge.getWeight()));
            operationsCount += 2; // adding edges
        }

        Set<String> visited = new HashSet<>();
        PriorityQueue<EdgeWithNode> pq = new PriorityQueue<>();
        List<Graph.Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;

        // Начинаем с первого узла
        String startNode = nodes.get(0);
        visited.add(startNode);
        operationsCount++; // add to visited

        // Добавляем все рёбра первого узла
        for (Graph.Edge edge : adjList.get(startNode)) {
            pq.offer(new EdgeWithNode(edge, startNode));
            operationsCount++; // add to priority queue
        }

        while (!pq.isEmpty() && visited.size() < nodes.size()) {
            EdgeWithNode current = pq.poll();
            operationsCount++; // poll operation

            Graph.Edge edge = current.edge;
            String nextNode = edge.getTo();

            operationsCount++; // check if visited
            if (visited.contains(nextNode)) {
                continue;
            }

            // Добавляем ребро в MST
            mstEdges.add(new Graph.Edge(current.fromNode, nextNode, edge.getWeight()));
            totalCost += edge.getWeight();
            visited.add(nextNode);
            operationsCount += 2; // add edge and cost

            // Добавляем все рёбра нового узла
            for (Graph.Edge nextEdge : adjList.get(nextNode)) {
                operationsCount++; // check each neighbor
                if (!visited.contains(nextEdge.getTo())) {
                    pq.offer(new EdgeWithNode(nextEdge, nextNode));
                    operationsCount++; // add to priority queue
                }
            }
        }

        long endTime = System.nanoTime();
        double executionTimeMs = (endTime - startTime) / 1_000_000.0;

        return new MSTResult(mstEdges, totalCost, operationsCount, executionTimeMs);
    }

    private static class EdgeWithNode implements Comparable<EdgeWithNode> {
        Graph.Edge edge;
        String fromNode;

        EdgeWithNode(Graph.Edge edge, String fromNode) {
            this.edge = edge;
            this.fromNode = fromNode;
        }

        @Override
        public int compareTo(EdgeWithNode other) {
            return Integer.compare(this.edge.getWeight(), other.edge.getWeight());
        }
    }
}