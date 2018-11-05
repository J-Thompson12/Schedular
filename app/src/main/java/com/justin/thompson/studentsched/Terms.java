package com.justin.thompson.studentsched;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Property;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;
import static com.justin.thompson.studentsched.R.id.addTerm;

public class Terms extends AppCompatActivity {

    private DBOpenHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        ListView termsLV = (ListView)findViewById(R.id.termsListView);
        db = new DBOpenHelper(getApplicationContext());
        db.getWritableDatabase();
        final List<Term> termsList = db.getAllTerms();
        ArrayAdapter<Term> adapter = new TermAdapter(this, 0, termsList);
        termsLV.setAdapter(adapter);
        AdapterView.OnItemClickListener adapterViewListener = new AdapterView.OnItemClickListener() {

            //on click
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Term term = termsList.get(position);
                Intent intent = new Intent(Terms.this, TermDetail.class);
                startActivity(intent);
                term.setIsSelected(true);
            }
        };
        termsLV.setOnItemClickListener(adapterViewListener);
        db.closeDB();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.terms_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.addTerm) {
            startActivity(new Intent(this, NewTerm.class));
            return true;
        }
        return false;
    }
}
