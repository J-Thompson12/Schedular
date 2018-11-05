package com.justin.thompson.studentsched;

/**
 * Created by Thompson on 6/21/17.
 */

public class Assessment {
    String details;
    String goalDate;
    int courseId;
    int id;
    String type;
    boolean selected;
    String alert;

    public Assessment(){

    }
    public Assessment(String goalDate, int courseId, String type, String details){
        this.goalDate = goalDate;
        this.courseId = courseId;
        this.type = type;
        this.details=details;
    }
    public void setDetails(String details){
        this.details=details;
    }
    public String getDetails(){
        return details;
    }
    public void setGoalDate(String goalDate){
        this.goalDate=goalDate;
    }
    public String getGoalDate(){
        return goalDate;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getid(){
        return id;
    }
    public void setCourseId(int courseId){
        this.courseId=courseId;
    }
    public int getCourseId(){
        return courseId;
    }
    public void setType(String type){
        this.type=type;
    }
    public String getType(){
        return type;
    }
    public void setSelected(boolean selected){
        this.selected = selected;
    }
    public boolean getSelected(){
        return selected;
    }
    public void setAlert(String alert){
        this.alert=alert;
    }
    public String getAlert(){
        return alert;
    }
}
