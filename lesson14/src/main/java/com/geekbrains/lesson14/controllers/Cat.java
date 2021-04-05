package com.geekbrains.lesson14.controllers;

import lombok.Data;

@Data
public class Cat {
    private Long id;
    private String name;
    private String color;

    public Cat(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }
}
