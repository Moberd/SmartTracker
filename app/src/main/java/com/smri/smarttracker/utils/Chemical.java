package com.smri.smarttracker.utils;

import java.util.GregorianCalendar;

public class Chemical {

    private String name;
    private String creator;
    private String description;
    private String id;
    private String location;
    private String createTime;

    public Chemical(String id, String name, String description, String location, String creator) {
        this.name = name;
        this.creator = creator;
        this.description = description;
        this.id = id;
        this.location = location;
    }

    public Chemical(String id, String name, String description, String location, String creator, String createTime) {
        this.name = name;
        this.creator = creator;
        this.description = description;
        this.id = id;
        this.location = location;
        this.createTime = createTime;
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

    public Chemical(String id, String name, String description, String location){
        this.name = name;
        this.description = description;
        creator = "";
        this.id = id;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
