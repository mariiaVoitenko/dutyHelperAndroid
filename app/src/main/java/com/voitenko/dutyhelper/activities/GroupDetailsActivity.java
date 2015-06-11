package com.voitenko.dutyhelper.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.voitenko.dutyhelper.API.DutiesAPI;
import com.voitenko.dutyhelper.API.GroupsAPI;
import com.voitenko.dutyhelper.BL.ServiceGenerator;
import com.voitenko.dutyhelper.ConstantsContainer;
import com.voitenko.dutyhelper.R;
import com.voitenko.dutyhelper.models.Group;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class GroupDetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);
        final Button saveButton = (Button) findViewById(R.id.edit_group_button);
        final int groupId = Integer.parseInt(getIntent().getStringExtra(ConstantsContainer.GROUP_ID));
        ServiceGenerator serviceGenerator = new ServiceGenerator();
        final GroupsAPI groupsAPI = serviceGenerator.createService(GroupsAPI.class, ConstantsContainer.ENDPOINT);
        groupsAPI.getGroup(groupId, new Callback<Group>() {
            @Override
            public void success(final Group group, Response response) {
                final EditText editText = (EditText) findViewById(R.id.group_name);
                editText.setText(group.getName().toString());

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Group editedGroup = new Group(groupId, editText.getText().toString());
                        groupsAPI.editGroup(editedGroup, new Callback<String>() {
                            @Override
                            public void success(String s, Response response) {
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "Group saved", Toast.LENGTH_SHORT);
                                toast.show();
                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {

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
            Intent intent = new Intent(GroupDetailsActivity.this, RPLoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_duties) {
            return true;
        }
        if (id == R.id.action_emergency) {
            Intent intent = new Intent(GroupDetailsActivity.this, EmergencyDutiesListActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_home) {
            Intent intent = new Intent(GroupDetailsActivity.this, EmergencyDutiesListActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_groups) {
            Intent intent = new Intent(GroupDetailsActivity.this, GroupListActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
