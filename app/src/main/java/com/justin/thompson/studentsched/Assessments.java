package com.justin.thompson.studentsched;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import static com.justin.thompson.studentsched.NoteAdapter.notesList;
import static com.justin.thompson.studentsched.R.id.notesLV;

public class Assessments extends AppCompatActivity {

    DBOpenHelper db;
    int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);
        ListView assLV = (ListView)findViewById(R.id.assLV);
        db = new DBOpenHelper(getApplicationContext());
        db.getWritableDatabase();
        List<Course> list = CourseDetailAdapter.courseList;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getSelected()){
                Course c = list.get(i);
                courseId = c.getId();
            }
        }
        final List<Assessment> assList = db.getAssByCourseID(courseId);
        ArrayAdapter<Assessment> adapter = new AssAdapter(this, 0, assList);
        assLV.setAdapter(adapter);
        AdapterView.OnItemClickListener adapterViewListener = new AdapterView.OnItemClickListener() {

            //on click
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Assessment ass = assList.get(position);
                ass.setSelected(true);
                Intent intent = new Intent(Assessments.this, AssDetails.class);
                startActivity(intent);
            }
        };
        assLV.setOnItemClickListener(adapterViewListener);
        db.closeDB();
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.terms_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.addTerm) {
            startActivity(new Intent(this, NewAss.class));
            return true;
        }
        return false;
    }
}
