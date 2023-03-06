package com.example.mappingservice;

import com.example.mappingservice.dto.MigrationInstruction;
import com.example.mappingservice.util.GraphUtil;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Mapper {

    private MigrationInstruction migrationInstruction;
    private final ApplicationSystem applicationSystem;
    private final int k;
    private final GraphUtil graphUtil;

    public Mapper(ApplicationSystem applicationSystem, int k) {
        this.graphUtil = new GraphUtil();
        this.applicationSystem = applicationSystem;
        this.k = k;
    }

    public MigrationInstruction getMigrationInstruction() {
        if (this.migrationInstruction == null) {
            this.migrationInstruction = new MigrationInstruction();
            SimpleWeightedGraph<Service, DefaultWeightedEdge> systemGraph = graphUtil.constructGraphFromSystem(applicationSystem);
            List<Set<Service>> kCutResult = graphUtil.performMinKCutAlgorithm(systemGraph, k);

            this.migrationInstruction.getGroups().addAll(kCutResult);

            for (Service service : applicationSystem.getServices()) {
                List<Connection> allConnections = applicationSystem.getConnections().stream()
                        .filter(connection -> service.equals(connection.getService1()) || service.equals(connection.getService2()))
                        .collect(Collectors.toList());

                this.migrationInstruction.getAdjacencyMap().put(service.getId(), allConnections);
            }
        }

        return migrationInstruction;
    }


}
