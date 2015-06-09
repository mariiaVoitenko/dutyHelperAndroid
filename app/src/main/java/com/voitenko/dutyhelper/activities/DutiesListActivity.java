package com.voitenko.dutyhelper.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.voitenko.dutyhelper.API.AppointmentAPI;
import com.voitenko.dutyhelper.API.DutiesAPI;
import com.voitenko.dutyhelper.API.UsersAPI;
import com.voitenko.dutyhelper.BL.ServiceGenerator;
import com.voitenko.dutyhelper.ConstantsContainer;
import com.voitenko.dutyhelper.R;
import com.voitenko.dutyhelper.models.Appointment;
import com.voitenko.dutyhelper.models.Duty;
import com.voitenko.dutyhelper.models.User;
import com.voitenko.dutyhelper.structures.DutyListAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class DutiesListActivity extends ActionBarActivity {
    int userId;
    ArrayList<Duty> duties;
    List<Appointment> appointments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duty);
        userId = 4;/*getIntent().getExtras().getInt(ConstantsContainer.USER_ID);*/
        ServiceGenerator serviceGenerator = new ServiceGenerator();
        DutiesAPI dutiesAPI = serviceGenerator.createService(DutiesAPI.class, ConstantsContainer.ENDPOINT);
        final AppointmentAPI appointmentAPI = serviceGenerator.createService(AppointmentAPI.class, ConstantsContainer.ENDPOINT);
        appointmentAPI.getAppointment(1463, new Callback<Appointment>() {
                    @Override
                    public void success(Appointment result, Response response) {
                        Log.d("!!!RESTOFIT_NORM!!!!!", result.getDuty().getName());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("!!!RESTOFIT_ERROR!!!!!", error.getMessage());
                        error.printStackTrace();
                        Log.d("!!!RESTOFIT_ERROR!!!!!", "");
                    }
                }

        );
        appointmentAPI.getAll(new Callback<ArrayList<Appointment>>() {
                                  @Override
                                  public void success(ArrayList<Appointment> result, Response response) {
                                      ArrayList<Duty> items = new ArrayList<Duty>();
                                      for (Appointment a : result) {
                                          if(a.getUser().getId().equals(userId)) {
                                              items.add(a.getDuty());
                                          }
                                      }
                                      final DutyListAdapter adapter = new DutyListAdapter(DutiesListActivity.this, items);
                                      ListView listView = (ListView) findViewById(R.id.listview);
                                      listView.setAdapter(adapter);
                                  }

                                  @Override
                                  public void failure(RetrofitError error) {
                                      Log.d("!!!RESTOFIT_ERROR!!!!!", error.getMessage());
                                  }
                              }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            Intent intent = new Intent(DutiesListActivity.this, RPLoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_duties) {
            return true;
        }
        if (id == R.id.action_home) {
            Intent intent = new Intent(DutiesListActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
