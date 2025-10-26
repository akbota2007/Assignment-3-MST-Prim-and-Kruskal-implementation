import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MSTComparison {

    public static void main(String[] args) {
        try {
            // Чтение входных данных
            System.out.println("Reading input data from input_example.json...");
            List<Graph> graphs = JsonHandler.readInput("input_example.json");
            System.out.println("Successfully read " + graphs.size() + " graph(s)\n");

            List<JsonHandler.GraphResult> results = new ArrayList<>();

            // Обработка каждого графа
            for (Graph graph : graphs) {
                System.out.println("=".repeat(60));
                System.out.println("Processing Graph ID: " + graph.getId());
                System.out.println("Vertices: " + graph.getVertexCount() + ", Edges: " + graph.getEdgeCount());
                System.out.println("=".repeat(60));

                // Запуск Prim's алгоритма
                System.out.println("\nRunning Prim's Algorithm...");
                PrimAlgorithm.MSTResult primResult = PrimAlgorithm.findMST(graph);
                System.out.println("✓ Prim's Algorithm completed");
                System.out.println("  Total Cost: " + primResult.totalCost);
                System.out.println("  Operations: " + primResult.operationsCount);
                System.out.println("  Execution Time: " + String.format("%.2f", primResult.executionTimeMs) + " ms");
                System.out.println("  MST Edges:");
                for (Graph.Edge edge : primResult.mstEdges) {
                    System.out.println("    " + edge.getFrom() + " - " + edge.getTo() + " (weight: " + edge.getWeight() + ")");
                }

                // Запуск Kruskal's алгоритма
                System.out.println("\nRunning Kruskal's Algorithm...");
                KruskalAlgorithm.MSTResult kruskalResult = KruskalAlgorithm.findMST(graph);
                System.out.println("✓ Kruskal's Algorithm completed");
                System.out.println("  Total Cost: " + kruskalResult.totalCost);
                System.out.println("  Operations: " + kruskalResult.operationsCount);
                System.out.println("  Execution Time: " + String.format("%.2f", kruskalResult.executionTimeMs) + " ms");
                System.out.println("  MST Edges:");
                for (Graph.Edge edge : kruskalResult.mstEdges) {
                    System.out.println("    " + edge.getFrom() + " - " + edge.getTo() + " (weight: " + edge.getWeight() + ")");
                }

                // Сравнение результатов
                System.out.println("\nComparison:");
                System.out.println("  Total costs match: " + (primResult.totalCost == kruskalResult.totalCost ? "✓ YES" : "✗ NO"));
                System.out.println("  Prim operations: " + primResult.operationsCount);
                System.out.println("  Kruskal operations: " + kruskalResult.operationsCount);
                System.out.println("  Faster algorithm: " +
                        (primResult.executionTimeMs < kruskalResult.executionTimeMs ? "Prim" : "Kruskal"));
                System.out.println();

                // Сохранение результата
                results.add(new JsonHandler.GraphResult(
                        graph.getId(),
                        graph.getVertexCount(),
                        graph.getEdgeCount(),
                        primResult,
                        kruskalResult
                ));
            }

            // Запись результатов в файл
            System.out.println("=".repeat(60));
            System.out.println("Writing results to output_example.json...");
            JsonHandler.writeOutput("output_example.json", results);
            System.out.println("✓ Results successfully written to output_example.json");
            System.out.println("=".repeat(60));

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}