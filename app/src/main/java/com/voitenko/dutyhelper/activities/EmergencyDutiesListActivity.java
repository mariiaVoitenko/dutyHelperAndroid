package com.voitenko.dutyhelper.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v4.app.NotificationCompat;
import android.widget.TextView;

import com.voitenko.dutyhelper.API.AppointmentAPI;
import com.voitenko.dutyhelper.BL.DataConverter;
import com.voitenko.dutyhelper.BL.ServiceGenerator;
import com.voitenko.dutyhelper.ConstantsContainer;
import com.voitenko.dutyhelper.R;
import com.voitenko.dutyhelper.models.Appointment;
import com.voitenko.dutyhelper.models.Duty;
import com.voitenko.dutyhelper.structures.DutyListAdapter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class EmergencyDutiesListActivity extends ActionBarActivity {
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_duties_list);
        userId = DataConverter.getId();
        getUrgentDuties();
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
            Intent intent = new Intent(EmergencyDutiesListActivity.this, RPLoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_duties) {
            Intent intent = new Intent(EmergencyDutiesListActivity.this, DutiesListActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_home) {
            Intent intent = new Intent(EmergencyDutiesListActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_groups) {
            Intent intent = new Intent(EmergencyDutiesListActivity.this, GroupListActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_emergency) {
            Intent intent = new Intent(EmergencyDutiesListActivity.this, GroupListActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getUrgentDuties() {
        ServiceGenerator serviceGenerator = new ServiceGenerator();
        final AppointmentAPI appointmentAPI = serviceGenerator.createService(AppointmentAPI.class, ConstantsContainer.ENDPOINT);
        appointmentAPI.getAll(
                new Callback<ArrayList<Appointment>>() {
                    @Override
                    public void success(ArrayList<Appointment> result, Response response) {
                        ArrayList<Duty> items = new ArrayList<>();
                        for (Appointment a : result) {
                            if (a.getUser().getId().equals(userId)) {
                                Date date = null;
                                try {
                                    date = DataConverter.parseDate(DataConverter.getDate(a.getDuty().getEndDate()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Date now = new Date();
                                Date dayPlus = new Date(now.getTime() + (1000 * 60 * 60 * 24));
                                if (dayPlus.getTime() > date.getTime() ) {
                                    if (a.getDuty().getIsDone()== null) {
                                        items.add(a.getDuty());
                                    }
                                    else{
                                        if(a.getDuty().getIsDone().equals(false)) {
                                            items.add(a.getDuty());
                                        }
                                    }
                                }
                            }
                        }
                        final DutyListAdapter adapter = new DutyListAdapter(EmergencyDutiesListActivity.this, items);
                        ListView listView = (ListView) findViewById(R.id.listview);
                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position,
                                                    long id) {
                                Duty duty = adapter.getItem(position);
                                TextView textView = (TextView) findViewById(R.id.txtdutyID);
                                textView.setText(duty.getId() + "");
                                Intent intent = new Intent(EmergencyDutiesListActivity.this, DutyDetailsActivity.class);
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
}
