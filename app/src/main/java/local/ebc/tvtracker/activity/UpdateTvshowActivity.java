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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tangxiaolv.telegramgallery.GalleryActivity;

import java.io.File;
import java.util.List;

import local.ebc.tvtracker.R;
import local.ebc.tvtracker.database.DataSource;
import local.ebc.tvtracker.model.Tvshow;

public class UpdateTvshowActivity extends AppCompatActivity {

    //Declare global activity variables
    private EditText editTextTitle;
    private EditText editTextDescription;
    private ImageView imageView;
    private Tvshow tvshow;
    private Spinner statusSpinner;
    private RatingBar ratingBar;
    private TextView dateText;
    private DataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tvshow);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initializing views
        editTextTitle = (EditText) findViewById(R.id.activity_update_title);
        editTextDescription = (EditText) findViewById(R.id.activity_update_description);
        imageView = (ImageView) findViewById(R.id.activity_update_imageView);
        statusSpinner = (Spinner) findViewById(R.id.activity_update_status);
        dateText = (TextView) findViewById(R.id.activity_update_dateAdded);
        ratingBar = (RatingBar) findViewById(R.id.activity_update_ratingBar);
        datasource = new DataSource(this);

        //getting and displaying tvshow in views
        Intent intent = getIntent();
        tvshow = (Tvshow) intent.getSerializableExtra("selectedTvshow");
        editTextTitle.setText(tvshow.getTitle());
        editTextDescription.setText(tvshow.getDescription());
        dateText.setText((tvshow.getDateadded()));
        Glide.with(this).load(tvshow.getImgPath()).into(imageView);
        ArrayAdapter statusAdapter = ArrayAdapter.createFromResource(this, R.array.tvshow_status, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);
        ratingBar.setRating(tvshow.getRating());

        //OnClickListener for the picture gallery activity
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryActivity.openActivity(UpdateTvshowActivity.this, true, 31);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the view values and update the database.
                int rating = Math.round(ratingBar.getRating());
                Tvshow tvshowNew = new Tvshow(tvshow.getId(), editTextTitle.getText().toString(), editTextDescription.getText().toString(), tvshow.getImgPath(), rating, tvshow.getDateadded(), statusSpinner.getSelectedItem().toString());
                datasource.updateTvshow(tvshowNew);

                //Send the updated tvshow back to the details activity.
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedTvshow", tvshowNew);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    // Handle new thumbnail picks in the gallery activity.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 31) {
            List<String> selectedPath = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);
            String path = selectedPath.get(0);
            tvshow.setImgPath(path);
            Glide.with(this).load(path).into(imageView);
        }
    }
}
