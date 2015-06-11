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

import com.voitenko.dutyhelper.API.CategoriesAPI;
import com.voitenko.dutyhelper.API.DutiesAPI;
import com.voitenko.dutyhelper.API.PrioritiesAPI;
import com.voitenko.dutyhelper.API.UsersAPI;
import com.voitenko.dutyhelper.BL.DataConverter;
import com.voitenko.dutyhelper.BL.ServiceGenerator;
import com.voitenko.dutyhelper.ConstantsContainer;
import com.voitenko.dutyhelper.R;
import com.voitenko.dutyhelper.models.Category;
import com.voitenko.dutyhelper.models.Duty;
import com.voitenko.dutyhelper.models.Priority;
import com.voitenko.dutyhelper.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class CreateDutyActivity extends ActionBarActivity {
    Button mCreateDutyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_duty);
        mCreateDutyButton = (Button) findViewById(R.id.create_duty_button);

        mCreateDutyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDuty();
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
            Intent intent = new Intent(CreateDutyActivity.this, RPLoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_duties) {
            Intent intent = new Intent(CreateDutyActivity.this, DutiesListActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_home) {
            Intent intent = new Intent(CreateDutyActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_emergency) {
            Intent intent = new Intent(CreateDutyActivity.this, EmergencyDutiesListActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createDuty(){
        EditText priority = (EditText) findViewById(R.id.create_priority);
        EditText category = (EditText) findViewById(R.id.create_categoty);

        ServiceGenerator serviceGenerator = new ServiceGenerator();
        final CategoriesAPI categoriesAPI = serviceGenerator.createService(CategoriesAPI.class, ConstantsContainer.ENDPOINT);
        final PrioritiesAPI prioritiesAPI = serviceGenerator.createService(PrioritiesAPI.class, ConstantsContainer.ENDPOINT);
        final DutiesAPI dutiesAPI = serviceGenerator.createService(DutiesAPI.class, ConstantsContainer.ENDPOINT);
        final String categoryName = category.getText().toString();
        final String priorityName = priority.getText().toString();

        categoriesAPI.getAll(
                new Callback<ArrayList<Category>>() {
                    @Override
                    public void success(ArrayList<Category> categories, Response response) {
                        int idC = 0;
                        String nameC = "";
                        for (Category c : categories) {
                            if (c.getName().equals(categoryName)) {
                                idC = c.getId();
                                nameC = c.getName();

                            }
                        }
                        final String categoryName = nameC;
                        final int categoryId = idC;
                        prioritiesAPI.getAll(
                                new Callback<ArrayList<Priority>>() {
                                    int id;
                                    String name;

                                    @Override
                                    public void success(ArrayList<Priority> priorities, Response response) {
                                        for (Priority p : priorities) {
                                            if (p.getName().equals(priorityName)) {
                                                id = p.getId();
                                                name = p.getName();
                                                Log.d("RESTOFIT_PRIORITY!!!!!", p.getName());
                                            }
                                        }
                                        Priority priority = new Priority(id, name);
                                        Category category = new Category(categoryId, categoryName);

                                        EditText dutyName = (EditText) findViewById(R.id.create_name);
                                        EditText startDate = (EditText) findViewById(R.id.create_date_start);
                                        EditText endDate = (EditText) findViewById(R.id.create_date_end);
                                        EditText description = (EditText) findViewById(R.id.create_description);

                                        Duty duty = new Duty(
                                                dutyName.getText().toString(),
                                                category,
                                                priority,
                                                description.getText().toString(),
                                                DataConverter.setRightTime(startDate.getText().toString()),
                                                DataConverter.setRightTime(endDate.getText().toString()),
                                                "false",
                                                "false"
                                        );
                                        dutiesAPI.setDuty(duty, new Callback<String>() {
                                            @Override
                                            public void success(String s, Response response) {
                                                Intent intent = new Intent(CreateDutyActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                Log.d("DUTY_HAS_DONE!!!!!", "DONE");
                                            }

                                            @Override
                                            public void failure(RetrofitError error) {
                                                Log.d("RESTOFIT_ERROR!!!!!", error.getMessage());
                                            }
                                        });
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        Log.d("RESTOFIT_ERROR!!!!!", error.getMessage());
                                    }


                                }
                        );
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("RESTOFIT_ERROR!!!!!", error.getMessage());
                    }


                }
        );
    }
}
