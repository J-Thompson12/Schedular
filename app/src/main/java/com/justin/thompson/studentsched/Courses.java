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

public class Courses extends AppCompatActivity {
    
    private DBOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        ListView coursesLV = (ListView)findViewById(R.id.coursesLV);
        db = new DBOpenHelper(getApplicationContext());
        db.getWritableDatabase();
        final List<Course> courseList = db.getAllCourses();
        ArrayAdapter<Course> adapter = new CourseDetailAdapter(this, 0, courseList);
        coursesLV.setAdapter(adapter);
        AdapterView.OnItemClickListener adapterViewListener = new AdapterView.OnItemClickListener() {

            //on click
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course course = courseList.get(position);
                course.setSelected(true);
                Intent intent = new Intent(Courses.this, CourseDetail.class);
                startActivity(intent);
            }
        };
        coursesLV.setOnItemClickListener(adapterViewListener);
        db.closeDB();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.terms_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.addTerm) {
            startActivity(new Intent(this, NewCourse.class));
            return true;
        }
        return false;
    }
}
