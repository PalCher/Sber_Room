package com.nexp.pavel.ass_hm_notepad_room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

@Dao
public interface NoteDAO {

    @Query("select * from notes where id = :id")
    Note getNoteById(long id);

    @Query("select * from notes where id = :id")
    Cursor contenProviderGetNoteById(long id);

    @Query("select * from notes where title = :title")
    Note getNoteByTitle(String title);

    @Query("select * from notes")
    List<Note> getAll();

    @Query("select * from notes")
    Cursor contenProviderGetAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Note note);

    @Update
    int update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM notes WHERE id = :id")
    int deleteById(long id);
}
