package com.nexp.pavel.ass_hm_notepad_room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateNote extends AppCompatActivity {

    private EditText noteTitle;
    private EditText noteText;
    private NotesDatabase db;
    private static final String TAG = "My";
    private int id;
    private Note note;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note);
        initGUI();

        if(getIntent().hasExtra("id")){
            Intent intent = getIntent();
            id = intent.getIntExtra("id", 0);
            note = db.getNotesDAO().getNoteById(id);
            noteText.setText(note.text);
            noteTitle.setText(note.title);
        }



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

    public void update(){
        String text = noteText.getText().toString();
        String title = noteTitle.getText().toString();
        String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date());

        if (!title.equals("")){
            note.text = text;
            note.title = title;
            note.lastDate = currentDateandTime;
            db.getNotesDAO().update(note);
            startActivity(MainActivity.newIntent(UpdateNote.this, true));
        }else {
            Toast toast = Toast.makeText(UpdateNote.this, "Введите заголовок заметки", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0,0);
            toast.show();
        }

    }

    public void delete(){
        db.getNotesDAO().delete(note);
    }

//

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                delete();
                startActivity(MainActivity.newIntent(UpdateNote.this, true));
            case R.id.action_save:
                update();
                return true;
            case R.id.action_back:
                startActivity(MainActivity.newIntent(UpdateNote.this, false));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static Intent newIntent (Context context, int id){
        Intent intent = new Intent(context, UpdateNote.class);
        intent.putExtra("id", id);
        return intent;
    }
}
