package com.justin.thompson.studentsched;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.R.attr.visible;
import static android.R.id.list;
import static com.justin.thompson.studentsched.R.drawable.calendar;

public class NewCourse extends AppCompatActivity {

    EditText code;
    EditText title;
    EditText start;
    EditText end;
    Spinner status;
    CheckBox sdAlert;
    CheckBox edAlert;
    ListView mentorsLV;
    TextView selectM;
    Date sDate;
    Date eDate;
    DBOpenHelper db;
    boolean edit;
    int courseId;
    ImageButton startBtn;
    ImageButton endBtn;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course);
        code = (EditText)findViewById(R.id.newCourseCode);
        title = (EditText)findViewById(R.id.newCourseTitle);
        start = (EditText)findViewById(R.id.newCourseStart);
        end = (EditText)findViewById(R.id.newCourseEnd);
        status = (Spinner) findViewById(R.id.newCourseDD);
        sdAlert = (CheckBox)findViewById(R.id.newCourseSCB);
        edAlert = (CheckBox)findViewById(R.id.newCourseECB);
        mentorsLV = (ListView)findViewById(R.id.editCourseMentorsLV);
        selectM = (TextView)findViewById(R.id.editCourseSelectM);
        startBtn = (ImageButton)findViewById(R.id.newCourseStartCal);
        endBtn = (ImageButton)findViewById(R.id.newCourseEndCal);
        db = new DBOpenHelper(getApplicationContext());
        db.getWritableDatabase();
        List<Course> list = CourseDetailAdapter.courseList;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getSelected()){
                Course c = list.get(i);
                title.setText(c.getTitle());
                start.setText(c.getStart());
                status.setSelection(getIndex(c.getStatus()));
                end.setText(c.getEnd());
                if(c.getsAlert().equals("true")){
                    sdAlert.setChecked(true);
                }else{
                    sdAlert.setChecked(false);
                }
                if(c.geteAlert().equals("true")){
                    edAlert.setChecked(true);
                }else{
                    edAlert.setChecked(false);
                }

                code.setText(c.getCode());
                courseId = c.getId();
                selectM.setVisibility(View.VISIBLE);
                mentorsLV.setVisibility(View.VISIBLE);
                edit = true;
                setTitle("Edit Course");

            }
        }
        final List<Mentor> mentorList = db.getMentorForEdit(courseId);
        for(int i = 0; i < mentorList.size(); i++){
            int termId = mentorList.get(i).getCourseId();
            if(termId != 0){
                mentorList.get(i).setSelected(true);
            }
        }
        MentorCBAdapter adapter = new MentorCBAdapter(this, mentorList);
        mentorsLV.setAdapter(adapter);
        mentorsLV.setItemsCanFocus(false);
        mentorsLV.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(NewCourse.this, startDatePicker, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(NewCourse.this, endDatePicker, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        sdAlert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(start.getText().toString().equals("")){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(NewCourse.this);
                    dialog.setCancelable(false);
                    dialog.setTitle("Start Date");
                    dialog.setMessage("You Need a Start Date");
                    dialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

                    final AlertDialog alert = dialog.create();
                    alert.show();
                    sdAlert.setChecked(false);
                }

            }
        });
        edAlert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(end.getText().toString().equals("")){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(NewCourse.this);
                    dialog.setCancelable(false);
                    dialog.setTitle("End Date");
                    dialog.setMessage("You Need an End Date");
                    dialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

                    final AlertDialog alert = dialog.create();
                    alert.show();
                    edAlert.setChecked(false);
                }

            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.noteSaveBtn) {
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            String sAlert;
            String eAlert;
            if (sdAlert.isChecked() == true) {
                sAlert = "true";
            } else {
                sAlert = "false";
            }
            if (edAlert.isChecked() == true) {
                eAlert = "true";
            } else {
                eAlert = "false";
            }

            if (edit == true) {
                db.updateCourse(code.getText().toString(), title.getText().toString(), start.getText().toString(), end.getText().toString(),
                        status.getSelectedItem().toString(), courseId, sAlert, eAlert);
                List<Mentor> mentorList = MentorCBAdapter.mentorList;
                for (int i = 0; i < mentorList.size(); i++) {
                    if (mentorList.get(i).getSelected()) {
                        db.setCourseID(mentorList.get(i).getId(), courseId);
                    } else {
                        db.setCourseID(mentorList.get(i).getId(), 0);
                    }
                }

            } else {
                Course course = new Course(code.getText().toString(), title.getText().toString(), start.getText().toString(), end.getText().toString(), status.getSelectedItem().toString());
                course.setsAlert(sAlert);
                course.seteAlert(eAlert);
                courseId = (int) db.createCourse(course);
            }

            if (sdAlert.isChecked() == true) {
                if (!start.getText().toString().isEmpty()) {
                    try {
                        sDate = sdf.parse(start.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    myCalendarStart.setTime(sDate);
                    myCalendarStart.set(Calendar.HOUR_OF_DAY, 9);
                    myCalendarStart.set(Calendar.MINUTE, 00);
                    if (myCalendarStart.getTimeInMillis() > System.currentTimeMillis()) {
                        Intent intent = new Intent(getApplicationContext(), CourseNotificationReciever.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), courseId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, myCalendarStart.getTimeInMillis(), pendingIntent);
                    }
                }
            }
            if (sdAlert.isChecked() == false) {
                Intent intent = new Intent(getApplicationContext(), CourseNotificationReciever.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), courseId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
            }

        if (edAlert.isChecked()) {
            if (!end.getText().toString().isEmpty()) {

                try {
                    eDate = sdf.parse(end.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                myCalendarEnd.setTime(eDate);
                myCalendarEnd.set(Calendar.HOUR_OF_DAY, 9);
                myCalendarEnd.set(Calendar.MINUTE, 00);
                if (myCalendarEnd.getTimeInMillis() > System.currentTimeMillis()) {
                    Intent intent = new Intent(getApplicationContext(), CourseNotificationReciever.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), courseId + 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, myCalendarEnd.getTimeInMillis(), pendingIntent);
                }
            }
        }
        if (edAlert.isChecked() == false) {
            Intent intent = new Intent(getApplicationContext(), CourseNotificationReciever.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), courseId + 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);

        }

        startActivity(new Intent(this, Courses.class));
        return true;
    }
        return false;
    }

    private int getIndex(String myString)
    {
        int index = 0;

        for (int i=0;i<status.getCount();i++){
            if (status.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }
    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        start.setText(sdf.format(myCalendarStart.getTime()));
    }
    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        end.setText(sdf.format(myCalendarEnd.getTime()));
    }
}
