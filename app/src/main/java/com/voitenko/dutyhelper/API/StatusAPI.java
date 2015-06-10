package com.voitenko.dutyhelper.API;

import com.voitenko.dutyhelper.models.Category;
import com.voitenko.dutyhelper.models.Status;

import retrofit.Callback;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface StatusAPI {
    @GET("/api/statuss/{id}")
    public void getStatus(@Path("id") int id, Callback<Status> response);

    @POST("/api/statuss")
    public Category setStatus(Callback<Status> response);

    @DELETE("/api/statuss/{id}")
    public void deleteStatus(@Path("id") int id, Callback<Status> response);
}
