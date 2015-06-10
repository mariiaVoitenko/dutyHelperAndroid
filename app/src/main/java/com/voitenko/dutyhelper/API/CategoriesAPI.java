package com.voitenko.dutyhelper.API;

import com.voitenko.dutyhelper.models.Appointment;
import com.voitenko.dutyhelper.models.Category;
import com.voitenko.dutyhelper.models.Duty;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface CategoriesAPI {

    @GET("/api/categorys/{id}")
    public void getCategory(@Path("id") int id, Callback<Category> response);

    @POST("/api/categorys")
    public void setCategory(@Body Category category, Callback<String> response);

    @DELETE("/api/categorys/{id}")
    public void deleteCategory(@Path("id") int id, Callback<Category> response);

    @GET("/api/categorys")
    public void getAll(Callback<ArrayList<Category>> response);
}

