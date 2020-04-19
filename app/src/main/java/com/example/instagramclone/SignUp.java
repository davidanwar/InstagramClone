package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    private Button btnSave;
    private EditText editName, editPunchSpeed, editPunchPower, editKickSpeed, editKickPower;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = findViewById(R.id.btnSave);
        editName = findViewById(R.id.editName);
        editPunchSpeed = findViewById(R.id.editPunchSpeed);
        editPunchPower = findViewById(R.id.editPunchPower);
        editKickSpeed = findViewById(R.id.editKickSpeed);
        editKickPower = findViewById(R.id.editPunchPower);


        btnSave.setOnClickListener(SignUp.this);
    }


    @Override
    public void onClick(View v) {

        try {


        final ParseObject kickBoxer = new ParseObject("KickBoxer");
        kickBoxer.put("nama", editName.getText().toString());
        kickBoxer.put("punchSpeed", Integer.parseInt(editPunchSpeed.getText().toString()));
        kickBoxer.put("punchPower", Integer.parseInt(editPunchPower.getText().toString()));
        kickBoxer.put("kickSpeed", Integer.parseInt(editKickSpeed.getText().toString()));
        kickBoxer.put("kickPower", Integer.parseInt(editKickPower.getText().toString()));
        kickBoxer.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    FancyToast.makeText(SignUp.this, kickBoxer.get("nama") + " is saved to server",
                            FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                } else {
                    FancyToast.makeText(SignUp.this, e.getMessage(),
                            FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                }
            }
        });

        } catch (Exception e){
            FancyToast.makeText(SignUp.this, e.getMessage(),
                    FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
        }
    }
}
