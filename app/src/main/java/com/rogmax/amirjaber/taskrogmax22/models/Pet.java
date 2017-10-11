package com.rogmax.amirjaber.taskrogmax22.models;

/**
 * Created by Amir Jaber on 9/27/2017.
 */

public class Pet {

    private String _type, _subtype, _name;
    private int _id;

    public Pet(int id, String type, String subtype, String name) {
        _id = id;
        _type = type;
        _subtype = subtype;
        _name = name;
    }

    public int getId() {
        return _id;
    }

    public String getType() {
        return _type;
    }

    public String getSubType() {
        return _subtype;
    }

    public String getName() {
        return _name;
    }


    public void set_type(String newType) {
        this._type = newType;
    }

    public void set_subtype(String newSubtype) {
        this._subtype = newSubtype;
    }

    public void set_name(String newName) {
        this._name = newName;
    }

    @Override
    public String toString() {
        return this._name;
    }

}
