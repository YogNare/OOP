package ru.nsu.baev;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IncidenceMatrix implements Graph {

    private final List<List<Integer>> matrix = new ArrayList<>();
    private int edgeCount = 0;

    @Override
    public void addVertex() {
        matrix.add(new ArrayList<>(Collections.nCopies(edgeCount, 0)));
    }

    @Override
    public void removeVertex(Integer vert) {
        if (isInvalidVertex(vert)) {
            System.err.println("Invalid vertex");
            return;
        }

        List<Integer> edgesToRemove = IntStream.range(0, edgeCount)
                .filter(edge -> matrix.get(vert).get(edge) != 0)
                .boxed()
                .toList();

        edgesToRemove.forEach(edge -> matrix.forEach(vertex -> vertex.remove((int) edge)));
        edgeCount -= edgesToRemove.size();

        matrix.remove((int) vert);
    }

    @Override
    public void addEdge(Integer fromVert, Integer toVert) {
        if (isInvalidVertex(fromVert) || isInvalidVertex(toVert)) {
            System.err.println("Invalid vertex");
            return;
        }

        matrix.forEach(vertex -> vertex.add(0));
        matrix.get(fromVert).set(edgeCount, -1);
        matrix.get(toVert).set(edgeCount, 1);

        edgeCount++;
    }

    @Override
    public void removeEdge(Integer fromVert, Integer toVert) {
        if (isInvalidVertex(fromVert) || isInvalidVertex(toVert)) {
            System.err.println("Invalid vertex");
            return;
        }

        OptionalInt edgeToRemove = IntStream.range(0, edgeCount)
                .filter(edge -> matrix.get(fromVert).get(edge) == -1 && matrix.get(toVert).get(edge) == 1)
                .findFirst();

        edgeToRemove.ifPresent(edge -> {
            matrix.forEach(vertex -> vertex.remove(edge));
            edgeCount--;
        });
    }

    @Override
    public List<Integer> getNeighbors(Integer vert) {
        if (isInvalidVertex(vert)) {
            System.err.println("Invalid vertex");
            return List.of();
        }

        return IntStream.range(0, edgeCount)
                .filter(edge -> matrix.get(vert).get(edge) != 0)
                .mapToObj(edge -> IntStream.range(0, matrix.size())
                        .filter(vertex -> vertex != vert && matrix.get(vertex).get(edge) != 0)
                        .boxed()
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .distinct()
                .toList();
    }

    @Override
    public void print() {
        matrix.forEach(row -> System.out.println(
                row.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(" "))
        ));
    }

    public List<Integer> topologicalSort() {
        int size = matrix.size();
        int[] inDegree = new int[size];

        // Подсчёт входящих степеней
        for (int vertex = 0; vertex < size; vertex++) {
            for (int edge = 0; edge < edgeCount; edge++) {
                if (matrix.get(vertex).get(edge) == 1) {
                    inDegree[vertex]++;
                }
            }
        }

        Queue<Integer> zeroInDegree = IntStream.range(0, size)
                .filter(i -> inDegree[i] == 0)
                .boxed()
                .collect(Collectors.toCollection(ArrayDeque::new));

        List<Integer> topologicalOrder = new ArrayList<>();

        while (!zeroInDegree.isEmpty()) {
            int current = zeroInDegree.poll();
            topologicalOrder.add(current);

            for (int edge = 0; edge < edgeCount; edge++) {
                if (matrix.get(current).get(edge) == -1) {
                    for (int neighbor = 0; neighbor < size; neighbor++) {
                        if (matrix.get(neighbor).get(edge) == 1) {
                            if (--inDegree[neighbor] == 0) {
                                zeroInDegree.add(neighbor);
                            }
                        }
                    }
                }
            }
        }

        return topologicalOrder;
    }
    private boolean isInvalidVertex(Integer vert) {
        return vert == null || vert < 0 || vert >= matrix.size();
    }
}