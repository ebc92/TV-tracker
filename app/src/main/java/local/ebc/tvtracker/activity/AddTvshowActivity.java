package local.ebc.tvtracker.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.tangxiaolv.telegramgallery.GalleryActivity;

import java.io.File;
import java.util.List;


import local.ebc.tvtracker.database.DataSource;
import local.ebc.tvtracker.model.Tvshow;
import local.ebc.tvtracker.R;

public class AddTvshowActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DataSource datasource;
    EditText editTextTitle;
    EditText editTextDescription;
    ImageView imageView;
    String path;
    NumberPicker np_e;
    NumberPicker np_s;
    NumberPicker np_d;
    Boolean pathSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tvshow);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        imageView = (ImageView) findViewById(R.id.imageView);
        datasource = new DataSource(this);
        pathSet = false;


        //OnClickListener for the picture gallery activity
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                GalleryActivity.openActivity(AddTvshowActivity.this, true, 21);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!pathSet){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddTvshowActivity.this);

                    builder.setMessage("You have to choose an image first! Click on the android to choose.")
                            .setTitle("Wait!");

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                if (pathSet) {
                    int episodes = np_e.getValue();
                    int seasons = np_s.getValue();
                    int duration = np_d.getValue();
                    Tvshow tvshow = new Tvshow(0, editTextTitle.getText().toString(), editTextDescription.getText().toString(), path, episodes, seasons, duration);
                    long tvshowId = datasource.createTvshow(tvshow);

                    Toast toast = Toast.makeText(getApplicationContext(), Long.toString(tvshowId), Toast.LENGTH_LONG);
                    Intent data = new Intent();
                    data.putExtra(MainActivity.EXTRA_TVSHOW_ID, tvshowId);
                    //Send the result back to the activity
                    setResult(Activity.RESULT_OK, data);
                    //Finish this activity
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 21) {
                //TODO: exception when result and thumb = null
                List<String> thumb = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);
                path = thumb.get(0);
                pathSet = true;
                makeThumbnail(thumb.get(0));
            }
        }
    public void makeThumbnail(String path){
        File imgFile = new File(path);

        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);

        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
