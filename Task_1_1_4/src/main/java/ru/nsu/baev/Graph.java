package ru.nsu.baev;

import java.util.List;

public interface Graph {

    void addVertex();

    void removeVertex(Integer vert);

    void addEdge(Integer fromVert, Integer toVert);

    void removeEdge(Integer fromVert, Integer toVert);

    List<Integer> getNeighbors(Integer vert);

    void print();
}