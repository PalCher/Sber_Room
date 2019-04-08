package com.nexp.pavel.ass_hm_notepad_room;


import android.app.Application;
import android.arch.persistence.room.Room;
import android.support.v7.widget.RecyclerView;

public class App extends Application {

    public static App instance;

    private NotesDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;



    }


}
