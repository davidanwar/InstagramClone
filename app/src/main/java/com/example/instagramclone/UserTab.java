package com.example.instagramclone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserTab extends Fragment {

    private ListView listView;
    private ArrayList arrayList;
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
}
