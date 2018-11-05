package com.justin.thompson.studentsched;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.R.id.edit;
import static android.media.CamcorderProfile.get;
import static com.justin.thompson.studentsched.R.id.startDatePick;

public class NewTerm extends AppCompatActivity {

    ImageButton startBtn = null;
    ImageButton endBtn = null;
    DatePicker  startDate = null;
    TextView startText = null;
    TextView endText = null;
    TextView title = null;
    boolean edit;
    int termId;
    Calendar myCalendarStart = Calendar.getInstance();
    Calendar myCalendarEnd = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDatePicker = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendarStart.set(Calendar.YEAR, year);
            myCalendarStart.set(Calendar.MONTH, monthOfYear);
            myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelStart();
        }

    };
    DatePickerDialog.OnDateSetListener endDatePicker = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendarEnd.set(Calendar.YEAR, year);
            myCalendarEnd.set(Calendar.MONTH, monthOfYear);
            myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelEnd();
        }

    };
    DBOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_term);
        startBtn = (ImageButton)findViewById(R.id.startCalBtn);
        startDate = (DatePicker)findViewById(startDatePick);
        startText = (TextView)findViewById(R.id.startTxt);
        endText = (TextView)findViewById(R.id.endTxt);
        endBtn = (ImageButton)findViewById((R.id.endCalBtn));
        title = (TextView)findViewById(R.id.termTitleTxt);
        ListView courseLV = (ListView)findViewById(R.id.newTermLV);
        TextView courseTxt = (TextView)findViewById(R.id.newTermCourseTxt);
        courseLV.setVisibility(View.INVISIBLE);
        courseTxt.setVisibility(View.INVISIBLE);
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(NewTerm.this, startDatePicker, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(NewTerm.this, endDatePicker, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        db = new DBOpenHelper(getApplicationContext());
        db.getWritableDatabase();
        List<Term> list = TermAdapter.termList;
        for(int i = 0; i < list.size(); i++) {
            if (list.get(i).getIsSelected()) {
                title.setText(list.get(i).getTitle());
                startText.setText(list.get(i).getStart());
                endText.setText(list.get(i).getEnd());
                termId = list.get(i).getId();
                edit = true;
                courseLV.setVisibility(View.VISIBLE);
                courseTxt.setVisibility(View.VISIBLE);
                setTitle("Edit Term");
            }
        }
        final List<Course> courseList = db.getCoursesListForEdit(termId);
        for(int i = 0; i < courseList.size(); i++){
            int termId = courseList.get(i).getTermId();
            if(termId != 0){
                courseList.get(i).setSelected(true);
            }
        }
        CourseAdapter adapter = new CourseAdapter(this, courseList);
        courseLV.setAdapter(adapter);
        courseLV.setItemsCanFocus(false);
        courseLV.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        db.closeDB();

    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        startText.setText(sdf.format(myCalendarStart.getTime()));
    }
    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        endText.setText(sdf.format(myCalendarEnd.getTime()));
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.noteSaveBtn) {
            if(edit == false) {
                String t = title.getText().toString();
                String st = startText.getText().toString();
                String ed = endText.getText().toString();
                Term term = new Term(t, st, ed);
                db.createTerm(term);
                db.closeDB();
                Intent intent = new Intent(this, Terms.class);
                startActivity(intent);
                return true;
            }else if(edit == true){
                List<Course> courseList = CourseAdapter.courseList;
                db.updateTerm(title.getText().toString(), startText.getText().toString(), endText.getText().toString(), termId);
                for (int i = 0; i < courseList.size(); i++) {
                    if (courseList.get(i).getSelected()) {
                        db.setTermID(courseList.get(i).getId(), termId);
                    } else {
                        db.setTermID(courseList.get(i).getId(), 0);
                    }
                }
                Intent intent = new Intent(this, TermDetail.class);
                startActivity(intent);
                return true;
            }
        }
        return false;
    }
}
