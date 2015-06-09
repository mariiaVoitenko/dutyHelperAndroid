package com.voitenko.dutyhelper.API;

import com.voitenko.dutyhelper.models.Appointment;
import com.voitenko.dutyhelper.models.Duty;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Path;

public interface AppointmentAPI {
    @GET("/api/appointments/{id}")
    public void getAppointment(@Path("id") int id, Callback<Appointment> response);

    @GET("/api/appointments")
    public void getAll(Callback<ArrayList<Appointment>> response);

    @DELETE("/api/appointments/{id}")
    public void deleteAppointment(@Path("id") int id, Callback<Appointment> response);
}
