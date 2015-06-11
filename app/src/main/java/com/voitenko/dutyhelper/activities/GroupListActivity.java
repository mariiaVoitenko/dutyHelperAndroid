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
import com.voitenko.dutyhelper.API.MembershipAPI;
import com.voitenko.dutyhelper.BL.DataConverter;
import com.voitenko.dutyhelper.BL.ServiceGenerator;
import com.voitenko.dutyhelper.ConstantsContainer;
import com.voitenko.dutyhelper.R;
import com.voitenko.dutyhelper.models.Appointment;
import com.voitenko.dutyhelper.models.Duty;
import com.voitenko.dutyhelper.models.Group;
import com.voitenko.dutyhelper.models.Membership;
import com.voitenko.dutyhelper.models.User;
import com.voitenko.dutyhelper.structures.DutyListAdapter;
import com.voitenko.dutyhelper.structures.GroupListAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class GroupListActivity extends ActionBarActivity {

    int userId;
    List<Membership> memberships = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);
        File file = new File(ConstantsContainer.FILEPATH_ID);
        try {
            userId = Integer.parseInt(DataConverter.readFile(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        getIntent().putExtra(ConstantsContainer.USER_ID,userId);

        ServiceGenerator serviceGenerator = new ServiceGenerator();
        final MembershipAPI membershipAPI = serviceGenerator.createService(MembershipAPI.class, ConstantsContainer.ENDPOINT);
        membershipAPI.getAll(
                new Callback<ArrayList<Membership>>() {
                    @Override
                    public void success(ArrayList<Membership> result, Response response) {
                        ArrayList<Group> items = new ArrayList<>();
                        for (Membership a : result) {
                            if (a.getUser().getId().equals(userId)) {
                                items.add(a.getUserGroup());
                            }
                        }
                        final GroupListAdapter adapter = new GroupListAdapter(GroupListActivity.this, items);
                        ListView listView = (ListView) findViewById(R.id.listviewGroup);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            Intent intent = new Intent(GroupListActivity.this, RPLoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_duties) {
            Intent intent = new Intent(GroupListActivity.this, DutiesListActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_home) {
            Intent intent = new Intent(GroupListActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_emergency) {
            Intent intent = new Intent(GroupListActivity.this, EmergencyDutiesListActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
