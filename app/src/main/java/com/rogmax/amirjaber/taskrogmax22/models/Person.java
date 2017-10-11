package com.rogmax.amirjaber.taskrogmax22.models;

/**
 * Created by Amir Jaber on 10/3/2017.
 */

public class Person {

    private String _name, _age, _pet;
    private int _id, _petId;

    public Person(int id, String name, String age, String pet, int petId) {
        _id = id;
        _name = name;
        _age = age;
        _pet = pet;
        _petId = petId;
    }

    public String get_name() {
        return _name;
    }

    public String get_age() {
        return _age;
    }

    public String get_pet() {
        return _pet;
    }

    public int get_petId() {
        return _petId;
    }

    public int get_id() {
        return _id;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_age(String _age) {
        this._age = _age;
    }

    public void set_pet(String _pet) {
        this._pet = _pet;
    }


}
