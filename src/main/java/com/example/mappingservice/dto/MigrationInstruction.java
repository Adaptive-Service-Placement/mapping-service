package com.example.mappingservice.dto;

import com.example.mappingservice.Connection;
import com.example.mappingservice.Service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MigrationInstruction {
    private List<Set<Service>> groups;
    private Map<String, List<Connection>> adjacencyMap;

    public List<Set<Service>> getGroups() {
        if (groups == null) {
            groups = new ArrayList<>();
        }
        return groups;
    }

    public Map<String, List<Connection>> getAdjacencyMap() {
        if (adjacencyMap == null) {
            adjacencyMap = new HashMap<>();
        }
        return adjacencyMap;
    }

}
