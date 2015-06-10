package com.voitenko.dutyhelper.API;

import com.voitenko.dutyhelper.models.Category;
import com.voitenko.dutyhelper.models.Priority;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface PrioritiesAPI {
    @GET("/api/prioritys/{id}")
    public void getPriority(@Path("id") int id, Callback<Priority> response);

    @POST("/api/prioritys")
    public Priority setPriority(Callback<Priority> response);

    @DELETE("/api/prioritys/{id}")
    public void deletePriority(@Path("id") int id, Callback<Priority> response);

    @GET("/api/prioritys")
    public void getAll(Callback<ArrayList<Priority>> response);
}
