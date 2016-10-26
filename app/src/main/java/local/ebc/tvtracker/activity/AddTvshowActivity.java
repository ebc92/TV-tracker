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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import com.bumptech.glide.Glide;
import com.tangxiaolv.telegramgallery.GalleryActivity;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import local.ebc.tvtracker.database.DataSource;
import local.ebc.tvtracker.model.Tvshow;
import local.ebc.tvtracker.R;

public class AddTvshowActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DataSource datasource;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private ImageView imageView;
    private String path;
    private RatingBar ratingBar;
    private String dateAdded;
    private Spinner statusSpinner;
    private Boolean pathSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tvshow);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pathSet = false;

        //initialize views
        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        imageView = (ImageView) findViewById(R.id.imageView);
        statusSpinner = (Spinner) findViewById(R.id.spinner);
        ratingBar = (RatingBar) findViewById(R.id.tvshow_rating_bar_add);
        datasource = new DataSource(this);

        //Fill the spinner with items.
        ArrayAdapter statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.tvshow_status, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);

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
                    dateAdded = getSimpleCurrentDate();
                    int rating = Math.round(ratingBar.getRating());

                    Tvshow tvshow = new Tvshow(0, editTextTitle.getText().toString(), editTextDescription.getText().toString(), path, rating, dateAdded, statusSpinner.getSelectedItem().toString());
                    long tvshowId = datasource.createTvshow(tvshow);
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


    // Get the chosen icon for the TV Show from the GalleryActivity
    // and load it into imageView with Glide dependency.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 21) {
                //TODO: exception when result and thumb = null
                List<String> thumb = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);
                path = thumb.get(0);
                pathSet = true;
                Glide.with(this).load(path).into(imageView);
            }
        }

    private static String getSimpleCurrentDate() {
        // Formatter that will convert dates into the day-month-year format
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        //Today's date, but with time included, which we don't want
        Date today = new Date();
        // Format.format returns a string
        return format.format(today);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
