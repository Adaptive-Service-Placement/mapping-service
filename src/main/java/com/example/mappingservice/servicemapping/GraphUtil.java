package com.example.mappingservice.servicemapping;

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

    // Algorithm Minimum k-Cut
    public static List<Set<Service>> performMinKCutAlgorithm(SimpleWeightedGraph<Service, DefaultWeightedEdge> graph, int k) {
        // Step 1 Compute a Gomory-Hu tree
        SimpleWeightedGraph<Service, DefaultWeightedEdge> gomoryHuCutTree = computeGusfieldGomoryHuCutTree(graph);

        // Step 2 Choose the k-1 lightest edges in Gomory-Hu tree and return the union of their associated cuts in original graph
        removeLightestEdges(gomoryHuCutTree, k);
        return new ConnectivityInspector<>(gomoryHuCutTree).connectedSets();
    }

    private static void removeLightestEdges(SimpleWeightedGraph<Service, DefaultWeightedEdge> gomoryHuCutTree, int k) {
        List<DefaultWeightedEdge> removedEdges = new ArrayList<>();
        Set<DefaultWeightedEdge> allEdges = gomoryHuCutTree.edgeSet();

        int numberOfEdgesRemoved = 0;

        while (removedEdges.size() < k - 1 && allEdges.size() >= numberOfEdgesRemoved) {
            DefaultWeightedEdge minimumWeightEdge = allEdges.stream()
                    .min(Comparator.comparingDouble(gomoryHuCutTree::getEdgeWeight))
                    .orElse(null);

            if (minimumWeightEdge != null) {
                removedEdges.add(minimumWeightEdge);
                gomoryHuCutTree.removeEdge(minimumWeightEdge);
            }
            numberOfEdgesRemoved++;
        }
    }

    private static SimpleWeightedGraph<Service, DefaultWeightedEdge> computeGusfieldGomoryHuCutTree(SimpleWeightedGraph<Service, DefaultWeightedEdge> graph) {
        GusfieldGomoryHuCutTree<Service, DefaultWeightedEdge> gomoryHuCutTree = new GusfieldGomoryHuCutTree<>(graph);
        return gomoryHuCutTree.getGomoryHuTree();
    }
}
