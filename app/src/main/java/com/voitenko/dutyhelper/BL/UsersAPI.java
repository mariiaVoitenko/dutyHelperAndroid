package com.voitenko.dutyhelper.BL;

import com.voitenko.dutyhelper.models.User;

import retrofit.Callback;

import retrofit.http.GET;
import retrofit.http.Path;

public interface UsersAPI {

    @GET("/api/users/id/{id}")
    public void getUser(@Path("id") int id,Callback<User> response);

}