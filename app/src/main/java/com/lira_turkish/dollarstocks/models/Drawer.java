package com.lira_turkish.dollarstocks.models;

public class Drawer {
    //veriables
    private int id;
    private String name;
    private int resource;
    //methods

    public Drawer(int id, String name, int resource) {
        this.id = id;
        this.name = name;
        this.resource = resource;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getResource() {
        return resource;
    }


    //tostring

    @Override
    public String toString() {
        return "Drawer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", resource=" + resource +
                '}';
    }
}