package com.rogmax.amirjaber.taskrogmax22.models;

/**
 * Created by Amir Jaber on 9/27/2017.
 */

public class Pet {

    private String type, subtype, name;
    private int id;

    public Pet(int id, String type, String subtype, String name) {
        this.id = id;
        this.type = type;
        this.subtype = subtype;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
