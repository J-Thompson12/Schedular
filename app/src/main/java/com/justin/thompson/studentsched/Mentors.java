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

import static com.justin.thompson.studentsched.MentorAdapter.mentorList;

public class Mentors extends AppCompatActivity {

    DBOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentors);
        ListView mentorLV = (ListView)findViewById(R.id.mentorsLV);
        db = new DBOpenHelper(getApplicationContext());
        db.getWritableDatabase();
        final List<Mentor> mentorsList = db.getAllMentors();
        ArrayAdapter<Mentor> adapter = new MentorAdapter(this, 0, mentorsList);
        mentorLV.setAdapter(adapter);
        AdapterView.OnItemClickListener adapterViewListener = new AdapterView.OnItemClickListener() {

            //on click
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Mentor mentor = mentorList.get(position);
                Intent intent = new Intent(Mentors.this, MentorDetail.class);
                startActivity(intent);
                mentor.setSelected(true);
            }
        };
        mentorLV.setOnItemClickListener(adapterViewListener);
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
            startActivity(new Intent(this, NewMentor.class));
            return true;
        }
        return false;
    }
}
