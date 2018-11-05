package com.justin.thompson.studentsched;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.description;
import static android.R.attr.resource;
import static com.justin.thompson.studentsched.CourseAdapter.courseList;

/**
 * Created by Thompson on 6/6/17.
 */

public class TermAdapter extends ArrayAdapter<Term>{
    private Context context;
    public static List<Term> termList;

    public TermAdapter(@NonNull Context context, int resource, List<Term> termList) {
        super(context, resource, termList);
        this.context = context;
        this.termList = termList;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        //get the property we are displaying
        Term term = termList.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.term_layout, null);

        TextView title = (TextView) view.findViewById(R.id.termTxtView);
        TextView start = (TextView) view.findViewById(R.id.startTxtView);
        TextView end = (TextView) view.findViewById(R.id.endTxtView);

        //set price and rental attributes
        title.setText(term.getTitle());
        start.setText(term.getStart());
        end.setText(term.getEnd());
        return view;
    }

}
