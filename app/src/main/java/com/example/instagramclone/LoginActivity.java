package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnLogin, btnSignUp;
    private EditText editEmail, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Log In");

        btnLogin = findViewById(R.id.btnLoginActivity);
        btnSignUp = findViewById(R.id.btnSignUpLoginActivity);
        editEmail = findViewById(R.id.editEmailLogin);
        editPassword = findViewById(R.id.editPasswordLogin);

        editEmail.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(btnLogin);
                }
                return false;
            }
        });

        btnSignUp.setOnClickListener(LoginActivity.this);
        btnLogin.setOnClickListener(LoginActivity.this);

        if (ParseUser.getCurrentUser() != null){
            ParseUser.logOut();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLoginActivity :
                if (editPassword.getText().toString().equals("") || editEmail.getText().toString().equals("")){
                    FancyToast.makeText(this, "Email and Password is required",
                            FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                } else {
                    ParseUser.logInInBackground(editEmail.getText().toString(), editPassword.getText().toString(),
                            new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {
                                    if (user != null && e == null){
                                        FancyToast.makeText(LoginActivity.this, user.get("username") + " Login is Succesfully",
                                                FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                                        transitionToSicialMediaActivity();
                                    }
                                }
                            });
                }
                break;

            case R.id.btnSignUpLoginActivity :
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent);
                break;
        }
    }

    public void loginLayoutTapped(View view){

        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void transitionToSicialMediaActivity(){
        Intent intent = new Intent(LoginActivity.this, SocialMediaActivity.class);
        startActivity(intent);
        // mendestroy singup activity agar user tidak bisa kembali ke signup activity kecuali ketika logout
        finish();
    }
}
