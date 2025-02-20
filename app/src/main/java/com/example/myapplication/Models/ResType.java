package com.example.myapplication.Models;

public class ResType {
    private String typeID;
    private String type;

    public ResType(String typeID, String type) {
        this.typeID = typeID;
        this.type = type;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
