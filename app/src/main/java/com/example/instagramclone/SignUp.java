package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    private Button btnSave, btnGetAllData;
    private EditText editName, editPunchSpeed, editPunchPower, editKickSpeed, editKickPower;
    private TextView textGetData;
    private String allKickBoxer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = findViewById(R.id.btnSave);
        btnGetAllData = findViewById(R.id.btnGetAllData);
        editName = findViewById(R.id.editName);
        editPunchSpeed = findViewById(R.id.editPunchSpeed);
        editPunchPower = findViewById(R.id.editPunchPower);
        editKickSpeed = findViewById(R.id.editKickSpeed);
        editKickPower = findViewById(R.id.editPunchPower);
        textGetData = findViewById(R.id.textGetData);


        btnSave.setOnClickListener(SignUp.this);

        textGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");
                parseQuery.getInBackground("ueHDqT2Ywf", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (object != null && e == null){
                            textGetData.setText(object.get("nama") + " - Punch Power " + object.get("punchPower"));
                        }
                    }
                });
            }
        });

        btnGetAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allKickBoxer = "";
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");

                // untuk mendapat semua data
                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if (e == null){
                            if (objects.size() > 0) {

                                // looping untuk mendapatkan semua data objek
                                for (ParseObject kickBoxer : objects) {
                                    allKickBoxer = allKickBoxer + kickBoxer.get("nama") + "\n";
                                    // kickBoxer.get("nama") --> agar yang ditampilkan text bukan adress memory

                                    FancyToast.makeText(SignUp.this, allKickBoxer,
                                            FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                                }
                            } else {
                                FancyToast.makeText(SignUp.this, e.getMessage(),
                                        FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                            }
                        }

                    }
                });
            }
        });
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
        // save proses di background agar tidak memberatkan UI
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
