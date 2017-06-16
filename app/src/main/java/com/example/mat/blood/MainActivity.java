package com.example.mat.blood;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText inputEmail, inputPassword;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Please Login to Continue...");

        inputLayoutEmail = (TextInputLayout)findViewById(R.id.login_layout_email);
        inputLayoutPassword = (TextInputLayout)findViewById(R.id.login_layout_pass);
        inputEmail = (EditText)findViewById(R.id.login_email);
        inputPassword = (EditText)findViewById(R.id.login_pass);
        loginBtn = (Button)findViewById(R.id.login_btn);

        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm(v);
            }
        });
    }

    private void submitForm(View v){
        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        Toast.makeText(getBaseContext(), "Login Successful", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }

    private boolean validateEmail(){
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)){
            inputLayoutEmail.setError("Invalid Email");
            requestFocus(inputEmail);
            return false;
        }else {
            inputLayoutEmail.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword(){
        if (inputPassword.getText().toString().trim().isEmpty()){
            inputLayoutPassword.setError("Invalid Password. Please fill the password");
            requestFocus(inputPassword);
            return false;
        }else {
            inputLayoutPassword.setErrorEnabled(false);
        }
        return true;
    }

    private static boolean isValidEmail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {
        private View view;

        public MyTextWatcher(View view){
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.login_email:
                    validateEmail();
                    break;
                case R.id.login_pass:
                    validatePassword();
                    break;
            }
        }
    }
}