package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Login2 extends AppCompatActivity {

    private Button btnSignUp, btnLogin;
    private EditText editUserNameSignUp, editPasswordSignUp, editUserNameLogin, editPasswordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        btnSignUp = findViewById(R.id.btnSignUp2);
        btnLogin = findViewById(R.id.btnLogin2);
        editUserNameSignUp = findViewById(R.id.editUserNameSignUp2);
        editUserNameLogin = findViewById(R.id.editUserNameLogin2);
        editPasswordSignUp = findViewById(R.id.editPasswordSignUP2);
        editPasswordLogin = findViewById(R.id.editPasswordLogin2);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseUser appUser = new ParseUser();
                appUser.setUsername(editUserNameSignUp.getText().toString());
                appUser.setPassword(editPasswordSignUp.getText().toString());
                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            FancyToast.makeText(Login2.this, appUser.get("username") + " is signup Succesfully",
                                    FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                        } else {
                            FancyToast.makeText(Login2.this, e.getMessage(),
                                    FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                        }
                    }
                });

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(editUserNameLogin.getText().toString(), editPasswordLogin.getText().toString(),
                        new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null ){
                            FancyToast.makeText(Login2.this, user.get("username") + " is login Succesfully",
                                    FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                        } else {
                            FancyToast.makeText(Login2.this, e.getMessage(),
                                    FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                        }
                    }
                });

            }
        });
    }
}
