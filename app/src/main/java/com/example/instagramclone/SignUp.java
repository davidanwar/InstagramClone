package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    private EditText editEmail, editUserName, editPassword;
    private Button btnLogin, btnSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // set title bar
        setTitle("Sign Up");

        editEmail = findViewById(R.id.editEmailSignUP);
        editUserName = findViewById(R.id.editUserNameSignUp);
        editPassword = findViewById(R.id.editPasswordSignUp);

        // signup manggunakan tombol di keyboard setelah mengisi data.
        editPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){

                    // memanggil onClick btnSignUp
                    onClick(btnSignUp);
                }
                return false;
            }
        });
        btnLogin = findViewById(R.id.btnLoginSignUpActivity);
        btnSignUp = findViewById(R.id.btnSignUpActivity);

        btnSignUp.setOnClickListener(SignUp.this);
        btnLogin.setOnClickListener(SignUp.this);

        if (ParseUser.getCurrentUser() != null){
            //ParseUser.logOut();
            transitionToSicialMediaActivity();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSignUpActivity :

                // jika email, user name, atau password kosong
                if (editEmail.getText().toString().equals("")
                        || editUserName.getText().toString().equals("")
                        || editPassword.getText().toString().equals("")){
                    FancyToast.makeText(this, "Email, User Name, and Password is required",
                            FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                    transitionToSicialMediaActivity();

                } else {
                    final ParseUser appUser = new ParseUser();
                    appUser.setUsername(editUserName.getText().toString());
                    appUser.setEmail(editEmail.getText().toString());
                    appUser.setPassword(editPassword.getText().toString());
                    final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                    progressDialog.setMessage("Signing up " + editUserName.getText().toString());
                    progressDialog.show();
                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null){
                                FancyToast.makeText(SignUp.this, appUser.get("username") + " SigUp is Succesfully",
                                        FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                            } else {
                                FancyToast.makeText(SignUp.this, e.getMessage(),
                                        FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                            }

                            progressDialog.dismiss();
                        }
                    });
                }
                break;

            case R.id.btnLoginSignUpActivity :
                Intent intent = new Intent(SignUp.this, LoginActivity.class);
                startActivity(intent);
                break;
        }

    }

    // ketika layout disentuh maka keyboard akan hilang
    // masukkan onClick methode di layout
    public void rootLayoutTapped(View view){

        // perlu diberikan try karena jika keyboard tidak ada dan layout disentuh maka aplikasi akan crash
        try {
            InputMethodManager inputMethodManager = (InputMethodManager)
                    getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void transitionToSicialMediaActivity(){
        Intent intent = new Intent(SignUp.this, SocialMediaActivity.class);
        startActivity(intent);

        // mendestroy singup activity agar user tidak bisa kembali ke signup activity kecuali ketika logout
        finish();
    }
}
