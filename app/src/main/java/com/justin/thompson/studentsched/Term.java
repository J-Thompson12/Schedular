package com.justin.thompson.studentsched;

import java.sql.Date;
import java.util.List;

/**
 * Created by Thompson on 5/30/17.
 */

public class Term {
    String title;
    String start;
    String end;
    int id;
    Boolean selected;

    public Term(){

    }
    public Term(String title, String start, String end){
        this.title = title;
        this.start = start;
        this.end = end;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }
    public void setStart(String start){
        this.start = start;
    }
    public String getStart(){
        return this.start;
    }
    public void setEnd(String end){
        this.end = end;
    }
    public String getEnd(){
        return this.end;
    }
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return this.id;
    }
    public void setIsSelected(Boolean selected){
        this.selected = selected;
    }
    public boolean getIsSelected(){
        return selected;
    }
}
