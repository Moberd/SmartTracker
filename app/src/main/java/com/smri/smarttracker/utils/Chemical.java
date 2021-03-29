package com.smri.smarttracker.utils;

public class Chemical {

    private String name;
    private String creator;
    private String description;
    private String id;

    public Chemical(String name, String creator, String description, String id){
        this.name = name;
        this.creator = creator;
        this.description = description;
        this.id = id;
    }

    public Chemical(String name, String description){
        this.name = name;
        this.description = description;
        creator = "";
        id = "-";
    }

    public Chemical(String id, String name, String description){
        this.name = name;
        this.description = description;
        creator = "";
        this.id = id;
    }

    public Chemical(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
