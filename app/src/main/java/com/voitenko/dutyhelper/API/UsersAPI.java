package com.voitenko.dutyhelper.API;

import com.voitenko.dutyhelper.models.User;

import retrofit.Callback;

import retrofit.http.GET;
import retrofit.http.Path;

public interface UsersAPI {

    @GET("/api/users/id/{id}")
    public void getUser(@Path("id") int id, Callback<User> response);

    @GET("/api/users/email?email=")
    public void getUserByEmail(@Path("email") String email, Callback<User> response);

    @GET("/api/users/login/{login}")
    public void getUserByLogin(@Path("login") String login, Callback<User> response);

    @GET("/api/users/validate/{login}")
    public void getPasswordByLogin(@Path("login") String login, Callback<User> response);


}