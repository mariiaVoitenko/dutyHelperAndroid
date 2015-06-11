package com.voitenko.dutyhelper.activities;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.opengl.Visibility;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.voitenko.dutyhelper.API.AppointmentAPI;
import com.voitenko.dutyhelper.API.UsersAPI;
import com.voitenko.dutyhelper.BL.DataConverter;
import com.voitenko.dutyhelper.BL.ServiceGenerator;
import com.voitenko.dutyhelper.BL.UserManager;
import com.voitenko.dutyhelper.ConstantsContainer;
import com.voitenko.dutyhelper.R;
import com.voitenko.dutyhelper.models.Appointment;
import com.voitenko.dutyhelper.models.Duty;
import com.voitenko.dutyhelper.models.User;
import com.voitenko.dutyhelper.structures.DutyListAdapter;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {
    private Button mCreateGroupButton;
    private Button mCreateDutyButton;
    private int userId;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCreateGroupButton = (Button) findViewById(R.id.group_button);
        mCreateDutyButton = (Button) findViewById(R.id.duty_button);

        if (!UserManager.isProfileValid(getApplicationContext())) {
            Intent loginIntent = new Intent(MainActivity.this, RPLoginActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(loginIntent);
        } else {
            TextView helloTextView = (TextView) findViewById(R.id.hello_textview);
            helloTextView.setText("Greetings, " + UserManager.getName());
        }

        email = DataConverter.getEmail();
        writeUserId();
        userId = DataConverter.getId();
        sendNotification();

        mCreateDutyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateDutyActivity.class));
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
            Intent intent = new Intent(MainActivity.this, RPLoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_duties) {
            Intent intent = new Intent(MainActivity.this, DutiesListActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_emergency) {
            Intent intent = new Intent(MainActivity.this, EmergencyDutiesListActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_groups) {
            Intent intent = new Intent(MainActivity.this, GroupListActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_home) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void writeUserId() {
        ServiceGenerator serviceGenerator = new ServiceGenerator();
        UsersAPI usersAPI = serviceGenerator.createService(UsersAPI.class, ConstantsContainer.ENDPOINT);

        usersAPI.getUserByEmail(email, new Callback<User>() {
                    @Override
                    public void success(User user, Response response) {
                        File idFile = new File(ConstantsContainer.FILEPATH_ID);
                        try {
                            DataConverter.writeFile(user.getId().toString(), idFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("RESTOFIT_ERROR!!!!!", error.getMessage());
                    }
                }

        );
    }

    private void sendNotification() {
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
                                if (dayPlus.getTime() > date.getTime()) {
                                    items.add(a.getDuty());
                                }
                            }
                        }
                        if (items.size() > 0) {
                            NotificationCompat.Builder mBuilder =
                                    new NotificationCompat.Builder(getApplicationContext())
                                            .setSmallIcon(R.drawable.moremedium)
                                            .setContentTitle("Duty Helper")

                                            .setContentText("You have some urgent duties!");
                            Intent resultIntent = new Intent(MainActivity.this, EmergencyDutiesListActivity.class);
                            PendingIntent resultPendingIntent =
                                    PendingIntent.getActivity(
                                            MainActivity.this,
                                            0,
                                            resultIntent,
                                            PendingIntent.FLAG_UPDATE_CURRENT
                                    );
                            mBuilder.setContentIntent(resultPendingIntent);
                            int mNotificationId = 001;
                            NotificationManager mNotifyMgr =
                                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            mNotifyMgr.notify(mNotificationId, mBuilder.build());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("!!!RESTOFIT_ERROR!!!!!", error.getMessage());
                    }
                }
        );
    }
}
