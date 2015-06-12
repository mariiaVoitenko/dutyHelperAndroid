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
import android.widget.TextView;

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


public class AddMemberActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        Button createButton = (Button) findViewById(R.id.add_member_button);
        final int groupId = Integer.parseInt(getIntent().getStringExtra(ConstantsContainer.GROUP_ID));

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailEditText = (EditText) findViewById(R.id.member_email);
                final String email = emailEditText.getText().toString();
                addMember(email, groupId);
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
            Intent intent = new Intent(AddMemberActivity.this, RPLoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_duties) {
            return true;
        }
        if (id == R.id.action_emergency) {
            Intent intent = new Intent(AddMemberActivity.this, EmergencyDutiesListActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_home) {
            Intent intent = new Intent(AddMemberActivity.this, EmergencyDutiesListActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_groups) {
            Intent intent = new Intent(AddMemberActivity.this, GroupListActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addMember(final String userEmail, int id) {
        final ServiceGenerator serviceGenerator = new ServiceGenerator();
        final GroupsAPI groupsAPI = serviceGenerator.createService(GroupsAPI.class, ConstantsContainer.ENDPOINT);
        final UsersAPI usersAPI = serviceGenerator.createService(UsersAPI.class, ConstantsContainer.ENDPOINT);
        final MembershipAPI membershipAPI = serviceGenerator.createService(MembershipAPI.class, ConstantsContainer.ENDPOINT);
        final int groupId = id;
        final Status status = new Status(1204, "plain");
        groupsAPI.getGroup(id, new Callback<Group>() {
            @Override
            public void success(Group group, Response response) {

                final Group newGroup = new Group(groupId, group.getName());
                usersAPI.getUserByEmail(userEmail, new Callback<User>() {
                    @Override
                    public void success(User user, Response response) {
                        final User currentUser = user;
                        Membership membership = new Membership(status, currentUser, newGroup);
                        membershipAPI.setMembership(membership, new Callback<String>() {
                            @Override
                            public void success(String s, Response response) {
                                Intent intent = new Intent(AddMemberActivity.this, GroupDetailsActivity.class);
                                final TextView textView = (TextView) findViewById(R.id.txtgroupID);
                                textView.setText(groupId + "");
                                intent.putExtra(ConstantsContainer.GROUP_ID, textView.getText().toString());
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


}

