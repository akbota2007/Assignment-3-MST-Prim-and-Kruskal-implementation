import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class JsonHandler {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static List<Graph> readInput(String filename) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        JsonObject root = JsonParser.parseString(content).getAsJsonObject();
        JsonArray graphsArray = root.getAsJsonArray("graphs");

        List<Graph> graphs = new ArrayList<>();

        for (JsonElement graphElement : graphsArray) {
            JsonObject graphObj = graphElement.getAsJsonObject();
            int id = graphObj.get("id").getAsInt();

            // Читаем узлы
            JsonArray nodesArray = graphObj.getAsJsonArray("nodes");
            List<String> nodes = new ArrayList<>();
            for (JsonElement node : nodesArray) {
                nodes.add(node.getAsString());
            }

            // Читаем рёбра
            JsonArray edgesArray = graphObj.getAsJsonArray("edges");
            List<Graph.Edge> edges = new ArrayList<>();
            for (JsonElement edgeElement : edgesArray) {
                JsonObject edgeObj = edgeElement.getAsJsonObject();
                String from = edgeObj.get("from").getAsString();
                String to = edgeObj.get("to").getAsString();
                int weight = edgeObj.get("weight").getAsInt();
                edges.add(new Graph.Edge(from, to, weight));
            }

            graphs.add(new Graph(id, nodes, edges));
        }

        return graphs;
    }

    public static void writeOutput(String filename, List<GraphResult> results) throws IOException {
        JsonObject root = new JsonObject();
        JsonArray resultsArray = new JsonArray();

        for (GraphResult result : results) {
            JsonObject resultObj = new JsonObject();
            resultObj.addProperty("graph_id", result.graphId);

            // Input stats
            JsonObject inputStats = new JsonObject();
            inputStats.addProperty("vertices", result.vertices);
            inputStats.addProperty("edges", result.edges);
            resultObj.add("input_stats", inputStats);

            // Prim's result
            JsonObject primObj = new JsonObject();
            JsonArray primEdges = new JsonArray();
            for (Graph.Edge edge : result.primResult.mstEdges) {
                JsonObject edgeObj = new JsonObject();
                edgeObj.addProperty("from", edge.getFrom());
                edgeObj.addProperty("to", edge.getTo());
                edgeObj.addProperty("weight", edge.getWeight());
                primEdges.add(edgeObj);
            }
            primObj.add("mst_edges", primEdges);
            primObj.addProperty("total_cost", result.primResult.totalCost);
            primObj.addProperty("operations_count", result.primResult.operationsCount);
            primObj.addProperty("execution_time_ms", Math.round(result.primResult.executionTimeMs * 100.0) / 100.0);
            resultObj.add("prim", primObj);

            // Kruskal's result
            JsonObject kruskalObj = new JsonObject();
            JsonArray kruskalEdges = new JsonArray();
            for (Graph.Edge edge : result.kruskalResult.mstEdges) {
                JsonObject edgeObj = new JsonObject();
                edgeObj.addProperty("from", edge.getFrom());
                edgeObj.addProperty("to", edge.getTo());
                edgeObj.addProperty("weight", edge.getWeight());
                kruskalEdges.add(edgeObj);
            }
            kruskalObj.add("mst_edges", kruskalEdges);
            kruskalObj.addProperty("total_cost", result.kruskalResult.totalCost);
            kruskalObj.addProperty("operations_count", result.kruskalResult.operationsCount);
            kruskalObj.addProperty("execution_time_ms", Math.round(result.kruskalResult.executionTimeMs * 100.0) / 100.0);
            resultObj.add("kruskal", kruskalObj);

            resultsArray.add(resultObj);
        }

        root.add("results", resultsArray);

        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(root, writer);
        }
    }

    public static class GraphResult {
        int graphId;
        int vertices;
        int edges;
        PrimAlgorithm.MSTResult primResult;
        KruskalAlgorithm.MSTResult kruskalResult;

        public GraphResult(int graphId, int vertices, int edges,
                           PrimAlgorithm.MSTResult primResult,
                           KruskalAlgorithm.MSTResult kruskalResult) {
            this.graphId = graphId;
            this.vertices = vertices;
            this.edges = edges;
            this.primResult = primResult;
            this.kruskalResult = kruskalResult;
        }
    }
}