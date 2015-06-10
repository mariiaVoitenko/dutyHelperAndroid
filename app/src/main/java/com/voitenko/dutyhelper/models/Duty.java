package com.voitenko.dutyhelper.models;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Duty {

    @Expose
    private Integer id;
    @Expose
    private String name;
    @Expose
    private Object description;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("can_change")
    @Expose
    private Object canChange;
    @SerializedName("is_done")
    @Expose
    private Object isDone;
    @Expose
    private Priority priority;
    @Expose
    private Category category;

    public Duty() {

    }

    public Duty(String name, Category category, Priority priority, String description, String startDate, String endDate, String canChange, String isDone) {
        this.name = name;
        this.category = category;
        this.priority = priority;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.canChange = canChange;
        this.isDone = isDone;
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

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Object getCanChange() {
        return canChange;
    }

    public void setCanChange(Object canChange) {
        this.canChange = canChange;
    }

    public Object getIsDone() {
        return isDone;
    }

    public void setIsDone(Object isDone) {
        this.isDone = isDone;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}