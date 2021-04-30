package com.igorbutymov.naumen;

import lombok.NoArgsConstructor;

import java.util.ArrayList;

public class Vertex implements Comparable<Vertex> {
    public final int x;
    public final int y;
    public ArrayList<Edge> adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;

    public Vertex(int x, int y) {
        adjacencies = new ArrayList<>();
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "[x = " + x + "; y = " + y + "]";
    }

    @Override
    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }
}
