package com.justin.thompson.studentsched;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Thompson on 6/6/17.
 */

public class CourseAdapter extends BaseAdapter {
    private Context context;
    public static List<Course> courseList;

    public CourseAdapter(@NonNull Context context, List<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent){

        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder(); LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.edit_course_layout, null, true);

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.courseCB);
            holder.code = (TextView) convertView.findViewById(R.id.codeTxt);
            holder.title = (TextView) convertView.findViewById(R.id.courseTitleTxt);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }
        holder.code.setText(courseList.get(position).getCode());
        holder.title.setText(courseList.get(position).getTitle());

        holder.checkBox.setChecked(courseList.get(position).getSelected());

        holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag( position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View tempview = (View) holder.checkBox.getTag(R.integer.btnplusview);
                Integer pos = (Integer)  holder.checkBox.getTag();
                Course course = courseList.get(position);

                if(courseList.get(pos).getSelected()){
                    courseList.get(pos).setSelected(false);
                }else {
                    courseList.get(pos).setSelected(true);
                }

            }
        });

        return convertView;
    }

    private class ViewHolder {

        protected CheckBox checkBox;
        private TextView code;
        private TextView title;

    }
}
