package ru.nsu.baev;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class AdjacencyMatrix implements Graph {
    private final List<List<Integer>> matrix = new ArrayList<>();

    @Override
    public void addVertex() {
        matrix.add(new ArrayList<>(Collections.nCopies(matrix.size(), 0)));
        matrix.forEach(row -> row.add(0));
    }

    @Override
    public void removeVertex(Integer vert) {
        if (isInvalidVertex(vert)) {
            System.out.println("Invalid vertex");
            return;
        }

        matrix.remove((int) vert);
        matrix.forEach(row -> row.remove((int) vert));
    }

    @Override
    public void addEdge(Integer fromVert, Integer toVert) {
        if (isInvalidVertex(fromVert) || isInvalidVertex(toVert)) {
            System.out.println("Invalid vertex");
            return;
        }

        matrix.get(fromVert).set(toVert, 1);
    }

    @Override
    public void removeEdge(Integer fromVert, Integer toVert) {
        if (isInvalidVertex(fromVert) || isInvalidVertex(toVert)) {
            System.out.println("Invalid vertex");
            return;
        }

        matrix.get(fromVert).set(toVert, 0);
    }

    @Override
    public List<Integer> getNeighbors(Integer vert) {
        if (isInvalidVertex(vert)) {
            System.out.println("Invalid vertex");
            return List.of();
        }

        return IntStream.range(0, matrix.size())
                .filter(i -> matrix.get(vert).get(i) == 1 || matrix.get(i).get(vert) == 1) // Проверяем строки и столбцы
                .distinct()
                .boxed()
                .collect(Collectors.toList());
    }

    @Override
    public void print() {
        matrix.forEach(row -> System.out.println(row.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" "))));
    }

    public List<Integer> topologicalSort() {
        int size = matrix.size();
        int[] inDegree = new int[size];

        matrix.forEach(row -> {
            for (int col = 0; col < row.size(); col++) {
                if (row.get(col) == 1) {
                    inDegree[col] ++;
                }
            }
        });

        Queue<Integer> zeroInDegree = IntStream.range(0, size)
                .filter(i -> inDegree[i] == 0)
                .boxed()
                .collect(Collectors.toCollection(ArrayDeque::new));

        List<Integer> topologicalOrder = new ArrayList<>();

        while (!zeroInDegree.isEmpty()) {
            int current = zeroInDegree.poll();
            topologicalOrder.add(current);

            for (int neighbor = 0; neighbor < size; neighbor++) {
                if (matrix.get(current).get(neighbor) == 1) {
                    inDegree[neighbor] --;
                    if (inDegree[neighbor] == 0) {
                        zeroInDegree.add(neighbor);
                    }
                }
            }
        }

        return topologicalOrder;
    }

    @Override
    public AdjacencyMatrix readFromFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        AdjacencyMatrix graph = new AdjacencyMatrix();

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
        return vert == null || vert < 0 || vert >= matrix.size();
    }
}