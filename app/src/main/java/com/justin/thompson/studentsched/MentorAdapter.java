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
 * Created by Thompson on 6/21/17.
 */

public class MentorAdapter extends ArrayAdapter<Mentor> {
    private Context context;
    public static List<Mentor> mentorList;

    public MentorAdapter(@NonNull Context context, int resource, List<Mentor> mentorList) {
        super(context, resource, mentorList);
        this.context = context;
        this.mentorList = mentorList;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        //get the property we are displaying
        Mentor mentor = mentorList.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.course_detail_mentor, null);

        TextView title = (TextView) view.findViewById(R.id.courseDetailMentorName);

        //set price and rental attributes
        title.setText(mentor.getName());
        return view;
    }
}
