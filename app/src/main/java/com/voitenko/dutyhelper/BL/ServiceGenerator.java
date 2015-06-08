package com.voitenko.dutyhelper.BL;


import retrofit.RestAdapter;

public class ServiceGenerator {

    public static <T> T createService(Class<T> service, String endpoint) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .setLogLevel(RestAdapter.LogLevel.NONE).build();
        return restAdapter.create(service);
    }

}