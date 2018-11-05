package com.justin.thompson.studentsched;

/**
 * Created by Thompson on 6/20/17.
 */

class Mentor {

    String name;
    String number;
    String email;
    int courseId;
    int id;
    boolean selected;

    public Mentor(){

    }

    public Mentor(String name, String number, String email){
        this.name = name;
        this.number = number;
        this.email = email;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setNumber(String number){
        this.number = number;
    }
    public String getNumber(){
        return number;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }
    public void setCourseId(int courseId){
        this.courseId = courseId;
    }
    public int getCourseId(){
        return courseId;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setSelected(boolean selected){
        this.selected = selected;
    }
    public boolean getSelected(){
        return selected;
    }
}
