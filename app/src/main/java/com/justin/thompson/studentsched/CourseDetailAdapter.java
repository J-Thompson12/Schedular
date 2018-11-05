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

import static android.media.CamcorderProfile.get;
import static com.justin.thompson.studentsched.TermAdapter.termList;

/**
 * Created by Thompson on 6/19/17.
 */

public class CourseDetailAdapter extends ArrayAdapter<Course> {
    private Context context;
    public static List<Course> courseList;

    public CourseDetailAdapter(@NonNull Context context, int resource, List<Course> courseList) {
        super(context, resource, courseList);
        this.context = context;
        this.courseList = courseList;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        //get the property we are displaying
        Course course = courseList.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.term_detail, null);

        TextView code = (TextView) view.findViewById(R.id.courseCodeTxt);
        TextView title = (TextView) view.findViewById(R.id.courseTitleTxt);

        //set price and rental attributes
        title.setText(course.getTitle());
        code.setText(course.getCode());
        return view;
    }

}
