package org.example;
import org.apache.commons.configuration2.BaseConfiguration;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;

import org.janusgraph.core.*;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.janusgraph.core.schema.*;

import java.util.List;
import java.time.LocalDateTime;

import static org.apache.tinkerpop.gremlin.structure.io.binary.DataType.T;

public class JanusGraphExample {

    public static JanusGraph init() {

//        config.setProperty("index.search.backend", "elasticsearch");
        BaseConfiguration config = new BaseConfiguration();


        // in memory
//        config.setProperty("storage.backend", "inmemory");

        // berkaley DB
        config.setProperty("storage.backend", "berkeleyje");
        LocalDateTime currentDateTime = LocalDateTime.now();
        String current_dir = System.getProperty("user.dir");
        String data_path = current_dir + "/data/";
        data_path += currentDateTime;
        config.setProperty("storage.directory", data_path);


        JanusGraph graph = JanusGraphFactory.open(config);

        JanusGraphManagement mgmt = graph.openManagement();

        // Create vertex labels
        for (String vertexLabel : new String[]{"airport", "country", "continent"}) {
            mgmt.makeVertexLabel(vertexLabel).make();
        }

        // Create edge labels
        for (String edgeLabel : new String[]{"route", "contains"}) {
            mgmt.makeEdgeLabel(edgeLabel).multiplicity(Multiplicity.MULTI).make();
        }

        // Define property keys
        PropertyKey city = mgmt.makePropertyKey("city").dataType(String.class).cardinality(Cardinality.SINGLE).make();
        PropertyKey lat = mgmt.makePropertyKey("lat").dataType(Double.class).cardinality(Cardinality.SINGLE).make();
        PropertyKey lon = mgmt.makePropertyKey("lon").dataType(Double.class).cardinality(Cardinality.SINGLE).make();
        PropertyKey dist = mgmt.makePropertyKey("dist").dataType(String.class).cardinality(Cardinality.SINGLE).make();
        PropertyKey identity = mgmt.makePropertyKey("identity").dataType(String.class).cardinality(Cardinality.SINGLE).make();
        PropertyKey type = mgmt.makePropertyKey("type").dataType(String.class).cardinality(Cardinality.SINGLE).make();
        PropertyKey code = mgmt.makePropertyKey("code").dataType(String.class).cardinality(Cardinality.SINGLE).make();
        PropertyKey icao = mgmt.makePropertyKey("icao").dataType(String.class).cardinality(Cardinality.SINGLE).make();
        PropertyKey desc = mgmt.makePropertyKey("desc").dataType(String.class).cardinality(Cardinality.SINGLE).make();
        PropertyKey region = mgmt.makePropertyKey("region").dataType(String.class).cardinality(Cardinality.SINGLE).make();
        PropertyKey runways = mgmt.makePropertyKey("runways").dataType(Integer.class).cardinality(Cardinality.SINGLE).make();
        PropertyKey longest = mgmt.makePropertyKey("longest").dataType(Integer.class).cardinality(Cardinality.SINGLE).make();
        PropertyKey elev = mgmt.makePropertyKey("elev").dataType(Integer.class).cardinality(Cardinality.SINGLE).make();
        PropertyKey country = mgmt.makePropertyKey("country").dataType(String.class).cardinality(Cardinality.SINGLE).make();

        // Composite Indices
        mgmt.buildIndex("Idx_comidx_Vertex_identity_unique", Vertex.class)
                .addKey(mgmt.getPropertyKey("identity"))
                .unique()
                .buildCompositeIndex();

        mgmt.buildIndex("Idx_comidx_Vertex_type_airport", Vertex.class)
                .addKey(mgmt.getPropertyKey("type"))
                .indexOnly(mgmt.getVertexLabel("airport"))
                .buildCompositeIndex();

        mgmt.buildIndex("Idx_comidx_Vertex_code", Vertex.class)
                .addKey(mgmt.getPropertyKey("code"))
                .buildCompositeIndex();

        mgmt.buildIndex("Idx_comidx_Vertex_icao", Vertex.class)
                .addKey(mgmt.getPropertyKey("icao"))
                .buildCompositeIndex();

        mgmt.buildIndex("Idx_comidx_Vertex_country", Vertex.class)
                .addKey(mgmt.getPropertyKey("country"))
                .buildCompositeIndex();

        mgmt.buildIndex("Idx_comidx_Vertex_city", Vertex.class)
                .addKey(mgmt.getPropertyKey("city"))
                .buildCompositeIndex();

        mgmt.buildIndex("Idx_comidx_Edge_identity", Edge.class)
                .addKey(mgmt.getPropertyKey("identity"))
                .buildCompositeIndex();


        System.out.println(mgmt.printSchema());
        mgmt.commit();

        return graph;
    }

