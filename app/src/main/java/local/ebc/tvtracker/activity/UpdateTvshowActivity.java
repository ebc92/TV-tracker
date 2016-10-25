package local.ebc.tvtracker.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;

import com.tangxiaolv.telegramgallery.GalleryActivity;

import java.io.File;

import local.ebc.tvtracker.R;
import local.ebc.tvtracker.database.DataSource;
import local.ebc.tvtracker.model.Tvshow;

public class UpdateTvshowActivity extends AppCompatActivity {
    DataSource datasource;
    EditText editTextTitle;
    EditText editTextDescription;
    ImageView imageView;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tvshow);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        imageView = (ImageView) findViewById(R.id.imageView);
        datasource = new DataSource(this);


        //getting and displaying tvshow in views
        final long id = getIntent().getLongExtra("id", 1);
        Tvshow tvshow = datasource.getTvshow(id);
        editTextTitle.setText(tvshow.getTitle());
        editTextDescription.setText(tvshow.getDescription());
        path = tvshow.getImgPath();


        //OnClickListener for the picture gallery activity
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                GalleryActivity.openActivity(UpdateTvshowActivity.this, true, 31);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Tvshow tvshow = new Tvshow(id, editTextTitle.getText().toString(), editTextDescription.getText().toString(), path, episodes, seasons, duration);
                datasource.updateTvshow(tvshow);
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                //Finish this activity */
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
