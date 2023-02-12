import com.example.mappingservice.Service;
import com.example.mappingservice.servicemapping.GraphUtil;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;
import java.util.Set;

public class TestGreedy {

    public static void main(String[] args) {

        SimpleWeightedGraph<Service, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        Service a = new Service();
        a.setId(1);
        Service b = new Service();
        b.setId(2);
        Service c = new Service();
        c.setId(3);
        Service d = new Service();
        d.setId(4);
        Service e = new Service();
        e.setId(5);
        Service f = new Service();
        f.setId(6);
        Service g = new Service();
        g.setId(7);
        Service h = new Service();
        h.setId(8);
        Service i = new Service();
        i.setId(9);
        Service j = new Service();
        j.setId(10);

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);
        graph.addVertex(f);
        graph.addVertex(g);
        graph.addVertex(h);
        graph.addVertex(i);
        graph.addVertex(j);

        DefaultWeightedEdge edge0 = graph.addEdge(a, b);
        graph.setEdgeWeight(edge0, 10);
        DefaultWeightedEdge edge1 = graph.addEdge(b, c);
        graph.setEdgeWeight(edge1, 15);
        DefaultWeightedEdge edge2 = graph.addEdge(c, d);
        graph.setEdgeWeight(edge2, 3);
        DefaultWeightedEdge edge3 = graph.addEdge(d, e);
        graph.setEdgeWeight(edge3, 12);
        DefaultWeightedEdge edge4 = graph.addEdge(d, f);
        graph.setEdgeWeight(edge4, 7);
        DefaultWeightedEdge edge5 = graph.addEdge(g, i);
        graph.setEdgeWeight(edge5, 2);
        DefaultWeightedEdge edge6 = graph.addEdge(h, i);
        graph.setEdgeWeight(edge6, 12);
        DefaultWeightedEdge edge7 = graph.addEdge(j, i);
        graph.setEdgeWeight(edge7, 9);
        DefaultWeightedEdge edge8 = graph.addEdge(j, g);
        graph.setEdgeWeight(edge8, 2);
//        DefaultWeightedEdge edge9 = graph.addEdge(d, f);
//        graph.setEdgeWeight(edge9, 2);
//        DefaultWeightedEdge edge10 = graph.addEdge(e, f);
//        graph.setEdgeWeight(edge10, 3);

        List<Set<Service>> connectedSets = GraphUtil.performMinKCutAlgorithm(graph, 4);

        System.out.println(connectedSets);
    }

}
