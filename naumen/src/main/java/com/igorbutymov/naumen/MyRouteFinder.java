package com.igorbutymov.naumen;

import java.util.*;

public class MyRouteFinder implements RouteFinder {

    private Vertex startVertex = null;
    private Vertex endVertex = null;

    private static void compute(Vertex source) {
        //Dijkstra algorithm
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<>();
        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();

            for (Edge e : u.adjacencies) {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    vertexQueue.remove(v);

                    v.minDistance = distanceThroughU;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
        }
    }

    private static ArrayList<Vertex> getShortestPathTo(Vertex target)
    {
        ArrayList<Vertex> path = new ArrayList<>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous) {
            path.add(vertex);
        }

        Collections.reverse(path);
        return path;
    }


    public char[][] findRoute(char[][] map) {
        ArrayList<Vertex> vertexes = new ArrayList<>();

        //find start vertex and end vertex
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '@') {
                    startVertex = new Vertex(j, i);
                    vertexes.add(startVertex);
                }
                if (map[i][j] == 'X') {
                    endVertex = new Vertex(j, i);
                }
            }
        }

        //find other vertexes
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (i == 0) {
                    if (map[i][j] == '.' && map[i + 1][j] == '.') {
                        vertexes.add(new Vertex(j, i));
                    }
                } else if (i == map.length - 1) {
                    if (map[i][j] == '.' && map[i - 1][j] == '.') {
                        vertexes.add(new Vertex(j, i));
                    }
                } else {
                    if ((map[i][j] == '.' && map[i + 1][j] == '.') || (map[i][j] == '.' && map[i - 1][j] == '.')) {
                        vertexes.add(new Vertex(j, i));
                    }
                }
            }
        }

        vertexes.add(endVertex);

        //find edges
        for (Vertex v1 : vertexes) {
            for (Vertex v2 : vertexes) {
                if (Math.abs(v1.y - v2.y) <= 1) {
                    //v1 and v2 are on the same row but not on the same column
                    if (v1.y == v2.y && v1.x != v2.x) {
                        v1.adjacencies.add(new Edge(v2, (Math.abs(v1.x - v2.x) + Math.abs(v1.y - v2.y))));
                    }
                    //v1 and v2 are not on the same row but on the same column
                    if (v1.y != v2.y && v1.x == v2.x) {
                        v1.adjacencies.add(new Edge(v2, (Math.abs(v1.x - v2.x) + Math.abs(v1.y - v2.y))));
                    }
                }
            }
        }

        compute(startVertex);
        ArrayList<Vertex> path = getShortestPathTo(endVertex);

        //output
        if (endVertex.minDistance == Double.POSITIVE_INFINITY) {
            System.out.println("null");
        } else {
            //filling the map
            for (int i = 0; i < path.size() - 1; i++) {
                //vertexes are on the same row
                if (path.get(i).y == path.get(i + 1).y) {
                    if (path.get(i + 1).x < path.get(i).x) {
                        for (int j = path.get(i + 1).x; j < path.get(i).x + 1; j++) {
                            map[path.get(i).y][j] = '+';
                        }
                    } else {
                        for (int j = path.get(i).x; j < path.get(i + 1).x + 1; j++) {
                            map[path.get(i).y][j] = '+';
                        }
                    }
                }
                //vertexes are on the same column
                if (path.get(i).x == path.get(i + 1).x) {
                    if (path.get(i + 1).y < path.get(i).y) {
                        for (int j = path.get(i + 1).y; j < path.get(i).y + 1; j++) {
                            map[j][path.get(i).x] = '+';
                        }
                    } else {
                        for (int j = path.get(i).y; j < path.get(i + 1).y + 1; j++) {
                            map[j][path.get(i).x] = '+';
                        }
                    }
                }
            }
            map[startVertex.y][startVertex.x] = '@';
            map[endVertex.y][endVertex.x] = 'X';

            //printing the map
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    System.out.print(map[i][j]);
                }
                System.out.println();
            }
        }
        return map;
    }
}
