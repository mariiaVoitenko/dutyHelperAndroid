package com.voitenko.dutyhelper.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.voitenko.dutyhelper.BL.UsersAPI;
import com.voitenko.dutyhelper.R;
import com.voitenko.dutyhelper.BL.UserManager;
import com.voitenko.dutyhelper.models.User;
import com.voitenko.dutyhelper.models.gitmodel;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!UserManager.isProfileValid(getApplicationContext())) {
            Intent loginIntent = new Intent(MainActivity.this, RPLoginActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(loginIntent);


        } else {
            TextView helloTextView = (TextView) findViewById(R.id.hello_textview);
            helloTextView.setText("Greetings, " + UserManager.getName());

        }

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://10.0.3.2:8080").setLogLevel(RestAdapter.LogLevel.NONE).build();
        UsersAPI usersAPI = restAdapter.create(UsersAPI.class);
        usersAPI.getUser(4, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                Log.d("RESTOFIT_NORM!!!!!", user.getEmail());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("RESTOFIT_ERROR!!!!!", error.getMessage());
                error.printStackTrace();
                Log.d("RESTOFIT_ERROR!!!!!", "");
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
            Intent loginIntent = new Intent(MainActivity.this, RPLoginActivity.class);
            startActivity(loginIntent);
            finish();
            return true;
        }
        if (id == R.id.action_home) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
