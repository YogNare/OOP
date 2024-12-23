package ru.nsu.baev;

import org.junit.jupiter.api.Test;
import ru.nsu.baev.AdjacencyMatrix;
public class GraphTest {
    @Test
    public void Test1() {
        Graph graph = new AdjacencyMatrix();

        graph.addVertex();
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(2, 0);
        System.out.println(graph.getNeighbors(2));
        graph.removeVertex(0);
        graph.print();
    }
}
