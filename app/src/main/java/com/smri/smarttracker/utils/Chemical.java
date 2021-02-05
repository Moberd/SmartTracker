package com.smri.smarttracker.utils;

public class Chemical {

    String name;
    String creator;
    String description;
    int id;

    Chemical(String name, String creator, String description, int id){
        name = this.name;
        creator = this.creator;
        description = this.description;
        id = this.id;
    }

    Chemical(){}

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
