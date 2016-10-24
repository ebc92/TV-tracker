package local.ebc.tvtracker.activity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import local.ebc.tvtracker.database.DataSource;
import local.ebc.tvtracker.model.Tvshow;
import local.ebc.tvtracker.R;

public class DetailsActivity extends AppCompatActivity {
    private ImageView icon;
    private TextView title;
    private TextView description;
    private TextView runtimeDetails;
    private DataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        icon = (ImageView) findViewById(R.id.activity_details_imgResource);
        title = (TextView) findViewById(R.id.activity_details_title);
        description = (TextView) findViewById(R.id.activity_details_description);
        runtimeDetails = (TextView) findViewById(R.id.activity_details_runtimeDetails);

        datasource = new DataSource(this);
        //Get the values from the intent
        long id = getIntent().getLongExtra("id", 1);
        Tvshow tvshow = datasource.getTvshow(id);
        File imgFile = new File(tvshow.getImgPath());
        Bitmap myBitmap;
        if(imgFile.exists()){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            icon.setImageBitmap(myBitmap);
        } else {
            icon.setImageResource(R.mipmap.ic_launcher);
        }

        //Set the values from the intent to the views
        title.setText(tvshow.getTitle());
        description.setText(tvshow.getDescription());

        int hours = (tvshow.getSeasons() * tvshow.getEpisodes()) * tvshow.getDuration();
        runtimeDetails.setText(tvshow.getTitle() + " has " + Integer.toString(tvshow.getSeasons()) + " seasons of " + Integer.toString(tvshow.getEpisodes()) + " episodes, totaling at " + hours + " hours of runtime.");

    }
}