package com.justin.thompson.studentsched;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static android.R.id.list;
import static com.justin.thompson.studentsched.R.id.coursesLV;

public class Notes extends AppCompatActivity {
    DBOpenHelper db;
    int courseId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ListView notesLV = (ListView)findViewById(R.id.notesLV);
        db = new DBOpenHelper(getApplicationContext());
        db.getWritableDatabase();
        List<Course> list = CourseDetailAdapter.courseList;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getSelected()){
                Course c = list.get(i);
                courseId = c.getId();
            }
        }
        final List<Note> notesList = db.getNoteByCourseID(courseId);
        System.out.println(notesList.size());
        ArrayAdapter<Note> adapter = new NoteAdapter(this, 0, notesList);
        notesLV.setAdapter(adapter);
        AdapterView.OnItemClickListener adapterViewListener = new AdapterView.OnItemClickListener() {

            //on click
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = notesList.get(position);
                note.setSelected(true);
                Intent intent = new Intent(Notes.this, NoteDetails.class);
                startActivity(intent);
            }
        };
        notesLV.setOnItemClickListener(adapterViewListener);
        db.closeDB();
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.terms_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.addTerm) {
            startActivity(new Intent(this, NewNote.class));
            return true;
        }
        return false;
    }
}
