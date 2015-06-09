package com.voitenko.dutyhelper.API;

import com.voitenko.dutyhelper.models.Duty;
import com.voitenko.dutyhelper.models.User;

import retrofit.Callback;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Path;

public interface DutiesAPI {
    @GET("/api/dutys/{id}")
    public void getDuty(@Path("id") int id, Callback<Duty> response);

    @DELETE("/api/dutys/{id}")
    public void deleteDuty(@Path("id") int id, Callback<Duty> response);
}