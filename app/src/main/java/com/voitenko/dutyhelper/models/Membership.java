package com.voitenko.dutyhelper.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Membership {

    @Expose
    private Integer id;
    @Expose
    private Status status;
    @Expose
    private User user;
    @SerializedName("user_group")
    @Expose
    private Group userGroup;

    public Membership(Integer id, Group userGroup, User user, Status status) {
        this.id = id;
        this.userGroup = userGroup;
        this.user = user;
        this.status = status;
    }

    public Membership(Status status, User user, Group userGroup) {

        this.status = status;
        this.user = user;
        this.userGroup = userGroup;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(Group userGroup) {
        this.userGroup = userGroup;
    }

}


