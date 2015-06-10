package com.voitenko.dutyhelper.models;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Category {

    @Expose
    private Integer id;
    @Expose
    private String name;

    public Category() {

    }

    public Category( String name) {
        this.name = name;
    }

    public Category(int id, String name) {
        this.id=id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}