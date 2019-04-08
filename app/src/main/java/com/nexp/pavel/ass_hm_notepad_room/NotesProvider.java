package com.nexp.pavel.ass_hm_notepad_room;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import java.util.regex.Matcher;

public class NotesProvider extends ContentProvider {

    private static final String AUTHORITY = "com.nexp.pavel.ass_hm_notepad_room";
    private static final String NOTES_TABLE = "notes";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + NOTES_TABLE);

    public static final int NOTES = 1;
    public static final int NOTE_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, NOTES_TABLE, NOTES);
        uriMatcher.addURI(AUTHORITY, NOTES_TABLE + "/#", NOTE_ID);
    }



    @Override
    public boolean onCreate() {

        return true;
    }

    @Override
    public Cursor query( Uri uri,  String[] projection,  String selection,  String[] selectionArgs,  String sortOrder) {
        final int code = uriMatcher.match(uri);
        if (code == NOTES || code == NOTE_ID){
            final Context context = getContext();

            if(context == null){
                return null;
            }

            NoteDAO notesDAO = NotesDatabase.getInstance(context).getNotesDAO();
            final Cursor cursor;
            if (code == NOTES){
                cursor = notesDAO.contenProviderGetAll();
            } else {
                cursor = notesDAO.contenProviderGetNoteById(ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else throw new IllegalArgumentException("Unknown URI: " + uri);
    }

    @Override
    public String getType( Uri uri) {
        switch (uriMatcher.match(uri)){
            case NOTES:
                 return "vnd.android.cursor.dir/" + AUTHORITY + "." + NOTES_TABLE;
            case NOTE_ID:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + NOTES_TABLE;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Uri insert( Uri uri,  ContentValues values) {
        switch (uriMatcher.match(uri)) {
            case NOTES:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                final long id = NotesDatabase.getInstance(context).getNotesDAO()
                        .insert(Note.fromContentValues(values));

                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case NOTE_ID:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(Uri uri,  String selection,  String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case NOTES:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case NOTE_ID:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final int count = NotesDatabase.getInstance(context).getNotesDAO()
                        .deleteById(ContentUris.parseId(uri));
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update( Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case NOTES:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case NOTE_ID:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final Note note = Note.fromContentValues(values);
                note.id = ContentUris.parseId(uri);
                final int count = NotesDatabase.getInstance(context).getNotesDAO()
                        .update(note);
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
