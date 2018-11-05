package com.justin.thompson.studentsched;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import static android.R.attr.x;
import static com.justin.thompson.studentsched.MentorAdapter.mentorList;

public class NewMentor extends AppCompatActivity {

    EditText name;
    EditText number;
    EditText email;
    DBOpenHelper db;
    int id;
    Boolean edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mentor);
        name = (EditText)findViewById(R.id.newMentorName);
        number = (EditText)findViewById(R.id.newMentorNumber);
        email = (EditText)findViewById(R.id.newMentorEmail);
        db = new DBOpenHelper(getApplicationContext());
        db.getWritableDatabase();
        edit = false;
        List<Mentor> list = mentorList;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getSelected()){
                Mentor m = list.get(i);
                name.setText(m.getName());
                number.setText(m.getNumber());
                email.setText(m.getEmail());
                id = m.getId();
                edit = true;
                setTitle("Edit Mentor");
            }
        }
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.noteSaveBtn) {
            if (edit == false){
                Mentor mentor = new Mentor(name.getText().toString(),number.getText().toString(),email.getText().toString());
                db.createMentor(mentor);
            }
            else{
                db.updateMentor(name.getText().toString(),number.getText().toString(),email.getText().toString(), id);
            }
            startActivity(new Intent(this, Mentors.class));
            return true;
        }
        return false;
    }
}
