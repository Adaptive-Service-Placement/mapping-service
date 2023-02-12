package com.example.mappingservice.servicemapping;

import com.example.mappingservice.ApplicationSystem;
import com.example.mappingservice.Connection;
import com.example.mappingservice.Service;
import com.example.mappingservice.dto.MigrationInstruction;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.mappingservice.servicemapping.GraphUtil.performMinKCutAlgorithm;

public class Mapper {

    private MigrationInstruction migrationInstruction;


    public Mapper(ApplicationSystem applicationSystem, int k) {
        SimpleWeightedGraph<Service, DefaultWeightedEdge> systemGraph = constructGraphFromSystem(applicationSystem);
        List<Set<Service>> kCutResult = performMinKCutAlgorithm(systemGraph, k);
        migrationInstruction.getGroups().addAll(kCutResult);
        for (Service service : applicationSystem.getServices()) {
            List<Connection> allConnections = applicationSystem.getConnections().stream()
                    .filter(connection -> service.equals(connection.getService1()) || service.equals(connection.getService2()))
                    .collect(Collectors.toList());

            migrationInstruction.getAdjacencyMap().put(service, allConnections);
        }
    }

    private SimpleWeightedGraph<Service, DefaultWeightedEdge> constructGraphFromSystem(ApplicationSystem applicationSystem) {
        SimpleWeightedGraph<Service, DefaultWeightedEdge> g = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        applicationSystem.getServices().forEach(g::addVertex);
        for (Connection connection : applicationSystem.getConnections()) {
            DefaultWeightedEdge edge = g.addEdge(connection.getService1(), connection.getService2());
            g.setEdgeWeight(edge, connection.getAffinity().doubleValue());
        }
        return g;
    }

    public MigrationInstruction getMigrationInstruction() {
        return migrationInstruction;
    }


}
