package com.justin.thompson.studentsched;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import static android.R.attr.id;
import static android.R.id.list;
import static com.justin.thompson.studentsched.MentorAdapter.mentorList;

public class MentorDetail extends AppCompatActivity {

    TextView name;
    TextView number;
    TextView email;
    DBOpenHelper db;
    int id;
    Mentor mentor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_detail);
        name = (TextView)findViewById(R.id.mentorDetailName);
        number = (TextView)findViewById(R.id.mentorDetailNumber);
        email = (TextView)findViewById(R.id.mentorDetailEmail);
        db = new DBOpenHelper(getApplicationContext());
        db.getWritableDatabase();


        List<Mentor> list = mentorList;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getSelected()){
                Mentor m = list.get(i);
                name.setText(m.getName());
                number.setText(m.getNumber());
                email.setText(m.getEmail());
                id = m.getId();
                mentor = m;
            }
        }
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.term_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.termEditBtn) {
            startActivity(new Intent(this, NewMentor.class));
            return true;
        }
        if(item.getItemId() == R.id.termDelBtn){
            db.deleteMentor(id);
            startActivity(new Intent(this, Mentors.class));
            return true;
        }
        return false;
    }
}
