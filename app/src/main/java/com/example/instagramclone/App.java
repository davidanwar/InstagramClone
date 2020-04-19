package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("D5SRBPDEHAesOgrnf7sXfzcBd9MofVVUC6fd661Q")
                // if defined
                .clientKey("A6Q1WTR4aJsxDJzHbaSuic1XXAn0QmP9d1GlgekO")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
