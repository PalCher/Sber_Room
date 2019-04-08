package com.nexp.pavel.ass_hm_notepad_room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Note.class}, version = 1)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NoteDAO getNotesDAO();

    private static NotesDatabase sInstance;

    public static synchronized NotesDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context,
                    NotesDatabase.class, "notes_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return sInstance;
    }
}
