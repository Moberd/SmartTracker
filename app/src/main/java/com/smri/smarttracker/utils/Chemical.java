package com.smri.smarttracker.utils;

public class Chemical {

    public String name;
    public String creator;
    public String description;
    public int id;

    public Chemical(String name, String creator, String description, int id){
        this.name = name;
        this.creator = creator;
        this.description = description;
        this.id = this.id;
    }

    public Chemical(String name, String description){
        this.name = name;
        this.description = description;
        creator = "";
        id = 0;
    }

    public Chemical(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

}
