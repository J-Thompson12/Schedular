package com.justin.thompson.studentsched;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.R.id.edit;

public class NewAss extends AppCompatActivity {

    Spinner type;
    EditText details;
    CheckBox goalCB;
    EditText goalDate;
    boolean edit = false;
    ImageButton calBtn;
    Date date;
    int id;
    int courseId;
    String sAlert;
    Calendar myCalendarStart = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDatePicker = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendarStart.set(Calendar.YEAR, year);
            myCalendarStart.set(Calendar.MONTH, monthOfYear);
            myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };
    DBOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ass);
        type = (Spinner)findViewById(R.id.assSpinner);
        details = (EditText)findViewById(R.id.newAssDetails);
        goalCB = (CheckBox)findViewById(R.id.assGoalCB);
        goalDate = (EditText)findViewById(R.id.newAssGoalDate);
        db = new DBOpenHelper(getApplicationContext());
        db.getWritableDatabase();
        calBtn = (ImageButton)findViewById(R.id.goalBtn);
        calBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(NewAss.this, startDatePicker, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        List<Course> list = CourseDetailAdapter.courseList;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getSelected()){
                Course c = list.get(i);
                courseId = c.getId();
            }
        }
        List<Assessment> assList = AssAdapter.assList;
        for(int i = 0; i < assList.size(); i++){
            if(assList.get(i).getSelected()){
                Assessment a = assList.get(i);
                type.setPrompt(a.getType());
                details.setText(a.getDetails());
                goalDate.setText(a.getGoalDate());
                id = a.getid();
                edit = true;
                if(a.getAlert().equals("true")){
                    goalCB.setChecked(true);
                }else{
                    goalCB.setChecked(false);
                }
                setTitle("Edit Assessment");

            }
        }
        goalCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(goalDate.getText().toString().equals("")){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(NewAss.this);
                    dialog.setCancelable(false);
                    dialog.setTitle("Goal Date");
                    dialog.setMessage("You Need a Goal Date");
                    dialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

                    final AlertDialog alert = dialog.create();
                    alert.show();
                    goalCB.setChecked(false);
                }

            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.noteSaveBtn) {
            if(goalCB.isChecked() == true){
                sAlert = "true";
            }else{
                sAlert = "false";
            }
            if(edit == false) {
                Assessment ass = new Assessment(goalDate.getText().toString(),courseId,type.getSelectedItem().toString(), details.getText().toString());
                ass.setAlert(sAlert);
                id = (int) db.createASS(ass);

            }else if(edit == true){
                db.updateASS(id, courseId, details.getText().toString(), goalDate.getText().toString(), type.getSelectedItem().toString(), sAlert);
            }
            if(goalCB.isChecked() == true) {
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                if (!goalDate.getText().equals(null)) {
                    boolean alarmUp = (PendingIntent.getBroadcast(getApplicationContext(), courseId,
                            new Intent("com.my.package.MY_UNIQUE_ACTION"),
                            PendingIntent.FLAG_NO_CREATE) != null);
                    if(alarmUp == false) {

                        try {
                            date = sdf.parse(goalDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        myCalendarStart.setTime(date);
                        myCalendarStart.set(Calendar.HOUR_OF_DAY, 9);
                        myCalendarStart.set(Calendar.MINUTE, 00);
                        if (myCalendarStart.getTimeInMillis() > System.currentTimeMillis()) {
                            Intent intent = new Intent(getApplicationContext(), AssNotificationReciever.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), id + 26, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, myCalendarStart.getTimeInMillis(), pendingIntent);
                        }
                    }
                }
            }
            if(goalCB.isChecked() == false){
                Intent intent = new Intent(getApplicationContext(), CourseNotificationReciever.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), id +26, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);

            }
            startActivity(new Intent(this, Assessments.class));
            return true;
        }
        return false;
    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        goalDate.setText(sdf.format(myCalendarStart.getTime()));
    }
}
