package ru.nsu.baev;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AdjacencyList implements Graph {

    private final List<List<Integer>> adjList = new ArrayList<>();

    @Override
    public void addVertex() {
        adjList.add(new ArrayList<>());
    }

    @Override
    public void removeVertex(Integer vert) {
        if (isInvalidVertex(vert)) {
            System.out.println("Invalid vertex");
            return;
        }

        // Удаление вершины и её ссылок
        adjList.remove((int) vert);
        adjList.forEach(vertexList -> vertexList.removeIf(neighbor -> neighbor.equals(vert)));
    }

    @Override
    public void addEdge(Integer fromVert, Integer toVert) {
        if (isInvalidVertex(fromVert) || isInvalidVertex(toVert)) {
            System.out.println("Invalid vertex");
            return;
        }

        adjList.get(fromVert).add(toVert);
    }

    @Override
    public void removeEdge(Integer fromVert, Integer toVert) {
        if (isInvalidVertex(fromVert) || isInvalidVertex(toVert)) {
            System.out.println("Invalid vertex");
            return;
        }

        adjList.get(fromVert).removeIf(toVert::equals);
    }

    @Override
    public List<Integer> getNeighbors(Integer vert) {
        if (isInvalidVertex(vert)) {
            System.out.println("Invalid vertex");
            return List.of();
        }

        return new ArrayList<>(new HashSet<>(adjList.get(vert)));
    }

    @Override
    public void print() {
        IntStream.range(0, adjList.size())
                .forEach(i -> System.out.printf("%d -> %s%n", i, adjList.get(i)));
    }

    public List<Integer> topologicalSort() {
        int size = adjList.size();
        int[] inDegree = new int[size];

        adjList.forEach(vertexList ->
                vertexList.forEach(neighbor -> inDegree[neighbor]++)
        );

        Queue<Integer> zeroInDegree = IntStream.range(0, size)
                .filter(i -> inDegree[i] == 0)
                .boxed()
                .collect(Collectors.toCollection(ArrayDeque::new));

        List<Integer> topologicalOrder = new ArrayList<>();

        while (!zeroInDegree.isEmpty()) {
            int current = zeroInDegree.poll();
            topologicalOrder.add(current);

            adjList.get(current).forEach(neighbor -> {
                if (--inDegree[neighbor] == 0) {
                    zeroInDegree.add(neighbor);
                }
            });
        }

        return topologicalOrder;
    }

    @Override
    public AdjacencyList readFromFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        AdjacencyList graph = new AdjacencyList();

        try {
            JsonNode rootNode = objectMapper.readTree(new File(filePath));

            int vertexCount = rootNode.get("vertexes").asInt();
            IntStream.range(0, vertexCount).forEach(i -> graph.addVertex());

            JsonNode edges = rootNode.get("edge");
            for (JsonNode edge : edges) {
                int from = edge.get(0).asInt();
                int to = edge.get(1).asInt();
                graph.addEdge(from, to);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filePath, e);
        }

        return graph;
    }

    private boolean isInvalidVertex(Integer vert) {
        return vert == null || vert < 0 || vert >= adjList.size();
    }
}