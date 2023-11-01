package org.example;

import com.opencsv.CSVReader;
import com.sun.xml.internal.bind.v2.TODO;

import java.io.FileReader;
import org.example.JanusGraphExample;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class CSVtographdata {

    static Map<Integer, String> IdToAirport = new HashMap<>();
    static Map<Integer, String> IdToCountryAndContinet = new HashMap<>();

    static String current_dir = System.getProperty("user.dir");
    static String nodes_path = current_dir + "/data_sets/air-routes-latest-nodes.csv";
    static String edges_path = current_dir + "/data_sets/air-routes-latest-edges.csv";
    public static List<String[]> getAirports() {
        List<String[]> airports = new ArrayList<>();
        try {

            CSVReader reader = new CSVReader(new FileReader(nodes_path));
            String[] nextLine;
            // 1	airport	airport	ATL	KATL	Hartsfield - Jackson Atlanta International Airport	US-GA	5	12390	1026	US	Atlanta	33.6366996765137	-84.4281005859375

            while ((nextLine = reader.readNext()) != null) {
                if(nextLine[1].equals( "airport")){
                    airports.add(nextLine);
                    IdToAirport.put(Integer.valueOf(nextLine[0]), nextLine[3]);
                }
            }
        }  catch (Exception e){
            System.out.println(e);
        }

        return airports;
//        JanusGraphExample.DoneAddingVertices();

    }

    public static List<String[]> getCountries() {

        List<String[]> countries = new ArrayList<>();
        try {
            //TODO: change path to global
            CSVReader reader = new CSVReader(new FileReader(nodes_path));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if(nextLine[1].equals( "country")) {
                    IdToCountryAndContinet.put(Integer.valueOf(nextLine[0]), nextLine[3]);
                    countries.add(nextLine);
                }
            }
        }  catch (Exception e){
            System.out.println(e);
        }

        return countries;
    }

    public static List<String[]> getContinents() {

        List<String[]> continents = new ArrayList<>();
        try {
            //TODO: change path to global
            CSVReader reader = new CSVReader(new FileReader(nodes_path));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if(nextLine[1].equals( "continent")) {
                    IdToCountryAndContinet.put(Integer.valueOf(nextLine[0]), nextLine[3]);
                    continents.add(nextLine);
                }
            }
        }  catch (Exception e){
            System.out.println(e);
        }

        return continents;
    }

    public static List<String[]> getRoutes() {

        List<String[]> routes = new ArrayList<>();
        try {
            //TODO: change path to global
            CSVReader reader = new CSVReader(new FileReader(edges_path));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if(nextLine[3].equals("route")) {
                    String Source = IdToAirport.get(Integer.parseInt(nextLine[1]));
                    String Dest = IdToAirport.get(Integer.parseInt(nextLine[2]));
                    nextLine[1] = Source;
                    nextLine[2] = Dest;
                    routes.add(nextLine);
                }
            }
        }  catch (Exception e){
            System.out.println(e);
        }

        return routes;
    }

    public static List<String[]> getContainsRelationships() {

        List<String[]> Rel = new ArrayList<>();
        try {
            //TODO: change path to global
            CSVReader reader = new CSVReader(new FileReader(edges_path));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if(nextLine[3].equals("contains")) {
                    String Source = IdToCountryAndContinet.get(Integer.parseInt(nextLine[1]));
                    String Dest = IdToAirport.get(Integer.parseInt(nextLine[2]));
                    nextLine[1] = Source;
                    nextLine[2] = Dest;
                    Rel.add(nextLine);
                }
            }
        }  catch (Exception e){
            System.out.println(e);
        }

        return Rel;
    }

}
