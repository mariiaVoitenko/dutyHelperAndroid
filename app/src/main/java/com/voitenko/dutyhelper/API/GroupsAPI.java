package com.voitenko.dutyhelper.API;

import com.voitenko.dutyhelper.models.Category;
import com.voitenko.dutyhelper.models.Duty;
import com.voitenko.dutyhelper.models.Group;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface GroupsAPI {
    @GET("/api/user_groups/{id}")
    public void getGroup(@Path("id") int id, Callback<Group> response);

    @POST("/api/user_groups")
    public void setGroup(@Body Group group,Callback<String> response);

    @PUT("/api/user_groups")
    public void  editGroup(@Body Group group, Callback<String> response);

    @DELETE("/api/user_groups/{id}")
    public void deleteGroup(@Path("id") int id, Callback<Group> response);

    @GET("/api/user_groups")
    public void getAll(Callback<ArrayList<Group>> response);
}
