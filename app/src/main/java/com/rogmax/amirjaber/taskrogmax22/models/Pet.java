package com.rogmax.amirjaber.taskrogmax22.models;

/**
 * Created by Amir Jaber on 9/27/2017.
 */

public class Pet {

    private String _type, _subtype, _name;

    public Pet (String type, String subtype, String name){
        _type = type;
        _subtype = subtype;
        _name = name;
    }

    public String get_type() {
        return _type;
    }

    public String get_subtype() {
        return _subtype;
    }

    public String get_name() {
        return _name;
    }
}