    public static void addAirports(JanusGraph graph, List<String[]> airports){
        JanusGraphTransaction tx = graph.newTransaction();

        for(String[] v: airports) {
            JanusGraphVertex airportVertex = tx.addVertex("airport");
            String city, label, code, icao, desc, region, country, type;
            Integer runways, longest, elev;
            Double lat, lon;
            label = v[1];
            type = v[2];
            code = v[3];
            icao = v[4];
            desc = v[5];
            region = v[6];
            runways = Integer.parseInt(v[7]);
            longest = Integer.parseInt(v[8]);
            elev = Integer.parseInt(v[9]);
            country = v[10];
            city = v[11];
            lat = Double.parseDouble(v[12]);
            lon = Double.parseDouble(v[13]);
            // adding it
            airportVertex.property("type", "airport");
            airportVertex.property("code", code);
            airportVertex.property("icao", icao);
            airportVertex.property("desc", desc);
            airportVertex.property("region", region);
            airportVertex.property("runways", runways);
            airportVertex.property("longest", longest);
            airportVertex.property("elev", elev);
            airportVertex.property("country", country);
            airportVertex.property("city", city);
            airportVertex.property("lat", lat);
            airportVertex.property("lon", lon);

        }
        tx.commit();
    }

    public static void addCountries(JanusGraph graph, List<String[]> countries){
        JanusGraphTransaction tx = graph.newTransaction();

        for(String[] v: countries) {
            JanusGraphVertex countryVertex = tx.addVertex("country");
            String code, desc;
            code = v[3];
            desc = v[5];
            countryVertex.property("type", "country");
            countryVertex.property("code", code);
            countryVertex.property("desc", desc);
        }
        tx.commit();
    }
    public static void addContinents(JanusGraph graph, List<String[]> continents){
        JanusGraphTransaction tx = graph.newTransaction();

        for(String[] v: continents) {
            JanusGraphVertex countryVertex = tx.addVertex("continent");
            String code, desc;
            code = v[3];
            desc = v[5];
            // adding it
            countryVertex.property("type", "continent");
            countryVertex.property("code", code);
            countryVertex.property("desc", desc);
        }
        tx.commit();
    }

    public static void view_graph(JanusGraph graph){
        GraphTraversalSource g = graph.traversal();
        System.out.println("hi");

        GraphTraversal<Vertex, Vertex> traversal = g.V();
        Integer count = 0;
        while (traversal.hasNext()) {
            JanusGraphVertex vertex = (JanusGraphVertex) traversal.next();
            count += 1;
        }
        System.out.println("Total Vertices count is " +  count);
        final Integer[] tot = {0};
        g.E().forEachRemaining(edge -> {
            // Process the edge
            System.out.println(edge.inVertex().property("code") +" " +  edge.outVertex().property("code") + " " + edge.property("dist"));
            tot[0] += 1;
        });
        System.out.println("Count of Edges is " + tot[0]);

    }

    public static void addRoutes(JanusGraph graph, List<String[]> Routes){
        GraphTraversalSource g = graph.traversal();
        JanusGraphTransaction tx = graph.newTransaction();
        for(String[] Route: Routes) {
            Vertex sourceVertex = g.V().has("code", Route[1]).next();
            Vertex targetVertex = g.V().has("code", Route[2]).next();
            // Create the edge
            Edge e = sourceVertex.addEdge("route", targetVertex);
            e.property("dist", Route[4]);
        }

        // Commit the transaction
        tx.commit();
    }

    public static void addRelations(JanusGraph graph, List<String[]> Rels){
        GraphTraversalSource g = graph.traversal();
        JanusGraphTransaction tx = graph.newTransaction();
        for(String[] Rel: Rels) {
            Vertex sourceVertex = g.V().has("code", Rel[1]).next();
            Vertex targetVertex = g.V().has("code", Rel[2]).next();
            // Create the edge
            Edge e = sourceVertex.addEdge("route", targetVertex);
        }

        // Commit the transaction
        tx.commit();
    }


}
