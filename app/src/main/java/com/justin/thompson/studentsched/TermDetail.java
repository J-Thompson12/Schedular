package com.justin.thompson.studentsched;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import static android.R.id.list;
import static android.media.CamcorderProfile.get;
import static com.justin.thompson.studentsched.R.id.termDetailLV;

public class TermDetail extends AppCompatActivity {
    DBOpenHelper db;
    ListView courseLv;
    TextView title;
    TextView start;
    TextView end;
    int termId;
    List<Course> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        title = (TextView)findViewById(R.id.termTitleTxt);
        start = (TextView)findViewById(R.id.termStartDate);
        end = (TextView)findViewById(R.id.termEndDate);
        db = new DBOpenHelper(getApplicationContext());
        db.getWritableDatabase();
        List<Term> list = TermAdapter.termList;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getIsSelected()){
                title.setText(list.get(i).getTitle());
                start.setText(list.get(i).getStart());
                end.setText(list.get(i).getEnd());
                termId = list.get(i).getId();
            }
        }
        courseList = db.getCoursesByTermId(termId);
        courseLv = (ListView) findViewById(termDetailLV);
        CourseDetailAdapter adapter = new CourseDetailAdapter(this,0,courseList);
        courseLv.setAdapter(adapter);
        courseLv.setItemsCanFocus(false);
        courseLv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        AdapterView.OnItemClickListener adapterViewListener = new AdapterView.OnItemClickListener() {

            //on click
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Course course = courseList.get(position);
                Intent intent = new Intent(TermDetail.this, CourseDetail.class);
                startActivity(intent);
                course.setSelected(true);
            }
        };
        courseLv.setOnItemClickListener(adapterViewListener);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.term_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.termEditBtn) {
            startActivity(new Intent(this, NewTerm.class));
            return true;
        }
        if(item.getItemId() == R.id.termDelBtn) {
            boolean hasCourse = false;
            for(int i = 0; i < courseList.size(); i++) {
                if (courseList.get(i).getTermId() == termId) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(TermDetail.this);
                    dialog.setCancelable(false);
                    dialog.setTitle("Delete Term");
                    dialog.setMessage("You cannot delete a term that has courses in it");
                    dialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

                    final AlertDialog alert = dialog.create();
                    alert.show();
                    hasCourse = true;
                    break;
                }
            }
            if(hasCourse == false) {
                db.deleteTerm(termId);
                startActivity(new Intent(this, Terms.class));
                return true;
            }

            }
        return false;
    }
}
