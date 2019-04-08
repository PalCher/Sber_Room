package com.nexp.pavel.ass_hm_notepad_room;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewNote extends AppCompatActivity {

    private EditText noteTitle;
    private EditText noteText;
    private NotesDatabase db;
    private static final String TAG = "My";

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note);
        initGUI();
    }

    private void initGUI() {
        noteText = findViewById(R.id.etText);
        noteTitle = findViewById(R.id.etTitle);
        init();
    }

    private void init(){
         db = NotesDatabase.getInstance(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_menu, menu);
        return true;
    }

    public void save(){
        String text = noteText.getText().toString();
        String title = noteTitle.getText().toString();
        Log.d(TAG, title);
        String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date());

        if (!title.equals("")){
            Note note = new Note( title, text, currentDateandTime);
            db.getNotesDAO().insert(note);
            startActivity(MainActivity.newIntent(NewNote.this, true));
        }else {
            Toast toast = Toast.makeText(NewNote.this, "Введите заголовок заметки", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0,0);
            toast.show();
        }

    }

//    public void test(){
////        Note note = db.getNotesDAO().getNoteById(0);
////        Log.d(TAG, note.title);
//
//        Note note = db.getNotesDAO().getNoteByTitle("title2");
//        Log.d(TAG, Long.toString(note.id));
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                save();
                return true;
            case R.id.action_back:
                startActivity(MainActivity.newIntent(NewNote.this, false));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static Intent newIntent (Context context){
        Intent intent = new Intent(context, NewNote.class);
        return intent;
    }
}
