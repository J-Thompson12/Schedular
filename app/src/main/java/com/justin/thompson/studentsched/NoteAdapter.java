package com.justin.thompson.studentsched;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Thompson on 6/28/17.
 */

public class NoteAdapter extends ArrayAdapter<Note>{
    private Context context;
    public static List<Note> notesList;

    public NoteAdapter(@NonNull Context context, int resource, List<Note> notesList) {
        super(context, resource, notesList);
        this.context = context;
        this.notesList = notesList;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        //get the property we are displaying
        Note note = notesList.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.course_detail_mentor, null);

        TextView notes = (TextView) view.findViewById(R.id.courseDetailMentorName);

        //set price and rental attributes
        if(note.getNote().isEmpty()){
            notes.setText("Image Only");
        }else{
            notes.setText(note.getNote());
        }

        return view;
    }
}
