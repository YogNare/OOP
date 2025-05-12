package ru.nsu.baev;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

public interface Graph {

    void addVertex();

    void removeVertex(Integer vert);

    void addEdge(Integer fromVert, Integer toVert);

    void removeEdge(Integer fromVert, Integer toVert);

    List<Integer> getNeighbors(Integer vert);

    Graph readFromFile(String filePath);

    void print();
}