package com.justin.thompson.studentsched;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Thompson on 6/25/17.
 */

public class MentorCBAdapter extends BaseAdapter {
    private Context context;
    public static List<Mentor> mentorList;

    public MentorCBAdapter(@NonNull Context context, List<Mentor> mentorList) {
        this.context = context;
        this.mentorList = mentorList;
    }

    @Override
    public int getCount() {
        return mentorList.size();
    }

    @Override
    public Object getItem(int position) {
        return mentorList.get(position);
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
            convertView = inflater.inflate(R.layout.add_course, null, true);

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.editMentorCB);
            holder.code = (TextView) convertView.findViewById(R.id.editMentorName);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }
        holder.code.setText(mentorList.get(position).getName());

        holder.checkBox.setChecked(mentorList.get(position).getSelected());

        holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag( position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View tempview = (View) holder.checkBox.getTag(R.integer.btnplusview);
                Integer pos = (Integer)  holder.checkBox.getTag();
                Mentor mentor = mentorList.get(position);

                if(mentorList.get(pos).getSelected()){
                    mentorList.get(pos).setSelected(false);
                }else {
                    mentorList.get(pos).setSelected(true);
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
