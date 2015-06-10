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

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class GroupListActivity extends ActionBarActivity {

    int userId;
    List<Membership> memberships = new ArrayList<>();
    TextView mIDTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);
        mIDTextView = (TextView) findViewById(R.id.txtgroupID);
        userId = Integer.parseInt(getIntent().getExtras().getString(ConstantsContainer.USER_ID));
        mIDTextView.setText(Integer.toString(userId));
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
            TextView mIDTextView = (TextView) findViewById(R.id.txtgroupID);
            Intent intent = new Intent(GroupListActivity.this, DutiesListActivity.class);
            intent.putExtra(ConstantsContainer.USER_ID, mIDTextView.getText());
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_home) {
            TextView mIDTextView = (TextView) findViewById(R.id.txtgroupID);
            Intent intent = new Intent(GroupListActivity.this, MainActivity.class);
            intent.putExtra(ConstantsContainer.USER_ID, mIDTextView.getText());
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_emergency) {
            TextView mIDTextView = (TextView) findViewById(R.id.txtgroupID);
            Intent intent = new Intent(GroupListActivity.this, EmergencyDutiesListActivity.class);
            intent.putExtra(ConstantsContainer.USER_ID, mIDTextView.getText());
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
