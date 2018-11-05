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

import static com.justin.thompson.studentsched.AssAdapter.assList;

/**
 * Created by Thompson on 7/1/17.
 */

public class AssAdapter extends ArrayAdapter<Assessment>{

    private Context context;
    public static List<Assessment> assList;

    public AssAdapter(@NonNull Context context, int resource, List<Assessment> assList) {
        super(context, resource, assList);
        this.context = context;
        this.assList = assList;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        //get the property we are displaying
        Assessment ass = assList.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.course_detail_mentor, null);

        TextView type = (TextView) view.findViewById(R.id.courseDetailMentorName);

        //set price and rental attributes
        type.setText(ass.getType());

        return view;
    }

}
