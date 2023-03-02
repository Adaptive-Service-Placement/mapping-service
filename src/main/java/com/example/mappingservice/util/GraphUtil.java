package com.example.mappingservice.util;

import com.example.mappingservice.ApplicationSystem;
import com.example.mappingservice.Connection;
import com.example.mappingservice.Service;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.flow.GusfieldGomoryHuCutTree;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class GraphUtil {

    public SimpleWeightedGraph<Service, DefaultWeightedEdge> constructGraphFromSystem(ApplicationSystem applicationSystem) {
        SimpleWeightedGraph<Service, DefaultWeightedEdge> g = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        applicationSystem.getServices().forEach(g::addVertex);
        for (Connection connection : applicationSystem.getConnections()) {
            DefaultWeightedEdge edge = g.addEdge(connection.getService1(), connection.getService2());
            g.setEdgeWeight(edge, connection.getAffinity().doubleValue());
        }
        return g;
    }

    // Algorithm Minimum k-Cut
    public List<Set<Service>> performMinKCutAlgorithm(SimpleWeightedGraph<Service, DefaultWeightedEdge> graph, int k) {
        // Step 1 Compute a Gomory-Hu tree
        SimpleWeightedGraph<Service, DefaultWeightedEdge> gomoryHuCutTree = computeGusfieldGomoryHuCutTree(graph);

        // Step 2 Remove the k-1 lightest edges in Gomory-Hu tree
        removeLightestEdges(gomoryHuCutTree, k);

        // Step 3 Return connected sets
        return new ConnectivityInspector<>(gomoryHuCutTree).connectedSets();
    }

    private void removeLightestEdges(SimpleWeightedGraph<Service, DefaultWeightedEdge> gomoryHuCutTree, int k) {
        List<DefaultWeightedEdge> removedEdges = new ArrayList<>();
        Set<DefaultWeightedEdge> allEdges = gomoryHuCutTree.edgeSet();

        int numberOfEdgesChecked = 0;

        while (removedEdges.size() < k - 1 && allEdges.size() >= numberOfEdgesChecked) {
            DefaultWeightedEdge minimumWeightEdge = allEdges.stream()
                    .min(Comparator.comparingDouble(gomoryHuCutTree::getEdgeWeight))
                    .orElse(null);

            if (minimumWeightEdge != null) {
                removedEdges.add(minimumWeightEdge);
                gomoryHuCutTree.removeEdge(minimumWeightEdge);
            }
            numberOfEdgesChecked++;
        }
    }

    private SimpleWeightedGraph<Service, DefaultWeightedEdge> computeGusfieldGomoryHuCutTree(SimpleWeightedGraph<Service, DefaultWeightedEdge> graph) {
        GusfieldGomoryHuCutTree<Service, DefaultWeightedEdge> gomoryHuCutTree = new GusfieldGomoryHuCutTree<>(graph);
        return gomoryHuCutTree.getGomoryHuTree();
    }
}
