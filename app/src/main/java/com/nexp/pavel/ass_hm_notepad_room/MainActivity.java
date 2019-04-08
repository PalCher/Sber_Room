package com.nexp.pavel.ass_hm_notepad_room;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Note> notes;
    private NotesDatabase db;
    private ContentObserver contentObserver;
    private static final String AUTHORITY = "com.nexp.pavel.ass_hm_notepad_room";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();


        if (getIntent().hasExtra("changeFlag")){
            Intent intent = getIntent();
            Boolean flag = intent.getBooleanExtra("changeFlag", false);
            if (flag){
                notes = db.getNotesDAO().getAll();
                mAdapter.refreshData(notes);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getContentResolver().registerContentObserver(NotesProvider.CONTENT_URI, true, contentObserver);
        Log.d("sd","sd");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(contentObserver);

    }

    public void init(){
        db = NotesDatabase.getInstance(getApplicationContext());
        notes = db.getNotesDAO().getAll();

        mRecyclerView = findViewById(R.id.note_recycler);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(notes);
        mRecyclerView.setAdapter(mAdapter);

        contentObserver = new MyObserver(new Handler(Looper.getMainLooper()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                startActivity(NewNote.newIntent(this));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static final Intent newIntent(Context context, boolean flag){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("changeFlag", flag);
        return intent;

    }

    private class MyObserver extends ContentObserver{

        public MyObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            changeUi();
        }
    }

    private void changeUi() {
        notes = db.getNotesDAO().getAll();
        mAdapter.refreshData(notes);
    }


}
