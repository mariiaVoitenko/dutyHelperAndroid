package com.voitenko.dutyhelper.API;

import com.voitenko.dutyhelper.models.Membership;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Path;

public interface MembershipAPI {
    @GET("/api/memberships/{id}")
    public void getMembership(@Path("id") int id, Callback<Membership> response);

    @GET("/api/memberships")
    public void getAll(Callback<ArrayList<Membership>> response);

    @DELETE("/api/memberships/{id}")
    public void deleteAppointment(@Path("id") int id, Callback<Membership> response);
}
