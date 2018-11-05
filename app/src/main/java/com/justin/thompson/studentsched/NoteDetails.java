package com.justin.thompson.studentsched;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static android.R.id.list;
import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static com.justin.thompson.studentsched.NoteAdapter.notesList;
import static java.security.AccessController.getContext;

public class NoteDetails extends AppCompatActivity {

    EditText note;
    ImageView image;
    byte[] imageDB;
    DBOpenHelper db;
    int noteId;
    Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        note = (EditText)findViewById(R.id.noteDetailNoteTxt);
        image = (ImageView)findViewById(R.id.noteDetailsImage);
        db = new DBOpenHelper(getApplicationContext());
        db.getWritableDatabase();
        List<Note> list = notesList;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getSelected()){
                Note n = list.get(i);
                if(!n.getNote().isEmpty()){
                    note.setText(n.getNote());
                }
                if(n.getImage() != null){
                    imageDB = n.getImage();
                    bmp = BitmapFactory.decodeByteArray(imageDB, 0, imageDB.length);

                    image.setImageBitmap(bmp);
                }
                noteId = n.getid();
            }
        }
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.note_share_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.termEditBtn) {
            startActivity(new Intent(this, NewNote.class));
            return true;
        }
        if(item.getItemId() == R.id.termDelBtn){
            System.out.println("note id for deletion " + noteId);
            db.deleteNote(noteId);
            startActivity(new Intent(this, Notes.class));
            return true;
        }
        if(item.getItemId() == R.id.noteShareBtn){
            try {
                File cacheDir = getBaseContext().getCacheDir();
                File file = new File(cacheDir, "pic.png");
                FileOutputStream fOut = null;
                fOut = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();
                file.setReadable(true,false);

                // insert the image and create a path
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(Intent.EXTRA_SUBJECT, "Note");
                i.putExtra(Intent.EXTRA_TEXT   , note.getText().toString());
                i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                i.setType("image/png");
                startActivity(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
