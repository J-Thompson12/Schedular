package com.justin.thompson.studentsched;

import java.sql.Blob;

/**
 * Created by Thompson on 6/27/17.
 */

public class Note {
    int id;
    String note;
    byte[]  image;
    int courseId;
    boolean selected;

    public Note(){

    }
    public Note(String note, int courseId) {
        this.note = note;
        this.courseId = courseId;

    }
    public Note(String note,byte[] image, int courseId) {
        this.note = note;
        this.image = image;
        this.courseId = courseId;

    }
    public void setNote(String note){
        this.note=note;
    }
    public String getNote(){
        return this.note;
    }
    public void setImage(byte[] image){
        this.image=image;
    }
    public byte[] getImage(){
        return this.image;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getid(){
        return this.id;
    }
    public void setCourseId(int courseId){
        this.courseId=courseId;
    }
    public int getCourseId(){
        return this.courseId;
    }
    public void setSelected(boolean selected){
        this.selected = selected;
    }
    public boolean getSelected(){
        return this.selected;
    }
}
