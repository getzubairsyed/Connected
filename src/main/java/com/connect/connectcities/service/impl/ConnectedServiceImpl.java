package com.connect.connectcities.service.impl;


import com.connect.connectcities.service.ConnectedService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service(value = "connectedService")
public class ConnectedServiceImpl implements ConnectedService {

    //to be clear with start and end destination
    private static String  START;
    private static String  END;

    ObjectMapper mapper = new ObjectMapper();

    public void addEdge(String node1, String node2, Map<String, LinkedHashSet<String>> map)
    {
        LinkedHashSet<String> adjacent = map.get(node1);
        if (adjacent == null)
        {
            adjacent = new LinkedHashSet();
            map.put(node1, adjacent);
        }
        adjacent.add(node2);
    }

    public void addTwoWayVertex(String node1, String node2, Map<String, LinkedHashSet<String>> map)
    {
        addEdge(node1, node2, map);
        addEdge(node2, node1, map);
    }

    public LinkedList<String> adjacentNodes(String last, Map<String, LinkedHashSet<String>> map)
    {
        LinkedHashSet<String> adjacent = map.get(last);
        if (adjacent == null)
        {
            return new LinkedList();
        }
        return new LinkedList<String>(adjacent);
    }

    private boolean breadthFirst( Map<String, LinkedHashSet<String>> map,
                              LinkedList<String> visited)
    {
        boolean flag = false;
        boolean retRes = false;
        LinkedList<String> nodes = adjacentNodes(visited.getLast(), map);

        for (String node : nodes)
        {
            if (visited.contains(node))
            {
                continue;
            }
            if (node.equalsIgnoreCase(END))
            {
                visited.add(node);
                retRes = checkConnection(visited, flag);
                flag = true;
                visited.removeLast();
                break;
            }
        }

        for (String node : nodes)
        {
            if (visited.contains(node) || node.equalsIgnoreCase(END))
            {
                continue;
            }
            visited.addLast(node);
            breadthFirst(map, visited);
            visited.removeLast();
        }
        if (flag == false)
        {
            System.out.println("No path Exists between " + START + " and "
                    + END);
            flag = true;
            retRes = false;
        }
        return retRes;
    }

    private boolean checkConnection(LinkedList<String> visited, boolean flag)
    {
        if (flag == false) {
            System.out.println("Yes there exists a path between " + START
                    + " and " + END);
            return true;
        }
        for (String node : visited)
        {
            System.out.print(node);
            System.out.print(" ");
        }
        System.out.println();
        return false;
    }

    @Override
    public boolean find(String city1,  String city2) {
        boolean con = false;
        String line;
        Map<String, LinkedHashSet<String>> map = new HashMap();
        try {

            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/cityroutes"));
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                String cts = mapper.writeValueAsString(parts);
//                if(cts.contains(city1) && cts.contains(city2)){
//                    continue;
//                }
                if (parts.length >= 2) {
                    String key = parts[0];
                    String value = parts[1];
                    addEdge(key, value, map);
                } else {
                    System.out.println("ignoring line: " + line);
                }
            }
            reader.close();

                LinkedList<String> visited = new LinkedList();

                START = city1;
                END = city2;

//            addTwoWayVertex(START, END, map);
                visited.add(START);
                con = breadthFirst(map, visited);
        }
        catch (Exception e){
            System.out.println("Exception Occurred in find method" + e.toString());
        }
        return con;
    }
}
