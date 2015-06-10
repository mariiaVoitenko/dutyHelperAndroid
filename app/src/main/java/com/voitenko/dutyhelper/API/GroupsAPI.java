package com.voitenko.dutyhelper.API;

import com.voitenko.dutyhelper.models.Category;
import com.voitenko.dutyhelper.models.Duty;
import com.voitenko.dutyhelper.models.Group;

import retrofit.Callback;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface GroupsAPI {
    @GET("/api/user_groups/{id}")
    public void getGroup(@Path("id") int id, Callback<Group> response);

    @POST("/api/user_groups")
    public Group setGroup(Callback<Group> response);

    @DELETE("/api/user_groups/{id}")
    public void deleteGroup(@Path("id") int id, Callback<Group> response);
}
