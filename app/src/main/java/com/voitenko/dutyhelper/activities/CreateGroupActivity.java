package com.voitenko.dutyhelper.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.voitenko.dutyhelper.API.AppointmentAPI;
import com.voitenko.dutyhelper.API.GroupsAPI;
import com.voitenko.dutyhelper.API.MembershipAPI;
import com.voitenko.dutyhelper.API.UsersAPI;
import com.voitenko.dutyhelper.BL.DataConverter;
import com.voitenko.dutyhelper.BL.ServiceGenerator;
import com.voitenko.dutyhelper.ConstantsContainer;
import com.voitenko.dutyhelper.R;
import com.voitenko.dutyhelper.models.Group;
import com.voitenko.dutyhelper.models.Membership;
import com.voitenko.dutyhelper.models.Status;
import com.voitenko.dutyhelper.models.User;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class CreateGroupActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        Button createButton = (Button) findViewById(R.id.create_group_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameEditText = (EditText) findViewById(R.id.group_name);
                final String name = nameEditText.getText().toString();
                createGroup(name);
            }
        });

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
            Intent intent = new Intent(CreateGroupActivity.this, RPLoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_duties) {
            Intent intent = new Intent(CreateGroupActivity.this, DutiesListActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_home) {
            Intent intent = new Intent(CreateGroupActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_emergency) {
            Intent intent = new Intent(CreateGroupActivity.this, EmergencyDutiesListActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.action_groups) {
            Intent intent = new Intent(CreateGroupActivity.this, GroupListActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createGroup(String groupName) {
        final String name = groupName;
        final ServiceGenerator serviceGenerator = new ServiceGenerator();
        final GroupsAPI groupsAPI = serviceGenerator.createService(GroupsAPI.class, ConstantsContainer.ENDPOINT);
        final UsersAPI usersAPI = serviceGenerator.createService(UsersAPI.class, ConstantsContainer.ENDPOINT);
        final MembershipAPI membershipAPI = serviceGenerator.createService(MembershipAPI.class, ConstantsContainer.ENDPOINT);
        groupsAPI.setGroup(new Group(name), new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                final Status status = new Status(1044, "boss");
                groupsAPI.getAll(new Callback<ArrayList<Group>>() {
                    @Override
                    public void success(ArrayList<Group> groups, Response response) {
                        int id = 0;
                        for (Group g : groups) {
                            if (g.getName().equals(name)) {
                                id = g.getId();
                            }
                        }
                        final Group group = new Group(id, name);
                        usersAPI.getUserByEmail(DataConverter.getEmail(), new Callback<User>() {
                            @Override
                            public void success(User user, Response response) {
                                final User currentUser = user;
                                Membership membership = new Membership(status, currentUser, group);
                                membershipAPI.setMembership(membership, new Callback<String>() {
                                    @Override
                                    public void success(String s, Response response) {
                                        Intent intent = new Intent(CreateGroupActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        Log.d("Membership", "DONE");
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        Log.d("Membership", "NOT DONE");
                                    }
                                });
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.d("Email", "NOT DONE");
                            }
                        });
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("Group", "NOT DONE");
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Group creation", "NOT DONE");
            }
        });
    }
}
