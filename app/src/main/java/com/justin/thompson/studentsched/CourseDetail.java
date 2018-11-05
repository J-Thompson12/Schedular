package com.justin.thompson.studentsched;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import static android.media.CamcorderProfile.get;
import static com.justin.thompson.studentsched.R.id.courseDetailLV;
import static com.justin.thompson.studentsched.R.id.termDetailLV;

public class CourseDetail extends AppCompatActivity {
    DBOpenHelper db;
    ListView mentorLv;
    TextView title;
    TextView start;
    TextView end;
    TextView code;
    TextView status;
    int courseId;
    List<Mentor> mentorList;
    List<Course> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        title = (TextView)findViewById(R.id.courseDetailTitle);
        start = (TextView)findViewById(R.id.courseDetailStart);
        end = (TextView)findViewById(R.id.courseDetailEnd);
        code = (TextView)findViewById(R.id.courseDetailCode);
        status = (TextView)findViewById(R.id.courseDetailStatus);
        mentorLv = (ListView)findViewById(R.id.courseDetailLV);

        db = new DBOpenHelper(getApplicationContext());
        db.getWritableDatabase();
        list = CourseDetailAdapter.courseList;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getSelected()){
                Course c = list.get(i);
                title.setText(c.getTitle());
                start.setText(c.getStart());
                status.setText(c.getStatus());
                end.setText(c.getEnd());
                code.setText(c.getCode());
                courseId = c.getId();
            }
        }
        mentorList = db.getMentorByCourseId(courseId);
        MentorAdapter adapter = new MentorAdapter(this,0,mentorList);
        mentorLv.setAdapter(adapter);
        mentorLv.setItemsCanFocus(false);
        mentorLv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        AdapterView.OnItemClickListener adapterViewListener = new AdapterView.OnItemClickListener() {

            //on click
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Mentor mentor = mentorList.get(position);
                Intent intent = new Intent(CourseDetail.this, MentorDetail.class);
                startActivity(intent);
                mentor.setSelected(true);
            }
        };
        mentorLv.setOnItemClickListener(adapterViewListener);

        db.closeDB();


    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.courses_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.termEditBtn) {
            startActivity(new Intent(this, NewCourse.class));
            return true;
        }
        else if(item.getItemId() == R.id.termDelBtn){
            db.deleteCourse(courseId);
            for(int i = 0; i < mentorList.size(); i++){
                if(mentorList.get(i).getCourseId() == courseId){
                    db.setCourseID(mentorList.get(i).getId(),0);
                }

            }
            startActivity(new Intent(this, Courses.class));
            return true;
        }else if(item.getItemId() == R.id.courseNotesBtn){

            startActivity(new Intent(this, Notes.class));
            return true;
        }else if(item.getItemId() == R.id.assBtn){
            startActivity(new Intent(this, Assessments.class));
            return true;
        }

        return false;
    }
}
