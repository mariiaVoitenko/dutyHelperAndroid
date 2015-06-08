package com.voitenko.dutyhelper.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class User {

    @Expose
    private Integer id;
    @Expose
    private String login;
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private String email;
    @Expose
    private Boolean activated;
    @Expose
    private String langKey;
    @Expose
    private Object resetKey;
    @Expose
    private Object resetDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Object getResetKey() {
        return resetKey;
    }

    public void setResetKey(Object resetKey) {
        this.resetKey = resetKey;
    }

    public Object getResetDate() {
        return resetDate;
    }

    public void setResetDate(Object resetDate) {
        this.resetDate = resetDate;
    }

}