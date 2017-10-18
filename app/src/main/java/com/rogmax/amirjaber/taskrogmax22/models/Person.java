package com.rogmax.amirjaber.taskrogmax22.models;

/**
 * Created by Amir Jaber on 10/3/2017.
 */

public class Person {

    private String name, age, pet;
    private int id;
    private int petId;

    public Person(int id, String name, String age, String pet, int petId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.pet = pet;
        this.petId = petId;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getPet() {
        return pet;
    }

    public int getPetId() {
        return petId;
    }


}
