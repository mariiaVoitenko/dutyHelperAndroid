package com.voitenko.dutyhelper.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.voitenko.dutyhelper.API.AppointmentAPI;
import com.voitenko.dutyhelper.BL.DataConverter;
import com.voitenko.dutyhelper.BL.ServiceGenerator;
import com.voitenko.dutyhelper.ConstantsContainer;
import com.voitenko.dutyhelper.R;
import com.voitenko.dutyhelper.models.Appointment;
import com.voitenko.dutyhelper.models.Duty;
import com.voitenko.dutyhelper.structures.DutyListAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class DutiesListActivity extends ActionBarActivity {
    ArrayList<Duty> duties;
    List<Appointment> appointments = new ArrayList<>();
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duty);
        File file = new File(ConstantsContainer.FILEPATH_ID);
        try {
            userId = Integer.parseInt(DataConverter.readFile(file));
        } catch (IOException e) {
            e.printStackTrace();
        }


        ServiceGenerator serviceGenerator = new ServiceGenerator();
        final AppointmentAPI appointmentAPI = serviceGenerator.createService(AppointmentAPI.class, ConstantsContainer.ENDPOINT);
        appointmentAPI.getAll(
                new Callback<ArrayList<Appointment>>() {
                    @Override
                    public void success(ArrayList<Appointment> result, Response response) {
                        ArrayList<Duty> items = new ArrayList<>();
                        for (Appointment a : result) {
                            if (a.getUser().getId().equals(userId)) {
                                items.add(a.getDuty());
                            }
                        }
                        final DutyListAdapter dutyAdapter = new DutyListAdapter(DutiesListActivity.this, items);
                        ListView dutiesListView = (ListView) findViewById(R.id.listview);
                        dutiesListView.setAdapter(dutyAdapter);

                        dutiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position,
                                                    long id) {
                                Duty duty = dutyAdapter.getItem(position);
                                TextView textView=(TextView)findViewById(R.id.txtdutyID);
                                textView.setText(duty.getId()+"");
                                Intent intent = new Intent(DutiesListActivity.this, DutyDetailsActivity.class);
                                intent.putExtra(ConstantsContainer.DUTY_ID, textView.getText().toString());
                                startActivity(intent);
                            }
                        });
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
        if (id == R.id.action_emergency) {
            Intent intent = new Intent(DutiesListActivity.this, EmergencyDutiesListActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_home) {
            Intent intent = new Intent(DutiesListActivity.this, EmergencyDutiesListActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_groups) {
            Intent intent = new Intent(DutiesListActivity.this, GroupListActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
