package com.justin.thompson.studentsched;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static android.R.string.no;
import static com.justin.thompson.studentsched.NoteAdapter.notesList;
import static com.justin.thompson.studentsched.R.id.imageView;
import static junit.runner.Version.id;

public class NewNote extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    ImageView imageView;
    EditText note;
    int courseId;
    DBOpenHelper db;
    byte[] image;
    int noteId;
    boolean edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        imageView = (ImageView)findViewById(R.id.imageView);
        note = (EditText)findViewById(R.id.newNoteTxt);
        db = new DBOpenHelper(getApplicationContext());
        db.getWritableDatabase();
        List<Course> list = CourseDetailAdapter.courseList;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getSelected()){
                Course c = list.get(i);
                courseId = c.getId();
            }
        }
        List<Note> nlist = notesList;
        for(int i = 0; i < nlist.size(); i++){
            if(nlist.get(i).getSelected()){
                Note n = nlist.get(i);
                if(!n.getNote().isEmpty()){
                    note.setText(n.getNote());
                }
                if(n.getImage() != null){
                    image = n.getImage();
                    Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);

                    imageView.setImageBitmap(bmp);
                }
                noteId = n.getid();
                edit = true;
                setTitle("Edit Note");
            }
        }

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            image = stream.toByteArray();
        }
    }

    public void addImage(View view) {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.noteSaveBtn) {
            if(edit == false) {
                Note n = new Note();

                if (image != null) {
                    n.setImage(image);
                }
                if (note != null) {
                    n.setNote(note.getText().toString());
                }
                n.setCourseId(courseId);
                db.createNote(n);
            }else if(edit == true){
                db.updateNote(note.getText().toString(), image, noteId);
            }
            startActivity(new Intent(this, Notes.class));
            return true;
        }
        return false;
    }
}
