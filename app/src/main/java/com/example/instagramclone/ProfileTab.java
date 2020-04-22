package com.example.instagramclone;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {

    EditText editProfileName, editProfileBio, editProfileHobby, editProfileSport, editProfileProfession;
    private Button btnUpdateInfo;



    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile_tab, container, false);
        // harus menggunakan view agar bisa menginisialisasi id
        editProfileName = view.findViewById(R.id.editProfileName);
        editProfileBio = view.findViewById(R.id.editProfileBio);
        editProfileProfession = view.findViewById(R.id.editProfileProfession);
        editProfileHobby = view.findViewById(R.id.editProfileHoby);
        editProfileSport = view.findViewById(R.id.editProfileSport);
        btnUpdateInfo = view.findViewById(R.id.btnUpdateProfile);

        final ParseUser parseUser = ParseUser.getCurrentUser();

        // jika data server null maka isi dengan ""
        // jika tidak menggunakan methode ini maka akan menampilkan string "null" di edit text
        if (parseUser.get("profileName") == null){
            editProfileName.setText("");
        } else {
            editProfileName.setText(parseUser.get("profileName") + "");
        }

        // jika menggunakn toString() maka akan crash jika data yang ambil dari server kosong
        editProfileBio.setText(parseUser.get("profileBio") + "");
        editProfileProfession.setText(parseUser.get("profileProfession") + "");
        editProfileHobby.setText(parseUser.get("profileHobby") + "");
        editProfileSport.setText(parseUser.get("profileSport") + "");

        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseUser.put("profileName", editProfileName.getText().toString());
                parseUser.put("profileBio", editProfileBio.getText().toString());
                parseUser.put("profileProfession", editProfileProfession.getText().toString());
                parseUser.put("profileHobby", editProfileHobby.getText().toString());
                parseUser.put("profileSport", editProfileSport.getText().toString());
//                ProgressDialog progressDialog = new ProgressDialog(getContext());
//                progressDialog

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            FancyToast.makeText(getContext(), "Info Updated",
                                    FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                        } else {
                            FancyToast.makeText(getContext(), e.getMessage(),
                                    FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                        }
                    }
                });
            }
        });
        return view;
    }
}
