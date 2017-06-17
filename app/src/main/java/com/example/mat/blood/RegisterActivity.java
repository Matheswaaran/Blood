package com.example.mat.blood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class RegisterActivity extends AppCompatActivity {
    String[] blood_groups, indian_States, indian_City;
    MaterialBetterSpinner bloodgrp_spinner, states_spinner, city_spinner;
    Button regBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bloodgrp_spinner = (MaterialBetterSpinner) findViewById(R.id.reg_spinner);
        states_spinner = (MaterialBetterSpinner)findViewById(R.id.reg_states);
        city_spinner = (MaterialBetterSpinner)findViewById(R.id.reg_city);
        regBtn = (Button)findViewById(R.id.reg_btn);

        blood_groups = getResources().getStringArray(R.array.blood_groups);
        indian_States = getResources().getStringArray(R.array.states);
        indian_City = getResources().getStringArray(R.array.cities);

        ArrayAdapter<String> blood_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, blood_groups);
        bloodgrp_spinner.setAdapter(blood_adapter);
        ArrayAdapter<String> states_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, indian_States);
        states_spinner.setAdapter(states_adapter);
        ArrayAdapter<String> city_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, indian_City);
        city_spinner.setAdapter(city_adapter);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Registration Successful.",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });
    }
}
