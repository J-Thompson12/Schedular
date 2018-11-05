package com.justin.thompson.studentsched;

import java.util.List;

/**
 * Created by Thompson on 6/6/17.
 */

public class Course {
    int id;
    String code;
    String title;
    String start;
    String end;
    String status;
    String desc;
    int termId;
    boolean selected;
    String sAlert;
    String eAlert;

    public Course(){

    }

    public Course(String code, String title, String start, String end,String status){
        this.code = code;
        this.title = title;
        this.start = start;
        this.end = end;
        this.status = status;

    }
     public int getId()
     {
         return this.id;
     }
     public void setId(int id)
     {
         this.id = id;
     }
     public String getCode(){
         return this.code;
     }
     public void setCode(String code){this.code = code;}
     public String getTitle(){
         return this.title;
     }
     public void setTitle(String title){this.title = title;}
     public String getStart(){
         return this.start;
     }
     public void setStart(String start){this.start = start;}
     public String getEnd(){
         return this.end;
     }
     public void setEnd(String end){this.end = end;}
     public String getStatus(){
         return this.status;
     }
     public void setStatus(String status){this.status = status;}
     public String getDesc(){
         return this.desc;
     }
     public void setDesc(String desc){this.desc = desc;}
     public int getTermId(){
         return this.termId;
     }
    public void setTermId(int termId){this.termId = termId;}
    public void setSelected(boolean selected){
        this.selected = selected;
    }
    public boolean getSelected(){
        return selected;
    }
    public void setsAlert(String sAlert){
        this.sAlert = sAlert;
    }
    public String getsAlert(){
        return sAlert;
    }
    public void seteAlert(String eAlert){
        this.eAlert = eAlert;
    }
    public String geteAlert(){
        return eAlert;
    }

}
