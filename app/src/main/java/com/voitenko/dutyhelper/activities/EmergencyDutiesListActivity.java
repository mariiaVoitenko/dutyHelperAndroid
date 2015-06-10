package com.voitenko.dutyhelper.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class EmergencyDutiesListActivity extends ActionBarActivity {
    int userId;
    TextView mIDTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_duties_list);
        mIDTextView = (TextView) findViewById(R.id.txtdutyID);
        userId = Integer.parseInt(getIntent().getExtras().getString(ConstantsContainer.USER_ID));
        mIDTextView.setText(Integer.toString(userId));
        ServiceGenerator serviceGenerator = new ServiceGenerator();
        final AppointmentAPI appointmentAPI = serviceGenerator.createService(AppointmentAPI.class, ConstantsContainer.ENDPOINT);
        appointmentAPI.getAll(
                new Callback<ArrayList<Appointment>>() {
                    @Override
                    public void success(ArrayList<Appointment> result, Response response) {
                        ArrayList<Duty> items = new ArrayList<>();
                        for (Appointment a : result) {
                            if (a.getUser().getId().equals(userId)) {
                                Log.d("!!!DATE_ERROR!!!!!", DataConverter.getDate(a.getDuty().getEndDate()));
                                Date date = null;
                                try {
                                    date = DataConverter.parseDate(DataConverter.getDate(a.getDuty().getEndDate()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Date now = new Date();
                                Date dayPlus = new Date(now.getTime() + (1000 * 60 * 60 * 24));
                                if (dayPlus.getTime() > date.getTime()) {
                                    items.add(a.getDuty());
                                }
                            }
                        }
                        final DutyListAdapter adapter = new DutyListAdapter(EmergencyDutiesListActivity.this, items);
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
            Intent intent = new Intent(EmergencyDutiesListActivity.this, RPLoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_duties) {
            mIDTextView = (TextView) findViewById(R.id.txtdutyID);
            Intent intent = new Intent(EmergencyDutiesListActivity.this, DutiesListActivity.class);
            intent.putExtra(ConstantsContainer.USER_ID, mIDTextView.getText());
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_home) {
            mIDTextView = (TextView) findViewById(R.id.txtdutyID);
            Intent intent = new Intent(EmergencyDutiesListActivity.this, MainActivity.class);
            intent.putExtra(ConstantsContainer.USER_ID, mIDTextView.getText());
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_groups) {
            mIDTextView = (TextView) findViewById(R.id.txtdutyID);
            Intent intent = new Intent(EmergencyDutiesListActivity.this, GroupListActivity.class);
            intent.putExtra(ConstantsContainer.USER_ID, mIDTextView.getText());
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_emergency) {
            mIDTextView = (TextView) findViewById(R.id.txtdutyID);
            Intent intent = new Intent(EmergencyDutiesListActivity.this, GroupListActivity.class);
            intent.putExtra(ConstantsContainer.USER_ID, mIDTextView.getText());
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
