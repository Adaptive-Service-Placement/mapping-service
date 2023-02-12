package com.example.mappingservice.dto;

import com.example.mappingservice.Connection;
import com.example.mappingservice.Service;

import java.util.*;

public class MigrationInstruction {
    private List<Set<Service>> groups;
    private Map<Service, List<Connection>> adjacencyMap;

    public List<Set<Service>> getGroups() {
        if (groups == null) {
            return new ArrayList<>();
        }
        return groups;
    }

    public Map<Service, List<Connection>> getAdjacencyMap() {
        if (adjacencyMap == null) {
            return new HashMap<>();
        }
        return adjacencyMap;
    }

}
