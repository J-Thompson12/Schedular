package com.justin.thompson.studentsched;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.R.attr.id;
import static com.justin.thompson.studentsched.AssAdapter.assList;
import static com.justin.thompson.studentsched.R.id.termBtn;

public class MainActivity extends AppCompatActivity {
    Button termsBtn = null;
    DBOpenHelper db;
    Calendar myCalendarStart = Calendar.getInstance();
    Calendar myCalendarEnd = Calendar.getInstance();
    Calendar myCalendarGoal = Calendar.getInstance();
    Date sDate;
    Date eDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        termsBtn = (Button)findViewById(R.id.termBtn);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBOpenHelper(getApplicationContext());
        db.getWritableDatabase();
        /*final List<Course> courseList = db.getAllCourses();
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if(!courseList.isEmpty()) {
            for (Course course : courseList) {
                if (!course.getStart().isEmpty()) {
                    if (course.getsAlert().equals("true")) {
                        try {
                            sDate = sdf.parse(course.getStart());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        myCalendarStart.setTime(sDate);
                        myCalendarStart.set(Calendar.HOUR_OF_DAY, 12);
                        myCalendarStart.set(Calendar.MINUTE, 00);
                        if (myCalendarStart.getTimeInMillis() > System.currentTimeMillis()) {
                            Intent intent = new Intent(getApplicationContext(), CourseNotificationReciever.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), course.getId() + 1000, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, myCalendarStart.getTimeInMillis(), pendingIntent);
                        }
                    }
                }
                if (!course.getEnd().isEmpty()) {
                    if (course.geteAlert().equals("true")) {
                        try {
                            eDate = sdf.parse(course.getEnd());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        myCalendarEnd.setTime(eDate);
                        myCalendarEnd.set(Calendar.HOUR_OF_DAY, 12);
                        myCalendarEnd.set(Calendar.MINUTE, 00);
                        if (myCalendarEnd.getTimeInMillis() > System.currentTimeMillis()) {
                            Intent intent = new Intent(getApplicationContext(), CourseNotificationReciever.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), course.getId() + 50, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, myCalendarEnd.getTimeInMillis(), pendingIntent);
                        }
                    }
                }
            }
        }
        List<Assessment> assList = db.getAllAss();
            for(Assessment ass: assList) {
                if (!assList.isEmpty()) {
                    if (!ass.getGoalDate().isEmpty()) {
                        if (ass.getAlert().equals("true")) {
                            try {
                                sDate = sdf.parse(ass.getGoalDate());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            myCalendarGoal.setTime(sDate);
                            myCalendarGoal.set(Calendar.HOUR_OF_DAY, 12);
                            myCalendarGoal.set(Calendar.MINUTE, 00);
                            if (myCalendarGoal.getTimeInMillis() > System.currentTimeMillis()) {
                                Intent intent = new Intent(getApplicationContext(), CourseNotificationReciever.class);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), ass.getid() + 15, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                                alarmManager.set(AlarmManager.RTC_WAKEUP, myCalendarGoal.getTimeInMillis(), pendingIntent);
                            }
                        }
                    }
                }
            }*/

        db.closeDB();
    }


    public void openTerms(View view) {
        Intent intent = new Intent(this, Terms.class);
        startActivity(intent);
    }

    public void openCourses(View view) {
        Intent intent = new Intent(this, Courses.class);
        startActivity(intent);
    }

    public void openMentors(View view) {
        Intent intent = new Intent(this, Mentors.class);
        startActivity(intent);
    }
}
