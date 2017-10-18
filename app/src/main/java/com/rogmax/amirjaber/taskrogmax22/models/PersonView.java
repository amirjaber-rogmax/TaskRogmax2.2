package com.rogmax.amirjaber.taskrogmax22.models;

/**
 * Created by Amir Jaber on 10/11/2017.
 */

public class PersonView {

    private int id;
    private String name, age;
    private String petName;

    public PersonView(int id, String name, String age, String petName) {
        this.name = name;
        this.age = age;
        this.id = id;
        this.petName = petName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPetName() {
        return petName;
    }

}
