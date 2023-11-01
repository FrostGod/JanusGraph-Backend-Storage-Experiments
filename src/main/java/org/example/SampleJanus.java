package org.example;

import org.apache.commons.configuration2.BaseConfiguration;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.*;
import org.janusgraph.core.schema.JanusGraphManagement;

import org.apache.commons.configuration2.BaseConfiguration;
//import org.apache.commons.configuration2.Configuration;
//import org.apache.commons.configuration.BaseConfiguration;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.janusgraph.core.*;
//import org.apache.commons.configuration.BaseConfiguration;
//import org.janusgraph.core.schema.*;
//import org.janusgraph.core.schema.JanusGraphManagement;
//import org.apache.tinkerpop.gremlin.structure.Graph;
//import org.janusgraph.diskstorage.configuration.ReadConfiguration;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.janusgraph.core.*;
import org.janusgraph.core.schema.*;

import static org.apache.tinkerpop.gremlin.structure.io.binary.DataType.T;


// This was dummy class just for my reference
public class SampleJanus {

    public static void main(String[] args) {
        BaseConfiguration config = new BaseConfiguration();
        config.setProperty("storage.backend", "inmemory");
//        config.setProperty("index.search.backend", "elasticsearch");

        JanusGraph graph = JanusGraphFactory.open(config);

        JanusGraphManagement mgmt = graph.openManagement();


        PropertyKey name = mgmt.makePropertyKey("name").dataType(String.class).make();
        mgmt.buildIndex("byName", Vertex.class).addKey(name).buildCompositeIndex();
        mgmt.commit();
//
        JanusGraphTransaction tx = graph.newTransaction();
        Vertex v1 = tx.addVertex();
        v1.property("name", "John Doe");
        tx.commit();
//        JanusGraphTransaction tx = graph.newTransaction();
//        JanusGraphVertex airportVertex = tx.addVertex("airport");
//        airportVertex.property("id", 1);
//        airportVertex.property("type", "airport");
//        airportVertex.property("code", "ATL");
//        airportVertex.property("icao", "KATL");
//        airportVertex.property("desc", "Hartsfield - Jackson Atlanta International Airport");
//        airportVertex.property("region", "US-GA");
//        airportVertex.property("runways", 5);
//        airportVertex.property("longest", 12390);
//        airportVertex.property("elev", 1026);
//        airportVertex.property("country", "US");
//        airportVertex.property("city", "Atlanta");
//        airportVertex.property("lat", 33.6366996765137);
//        airportVertex.property("lon", -84.4281005859375);
//        airportVertex.addEdge("route", "")
//        tx.commit();
//
////        Edge routedge =
//
//        GraphTraversalSource g = graph.traversal();
//        System.out.println("hi");
////        System.out.println(g.V());
//
//        GraphTraversal<Vertex, Vertex> traversal = g.V();
//
//        while (traversal.hasNext()) {
//            JanusGraphVertex vertex = (JanusGraphVertex) traversal.next();
//            System.out.println(vertex.id());
//            // Add more properties as needed
//        }
//
//        graph.close();

        GraphTraversalSource g = graph.traversal();
        System.out.println("hola");
//        System.out.println(g.V()

        GraphTraversal<Vertex, Vertex> traversal = g.V();

        while (traversal.hasNext()) {
            JanusGraphVertex vertex = (JanusGraphVertex) traversal.next();
            System.out.println(vertex.property("name"));
            // Add more properties as needed
        }

        g.V().has("name", "John Doe").values("name").forEachRemaining(System.out::println);

        graph.close();
    }
}

