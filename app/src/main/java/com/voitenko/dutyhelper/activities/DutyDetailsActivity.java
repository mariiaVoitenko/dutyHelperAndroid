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
import android.widget.Toast;

import com.voitenko.dutyhelper.API.AppointmentAPI;
import com.voitenko.dutyhelper.API.CategoriesAPI;
import com.voitenko.dutyhelper.API.DutiesAPI;
import com.voitenko.dutyhelper.API.PrioritiesAPI;
import com.voitenko.dutyhelper.BL.DataConverter;
import com.voitenko.dutyhelper.BL.ServiceGenerator;
import com.voitenko.dutyhelper.ConstantsContainer;
import com.voitenko.dutyhelper.R;
import com.voitenko.dutyhelper.models.Category;
import com.voitenko.dutyhelper.models.Duty;
import com.voitenko.dutyhelper.models.Priority;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class DutyDetailsActivity extends ActionBarActivity {
    int dutyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duty_details);
        final Button saveButton = (Button) findViewById(R.id.save_duty_button);

        final int dutyId = Integer.parseInt(getIntent().getStringExtra(ConstantsContainer.DUTY_ID));
        ServiceGenerator serviceGenerator = new ServiceGenerator();
        final DutiesAPI dutiesAPI = serviceGenerator.createService(DutiesAPI.class, ConstantsContainer.ENDPOINT);
        dutiesAPI.getDuty(dutyId, new Callback<Duty>() {
            @Override
            public void success(Duty duty, Response response) {
                TextView name = (TextView) findViewById(R.id.name_detail);
                TextView startDate = (TextView) findViewById(R.id.date_start_detail);
                TextView endDate = (TextView) findViewById(R.id.date_end_detail);
                TextView description = (TextView) findViewById(R.id.description_detail);
                TextView priority = (TextView) findViewById(R.id.priority_detail);
                TextView category = (TextView) findViewById(R.id.category_detail);
                TextView isDone = (TextView) findViewById(R.id.done_detail);
                name.setText(duty.getName());
                startDate.setText(DataConverter.getTime(duty.getStartDate()));
                endDate.setText(DataConverter.getTime(duty.getEndDate()));
                if (duty.getDescription() != null) {
                    description.setText(duty.getDescription().toString());
                } else {
                    description.setText("no description for this duty");
                }
                priority.setText(duty.getPriority().getName());
                category.setText(duty.getCategory().getName());
                if (duty.getIsDone() != null)
                    isDone.setText(duty.getIsDone().toString());
                else isDone.setText("false");

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText priority = (EditText) findViewById(R.id.priority_detail);
                        EditText category = (EditText) findViewById(R.id.category_detail);

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

                                                        EditText dutyName = (EditText) findViewById(R.id.name_detail);
                                                        EditText startDate = (EditText) findViewById(R.id.date_start_detail);
                                                        EditText endDate = (EditText) findViewById(R.id.date_end_detail);
                                                        EditText description = (EditText) findViewById(R.id.description_detail);

                                                        EditText isDone = (EditText) findViewById(R.id.done_detail);

                                                        Duty editedDuty = new Duty(
                                                                dutyId,
                                                                dutyName.getText().toString(),
                                                                category,
                                                                priority,
                                                                description.getText().toString(),
                                                                DataConverter.setRightTime(startDate.getText().toString()),
                                                                DataConverter.setRightTime(endDate.getText().toString()),
                                                                "false",
                                                                isDone.getText().toString()
                                                        );
                                                        Toast toast = Toast.makeText(getApplicationContext(),
                                                                editedDuty.toString(), Toast.LENGTH_LONG);
                                                        toast.show();
                                                        dutiesAPI.editDuty(editedDuty, new Callback<String>() {
                                                            @Override
                                                            public void success(String s, Response response) {
                                                                Log.d("DUTY_HAS_DONE!!!!!", "DONE");
                                                            }

                                                            @Override
                                                            public void failure(RetrofitError error) {
                                                                Log.d("RESTOFIT_ERROR!!!!!", error.getMessage());
                                                            }
                                                        });
                                                        Intent create = new Intent(DutyDetailsActivity.this, DutiesListActivity.class);
                                                        startActivity(create);

                                                    }

                                                    @Override
                                                    public void failure(RetrofitError error) {

                                                    }
                                                });

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
