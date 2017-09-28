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

    public String getType() {
        return _type;
    }

    public String getSubType() {
        return _subtype;
    }

    public String getName() {
        return _name;
    }
}
