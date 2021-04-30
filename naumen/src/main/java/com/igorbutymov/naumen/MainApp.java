package com.igorbutymov.naumen;

public class MainApp {
    public static void main(String[] args) {
        MyRouteFinder myRouteFinder = new MyRouteFinder();

        char[][] map = {{'.', '.', '.', '@', '.'},
                        {'.', '#', '#', '#', '#'},
                        {'.', '.', '.', '.', '.'},
                        {'#', '#', '#', '#', '.'},
                        {'.', 'X', '.', '.', '.'}};

        myRouteFinder.findRoute(map);
    }
}
