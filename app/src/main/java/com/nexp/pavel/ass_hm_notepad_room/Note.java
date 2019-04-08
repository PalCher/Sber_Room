package com.nexp.pavel.ass_hm_notepad_room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;

@Entity(tableName = "notes")
public class Note {

    public static final String NOTE_ID = "id";

    public static final String NOTE_TITLE = "title";
    public static final String NOTE_DATE = "last_date";
    public static final String NOTE_TEXT = "text";

    @PrimaryKey(autoGenerate = true)
    long id;
    String title;

    String text;
    @ColumnInfo (name = "last_date")
    String lastDate;

    public Note( String title, String text, String lastDate) {
        this.title = title;
        this.text = text;
        this.lastDate = lastDate;
    }

    public static Note fromContentValues(ContentValues values) {

        final Note note = new Note(values.getAsString(NOTE_TITLE), values.getAsString(NOTE_TEXT), values.getAsString(NOTE_DATE));

        return note;
    }


}
