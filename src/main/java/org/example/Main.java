package org.example;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import java.util.*;
import org.janusgraph.core.*;
import org.apache.commons.configuration.BaseConfiguration;
import org.example.JanusGraphExample;
import java.util.ArrayList;
import java.util.List;
import org.example.CSVtographdata;
import org.janusgraph.graphdb.internal.JanusGraphSchemaCategory;


public class Main {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        // Press Opt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
//        System.out.printf("Hello and welcome!");


        JanusGraph graph = JanusGraphExample.init();
        System.out.println("mile stone 1");

        List<String[]> airports = CSVtographdata.getAirports();
        long total_load_time = 0;
        long startTime = System.currentTimeMillis();
        JanusGraphExample.addAirports(graph, airports);
        long endTime = System.currentTimeMillis();
        total_load_time += endTime - startTime;

        List<String[]> countries = CSVtographdata.getCountries();
        startTime = System.currentTimeMillis();
        JanusGraphExample.addCountries(graph, countries);
        endTime = System.currentTimeMillis();
        total_load_time += endTime - startTime;

        List<String[]> continents = CSVtographdata.getContinents();
        startTime = System.currentTimeMillis();
        JanusGraphExample.addContinents(graph, continents);
        endTime = System.currentTimeMillis();
        total_load_time += endTime - startTime;

        List<String[]> Routes = CSVtographdata.getRoutes();
        startTime = System.currentTimeMillis();
        JanusGraphExample.addRoutes(graph, Routes);
        endTime = System.currentTimeMillis();
        total_load_time += endTime - startTime;

        List<String[]> Rel = CSVtographdata.getContainsRelationships();
        startTime = System.currentTimeMillis();
        JanusGraphExample.addRelations(graph, Rel);
        endTime = System.currentTimeMillis();
        total_load_time += endTime - startTime;

//        long startTime = System.currentTimeMillis();
        JanusGraphExample.view_graph(graph);
        System.out.println("Total Load Time Excluding fetching data from CSV is: " + total_load_time + " milliseconds");
        graph.close();
        return;

    }
}