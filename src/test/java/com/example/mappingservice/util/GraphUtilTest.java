package com.example.mappingservice.util;

import com.example.mappingservice.ApplicationSystem;
import com.example.mappingservice.Service;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.jupiter.api.BeforeEach;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GraphUtilTest {

    private GraphUtil underTest;

    @BeforeEach
    public void setUp() {
        underTest = new GraphUtil();
    }

    @Test
    public void constructGraphFromSystemTest() {
        assertGraph(null, null);
    }

    @DataProvider
    private Object[][] performMinKCutAlgorithmDataProvider() {
        return new Object[][]{
                {},
                {},
                {},
                {}
        };
    }

    @Test(dataProvider = "performMinKCutAlgorithmDataProvider")
    public void performMinKCutAlgorithmTest() {
        int k = 2;
        assertEquals(null, underTest.performMinKCutAlgorithm(null, k));
    }

    private void assertGraph(ApplicationSystem applicationSystem, SimpleWeightedGraph<Service, DefaultWeightedEdge> graph) {
        // TODO: assert expected edges, vertices, weights
        assertEquals(graph, underTest.constructGraphFromSystem(applicationSystem));
    }

}