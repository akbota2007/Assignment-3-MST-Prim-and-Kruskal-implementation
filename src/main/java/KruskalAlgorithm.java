import java.util.*;

public class KruskalAlgorithm {

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
        List<Graph.Edge> edges = new ArrayList<>(graph.getEdges());

        // Сортируем рёбра по весу
        Collections.sort(edges);
        operationsCount += edges.size(); // sorting operations approximation

        // Инициализация Union-Find
        UnionFind uf = new UnionFind(nodes);
        operationsCount += nodes.size(); // initialization

        List<Graph.Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;

        for (Graph.Edge edge : edges) {
            operationsCount++; // processing edge

            String root1 = uf.find(edge.getFrom());
            String root2 = uf.find(edge.getTo());
            operationsCount += 2; // find operations

            // Если узлы в разных компонентах, добавляем ребро
            if (!root1.equals(root2)) {
                operationsCount++; // comparison
                mstEdges.add(edge);
                totalCost += edge.getWeight();
                uf.union(edge.getFrom(), edge.getTo());
                operationsCount++; // union operation

                // Если собрали все рёбра MST, выходим
                if (mstEdges.size() == nodes.size() - 1) {
                    break;
                }
            }
        }

        long endTime = System.nanoTime();
        double executionTimeMs = (endTime - startTime) / 1_000_000.0;

        return new MSTResult(mstEdges, totalCost, operationsCount, executionTimeMs);
    }

    // Union-Find (Disjoint Set Union) структура данных
    private static class UnionFind {
        private Map<String, String> parent;
        private Map<String, Integer> rank;

        public UnionFind(List<String> nodes) {
            parent = new HashMap<>();
            rank = new HashMap<>();
            for (String node : nodes) {
                parent.put(node, node);
                rank.put(node, 0);
            }
        }

        public String find(String node) {
            if (!parent.get(node).equals(node)) {
                parent.put(node, find(parent.get(node))); // path compression
            }
            return parent.get(node);
        }

        public void union(String node1, String node2) {
            String root1 = find(node1);
            String root2 = find(node2);

            if (root1.equals(root2)) {
                return;
            }

            // Union by rank
            if (rank.get(root1) < rank.get(root2)) {
                parent.put(root1, root2);
            } else if (rank.get(root1) > rank.get(root2)) {
                parent.put(root2, root1);
            } else {
                parent.put(root2, root1);
                rank.put(root1, rank.get(root1) + 1);
            }
        }
    }
}