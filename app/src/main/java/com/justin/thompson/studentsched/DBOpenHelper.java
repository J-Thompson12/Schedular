package com.justin.thompson.studentsched;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static android.R.attr.name;
import static android.R.attr.tag;
import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Created by Thompson on 5/24/17.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "schedule.db";

    // Create Tables
    private static final String TABLE_TERMS = "Terms";
    private static final String TABLE_MENTORS = "Mentors";
    private static final String TABLE_ASSESSMENT = "Assessments";
    private static final String TABLE_NOTES = "Notes";
    private static final String TABLE_COURSES = "Courses";

    //Columns in every table
    private static final String ID = "id";
    private static final String CREATEDAT = "createdAt";

    //Columns in Terms table
    public static final String TERM_TITLE = "title";
    public static final String TERM_START = "start";
    public static final String TERM_END = "end";

    //Columns in Course table
    private static final String COURSE_CODE = "code";
    private static final String COURSE_TITLE = "title";
    private static final String COURSE_START = "start";
    private static final String COURSE_END = "end";
    private static final String COURSE_STATUS = "status";
    private static final String COURSE_DESC = "desc";
    private static final String COURSE_TERM_ID = "termId";
    private static final String COURSE_START_ALERT = "startAlert";
    private static final String COURSE_END_ALERT = "endAlert";


    //Columns in Mentors table
    private static final String MENTOR_COURSE_ID = "courseId";
    private static final String MENTOR_NAME = "name";
    private static final String MENTOR_NUMBER = "number";
    private static final String MENTOR_EMAIL = "email";

    //Columns in Assessments table
    private static final String ASS_GOAL = "goal";
    private static final String ASS_DETAILS = "details";
    private static final String ASS_COURSEID = "courseId";
    private static final String ASS_TYPE = "type";
    private static final String ASS_ALERT = "assAlert";

    //Columns in Notes table
    private static final String NOTE_COURSEID = "courseId";
    private static final String NOTE_NOTE = "note";
    private static final String NOTE_IMAGE = "image";

    //Create tables
    private static final String CREATE_TABLE_TERMS = "CREATE TABLE " + TABLE_TERMS + "( " + ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + CREATEDAT + " TIMESTAMP DEFAULT (DATETIME('now'))," +
            TERM_TITLE + " TEXT," + TERM_START + " DATE," + TERM_END + " DATE)";
    private static final String CREATE_TABLE_COURSE = "CREATE TABLE " + TABLE_COURSES + "(" + ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + CREATEDAT + " TIMESTAMP DEFAULT (DATETIME('now'))," +
            COURSE_TITLE + " TEXT," + COURSE_START + " DATE," + COURSE_END + " DATE," + COURSE_CODE + " INTEGER," + COURSE_TERM_ID + " INTEGER," +
            COURSE_STATUS + " TEXT," + COURSE_DESC + " TEXT," + COURSE_START_ALERT + " TEXT," + COURSE_END_ALERT + " TEXT)";
    private static final String CREATE_TABLE_MENTORS = "CREATE TABLE " + TABLE_MENTORS + "(" + ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + CREATEDAT + " TIMESTAMP DEFAULT (DATETIME('now'))," +
            MENTOR_NAME + " TEXT," + MENTOR_COURSE_ID + " INTEGER," + MENTOR_NUMBER + " TEXT," + MENTOR_EMAIL + " TEXT)";
    private static final String CREATE_TABLE_ASS = "CREATE TABLE " + TABLE_ASSESSMENT + "(" + ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + CREATEDAT + " TIMESTAMP DEFAULT (DATETIME('now'))," +
            ASS_COURSEID + " INTEGER," + ASS_DETAILS + " TEXT," + ASS_GOAL + " DATE," + ASS_TYPE + " TEXT," + ASS_ALERT + " TEXT)";
    private static final String CREATE_TABLE_NOTES = "CREATE TABLE " + TABLE_NOTES + "(" + ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + CREATEDAT + " TIMESTAMP DEFAULT (DATETIME('now'))," +
            NOTE_COURSEID + " INTEGER," + NOTE_NOTE + " TEXT," + NOTE_IMAGE + " BLOB)";

    List<Term> terms = new ArrayList<Term>();
    List<Course> courses = new ArrayList<Course>();
    List<Mentor> mentors = new ArrayList<Mentor>();
    List<Note> notes = new ArrayList<Note>();
    List<Assessment> asses = new ArrayList<Assessment>();

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TERMS);
        db.execSQL(CREATE_TABLE_COURSE);
        db.execSQL(CREATE_TABLE_MENTORS);
        db.execSQL(CREATE_TABLE_ASS);
        db.execSQL(CREATE_TABLE_NOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENTORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSESSMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);

        // create new tables
        onCreate(db);
    }
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    public long createTerm(Term term) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TERM_TITLE, term.getTitle());
        values.put(TERM_START,  term.getStart().toString());
        values.put(TERM_END, term.getEnd().toString());
        long term_id = db.insert(TABLE_TERMS,null,values);
        return term_id;
    }
    public List<Term> getAllTerms(){
        String selectQuery = "SELECT * from TERMS";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Term term = new Term();
                term.setTitle(c.getString(c.getColumnIndex(TERM_TITLE)));
                term.setStart(c.getString(c.getColumnIndex(TERM_START)));
                term.setEnd(c.getString(c.getColumnIndex(TERM_END)));
                term.setId(c.getInt(c.getColumnIndex(ID)));
                term.setIsSelected(false);

                terms.add(term);
            } while (c.moveToNext());
        }
        return terms;
    }

    public List<Course> getAllCourses(){
        String selectQuery = "SELECT * from Courses";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Course course = new Course();
                course.setId(c.getInt((c.getColumnIndex(ID))));
                course.setTitle(c.getString(c.getColumnIndex(COURSE_TITLE)));
                course.setCode(c.getString(c.getColumnIndex(COURSE_CODE)));
                course.setStart(c.getString(c.getColumnIndex(COURSE_START)));
                course.setEnd(c.getString(c.getColumnIndex(COURSE_END)));
                course.setStatus(c.getString(c.getColumnIndex(COURSE_STATUS)));
                course.setDesc(c.getString(c.getColumnIndex(COURSE_DESC)));
                course.setTermId(c.getInt(c.getColumnIndex(COURSE_TERM_ID)));
                course.setsAlert(c.getString(c.getColumnIndex(COURSE_START_ALERT)));
                course.seteAlert(c.getString(c.getColumnIndex(COURSE_END_ALERT)));

                courses.add(course);
            } while (c.moveToNext());
        }
        return courses;
    }
    public long createCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COURSE_CODE, course.getCode());
        values.put(COURSE_TITLE,  course.getTitle());
        values.put(COURSE_START, course.getStart().toString());
        values.put(COURSE_END, course.getEnd().toString());
        values.put(COURSE_STATUS,  course.getStatus());
        values.put(COURSE_DESC,  course.getDesc());
        values.put(COURSE_START_ALERT, course.getsAlert());
        values.put(COURSE_END_ALERT, course.geteAlert());
        long course_id = db.insert(TABLE_COURSES,null,values);
        return course_id;
    }
    public int setTermID(int courseId, int termId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if(termId == 0){
            values.put(COURSE_TERM_ID, (byte[]) null);
        }else{
            values.put(COURSE_TERM_ID, termId);
        }

        // updating row
        return db.update(TABLE_COURSES, values, ID + " = ?",
                new String[] { String.valueOf(courseId) });
    }
    public List<Course> getCoursesNoId(){
        String selectQuery = "SELECT * from Courses where termId is null";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Course course = new Course();
                course.setId(c.getInt((c.getColumnIndex(ID))));
                course.setTitle(c.getString(c.getColumnIndex(COURSE_TITLE)));
                course.setCode(c.getString(c.getColumnIndex(COURSE_CODE)));
                course.setStart(c.getString(c.getColumnIndex(COURSE_START)));
                course.setEnd(c.getString(c.getColumnIndex(COURSE_END)));
                course.setStatus(c.getString(c.getColumnIndex(COURSE_STATUS)));
                course.setDesc(c.getString(c.getColumnIndex(COURSE_DESC)));

                courses.add(course);
            } while (c.moveToNext());
        }
        return courses;
    }
    public List<Course> getCoursesByTermId(int id){
        String selectQuery = "SELECT * from Courses where termId = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Course course = new Course();
                course.setId(c.getInt((c.getColumnIndex(ID))));
                course.setTitle(c.getString(c.getColumnIndex(COURSE_TITLE)));
                course.setCode(c.getString(c.getColumnIndex(COURSE_CODE)));
                course.setStart(c.getString(c.getColumnIndex(COURSE_START)));
                course.setEnd(c.getString(c.getColumnIndex(COURSE_END)));
                course.setStatus(c.getString(c.getColumnIndex(COURSE_STATUS)));
                course.setDesc(c.getString(c.getColumnIndex(COURSE_DESC)));
                course.setTermId(c.getInt(c.getColumnIndex(COURSE_TERM_ID)));
                course.setsAlert(c.getString(c.getColumnIndex(COURSE_START_ALERT)));
                course.seteAlert(c.getString(c.getColumnIndex(COURSE_END_ALERT)));


                courses.add(course);
            } while (c.moveToNext());
        }
        return courses;
    }
    public List<Course> getCoursesListForEdit(int id){
        String selectQuery = "SELECT * from Courses where termId is null or termId  = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Course course = new Course();
                course.setId(c.getInt((c.getColumnIndex(ID))));
                course.setTitle(c.getString(c.getColumnIndex(COURSE_TITLE)));
                course.setCode(c.getString(c.getColumnIndex(COURSE_CODE)));
                course.setStart(c.getString(c.getColumnIndex(COURSE_START)));
                course.setEnd(c.getString(c.getColumnIndex(COURSE_END)));
                course.setStatus(c.getString(c.getColumnIndex(COURSE_STATUS)));
                course.setDesc(c.getString(c.getColumnIndex(COURSE_DESC)));
                course.setTermId(c.getInt(c.getColumnIndex(COURSE_TERM_ID)));


                courses.add(course);
            } while (c.moveToNext());
        }
        return courses;
    }
    public int updateTerm(String title, String start,String end, int termId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TERM_TITLE, title);
        values.put(TERM_START, start);
        values.put(TERM_END, end);

        // updating row
        return db.update(TABLE_TERMS, values, ID + " = ?",
                new String[] { String.valueOf(termId) });
    }
    public void deleteTerm(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_TERMS, ID + " = ?",
                new String[] { String.valueOf(id) });
    }
    public List<Mentor> getMentorByCourseId(int id){
        String selectQuery = "SELECT * from Mentors where courseId = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Mentor mentor = new Mentor();
                mentor.setId(c.getInt((c.getColumnIndex(ID))));
                mentor.setName(c.getString(c.getColumnIndex(MENTOR_NAME)));
                mentor.setNumber(c.getString(c.getColumnIndex(MENTOR_NUMBER)));
                mentor.setEmail(c.getString(c.getColumnIndex(MENTOR_EMAIL)));
                mentor.setCourseId(c.getInt(c.getColumnIndex(MENTOR_COURSE_ID)));


                mentors.add(mentor);
            } while (c.moveToNext());
        }
        return mentors;
    }
    public List<Mentor> getMentorForEdit(int id){
        String selectQuery = "SELECT * from Mentors where courseId is null or courseId = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Mentor mentor = new Mentor();
                mentor.setId(c.getInt((c.getColumnIndex(ID))));
                mentor.setName(c.getString(c.getColumnIndex(MENTOR_NAME)));
                mentor.setNumber(c.getString(c.getColumnIndex(MENTOR_NUMBER)));
                mentor.setEmail(c.getString(c.getColumnIndex(MENTOR_EMAIL)));
                mentor.setCourseId(c.getInt(c.getColumnIndex(MENTOR_COURSE_ID)));


                mentors.add(mentor);
            } while (c.moveToNext());
        }
        return mentors;
    }
    public List<Mentor> getAllMentors(){
        String selectQuery = "SELECT * from Mentors";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Mentor mentor = new Mentor();
                mentor.setId(c.getInt((c.getColumnIndex(ID))));
                mentor.setName(c.getString(c.getColumnIndex(MENTOR_NAME)));
                mentor.setNumber(c.getString(c.getColumnIndex(MENTOR_NUMBER)));
                mentor.setEmail(c.getString(c.getColumnIndex(MENTOR_EMAIL)));

                mentors.add(mentor);
            } while (c.moveToNext());
        }
        return mentors;
    }
    public long createMentor(Mentor m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MENTOR_NUMBER, m.getNumber());
        values.put(MENTOR_NAME,  m.getName());
        values.put(MENTOR_EMAIL, m.getEmail());
        long mentor_id = db.insert(TABLE_MENTORS,null,values);
        return mentor_id;
    }
    public void deleteMentor(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_MENTORS, ID + " = ?",
                new String[] { String.valueOf(id) });
    }
    public void deleteCourse(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_COURSES, ID + " = ?",
                new String[] { String.valueOf(id) });
    }
    public int setCourseID(int mentorId, int courseId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if(courseId == 0){
            values.put(MENTOR_COURSE_ID, (byte[]) null);
        }else{
            values.put(MENTOR_COURSE_ID, courseId);
        }

        // updating row
        return db.update(TABLE_MENTORS, values, ID + " = ?",
                new String[] { String.valueOf(mentorId) });
    }
    public int updateMentor(String name, String number,String email, int mentorId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MENTOR_NAME, name);
        values.put(MENTOR_NUMBER, number);
        values.put(MENTOR_EMAIL, email);

        // updating row
        return db.update(TABLE_MENTORS, values, ID + " = ?",
                new String[] { String.valueOf(mentorId) });
    }
    public int updateCourse(String code, String title,String start,String end,String status, int courseId, String sAlert, String eAlert){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COURSE_CODE, code);
        values.put(COURSE_TITLE, title);
        values.put(COURSE_START, start);
        values.put(COURSE_END, end);
        values.put(COURSE_STATUS, status);
        values.put(COURSE_START_ALERT, sAlert);
        values.put(COURSE_END_ALERT, eAlert);

        // updating row
        return db.update(TABLE_COURSES, values, ID + " = ?",
                new String[] { String.valueOf(courseId) });
    }
    public long createASS(Assessment ass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ASS_COURSEID, ass.getCourseId());
        values.put(ASS_DETAILS,  ass.getDetails());
        values.put(ASS_GOAL,  ass.getGoalDate());
        values.put(ASS_TYPE, ass.getType());
        values.put(ASS_ALERT, ass.getAlert());
        long ass_id = db.insert(TABLE_ASSESSMENT,null,values);
        return ass_id;
    }
    public List<Note> getNoteByCourseID(int id){
        String selectQuery = "SELECT * from Notes where courseId = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(c.getInt(c.getColumnIndex(ID)));
                note.setCourseId(c.getInt(c.getColumnIndex(NOTE_COURSEID)));
                note.setNote(c.getString(c.getColumnIndex(NOTE_NOTE)));
                note.setImage(c.getBlob(c.getColumnIndex(NOTE_IMAGE)));

                notes.add(note);
            } while (c.moveToNext());
        }
        return notes;
    }
    public long createNote(Note n) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOTE_COURSEID, n.getCourseId());
        values.put(NOTE_NOTE,  n.getNote());
        values.put(NOTE_IMAGE,  n.getImage());
        long note_id = db.insert(TABLE_NOTES,null,values);
        return note_id;
    }
    public void deleteNote(int noteid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, ID + " = ?",
                new String[] { String.valueOf(noteid) });
    }
    public int updateNote(String note, byte[] image, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOTE_NOTE, note);
        values.put(NOTE_IMAGE, image);
        // updating row
        return db.update(TABLE_NOTES, values, ID + " = ?",
                new String[] { String.valueOf(id) });
    }
    public List<Assessment> getAssByCourseID(int id){
        String selectQuery = "SELECT * from Assessments where courseId = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Assessment a = new Assessment();
                a.setId(c.getInt(c.getColumnIndex(ID)));
                a.setCourseId(c.getInt(c.getColumnIndex(ASS_COURSEID)));
                a.setDetails(c.getString(c.getColumnIndex(ASS_DETAILS)));
                a.setGoalDate(c.getString(c.getColumnIndex(ASS_GOAL)));
                a.setType(c.getString(c.getColumnIndex(ASS_TYPE)));
                a.setAlert(c.getString(c.getColumnIndex(ASS_ALERT)));
                System.out.println(c.getString(c.getColumnIndex(ASS_ALERT)));

                asses.add(a);
            } while (c.moveToNext());
        }
        return asses;
    }
    public int updateASS(int id, int courseId, String details, String goalDate, String type, String alert ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ASS_COURSEID, courseId);
        values.put(ASS_DETAILS,  details);
        values.put(ASS_GOAL,  goalDate);
        values.put(ASS_TYPE, type);
        values.put(ASS_ALERT, alert);
        System.out.println(alert + " WHY WONT YOU PUT THIS VALUE IN");
        return db.update(TABLE_ASSESSMENT, values, ID + " = ?",
                new String[] { String.valueOf(id) });
    }
    public void deleteAss(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_ASSESSMENT, ID + " = ?",
                new String[] { String.valueOf(id) });
    }
}
