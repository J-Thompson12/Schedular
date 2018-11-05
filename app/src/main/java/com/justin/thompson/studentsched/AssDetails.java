package com.justin.thompson.studentsched;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import static android.R.attr.id;
import static android.R.attr.type;

public class AssDetails extends AppCompatActivity {
    TextView type;
    TextView details;
    TextView goalDate;
    int id;
    DBOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ass_details);
        type = (TextView) findViewById(R.id.assDetailsType);
        details = (TextView) findViewById(R.id.assDetails);
        goalDate = (TextView) findViewById(R.id.assDetailsGoal);
        db = new DBOpenHelper(getApplicationContext());
        db.getWritableDatabase();
        List<Assessment> assList = AssAdapter.assList;
        for(int i = 0; i < assList.size(); i++){
            if(assList.get(i).getSelected()){
                Assessment a = assList.get(i);
                System.out.println(a.getType());
                type.setText(a.getType());
                details.setText(a.getDetails());
                goalDate.setText(a.getGoalDate());
                id = a.getid();

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
            startActivity(new Intent(this, NewAss.class));
            return true;
        }
        if(item.getItemId() == R.id.termDelBtn){
            db.deleteAss(id);
            startActivity(new Intent(this, Assessments.class));
            return true;
        }
        return false;
    }
}
