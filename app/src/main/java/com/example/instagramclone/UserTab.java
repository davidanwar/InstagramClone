package com.example.instagramclone;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserTab extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private ListView listView;
    private ArrayList<String> arrayList; // tambahan <String> agar methode putExtra ia tidak dianggap sebagai objek
    private ArrayAdapter arrayAdapter;

    public UserTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_user_tab, container, false);
        listView = view.findViewById(R.id.listView);
        arrayList = new ArrayList();

        // butuh array adapter sebagai MCV karena arrayList tidak bisa langsung berinteraksi dengan struktur database
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrayList);
        final TextView textLoadingUser = view.findViewById(R.id.textLoadingUser);

        listView.setOnItemClickListener(UserTab.this);
        listView.setOnItemLongClickListener(UserTab.this);

        // menampilkan data user yang diambil dari server
        ParseQuery<ParseUser> parseUserParseQuery = ParseUser.getQuery();

        // agar current user tidak ditampilkan datanya
        parseUserParseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        parseUserParseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null){
                    if (objects.size() > 0){
                        for (ParseUser user : objects){
                            arrayList.add(user.getUsername());
                        }
                        listView.setAdapter(arrayAdapter);

                        // text loading akan hilang setelah 2 second dan digantikan dengan listView
                        textLoadingUser.animate().alpha(0).setDuration(2000);
                        // list view muncul setelah textLoading hilang
                        listView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), UserPosts.class);

        // putExtra digunakan untuk mengirim data dari activity atau fragment ke activity atau fragment yang lain
        intent.putExtra("username", arrayList.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ParseQuery<ParseUser> parseUserParseQuery = ParseUser.getQuery();
        parseUserParseQuery.whereEqualTo("username", arrayList.get(position));
        parseUserParseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser object, ParseException e) {
                if (object != null && e == null){
                    //FancyToast.makeText(getContext(), object.get("profileProfession") + "", Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                    final PrettyDialog prettyDialog = new PrettyDialog(getContext());
                    prettyDialog.setTitle(object.getUsername()+ " 's Info")
                            .setMessage(object.get("profileBio") + "\n"
                                    + object.get("profileProfession") + "\n"
                                    + object.get("profileHobby") + "\n"
                                    + object.get("profileSport"))
                            .setIcon(R.drawable.person)
                            .addButton(
                                    "OK", // buttontext
                                    R.color.pdlg_color_white, // button text color
                                    R.color.pdlg_color_green, // button background color
                                    new PrettyDialogCallback() {
                                        @Override
                                        public void onClick() {
                                            prettyDialog.dismiss();
                                        }
                                    }).show(); // pretty dialog
                }
            }
        });
        return true;
    }
}
