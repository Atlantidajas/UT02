package com.practices.jorge.ut02.models;

public class User {
    private int id;
    private String name;

    public User(String name ) {
        this.name = name;
    }

    public User(int id, String nombre) {
        this.id = id;
        this.name = nombre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
