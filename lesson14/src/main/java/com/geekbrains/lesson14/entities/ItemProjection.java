package com.geekbrains.lesson14.entities;

//если хотим доставать только часть объекта
public interface ItemProjection {
    String title();
    int cost();
}
