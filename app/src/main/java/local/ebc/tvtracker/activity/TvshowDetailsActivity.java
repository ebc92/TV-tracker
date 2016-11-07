package local.ebc.tvtracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import local.ebc.tvtracker.R;
import local.ebc.tvtracker.database.DataSource;
import local.ebc.tvtracker.fragment.AdvertFragment;
import local.ebc.tvtracker.model.Tvshow;
import local.ebc.tvtracker.utility.ConfirmDeleteDialog;

public class TvshowDetailsActivity extends AppCompatActivity implements ConfirmDeleteDialog.ConfirmDeleteDialogListener {

    private Tvshow tvshow;
    private ImageView icon;
    private TextView title;
    private TextView description;
    private RatingBar rating;
    private TextView dateAdded;
    private TextView status;
    private FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = new AdvertFragment();
        FragmentManager manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.activity_details_adContainer, fragment, "ad_ini");
        transaction.commit();

        //get the tvshow object from the intent
        tvshow = (Tvshow) getIntent().getSerializableExtra("selectedTvshow");
        setTvshowView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TvshowDetailsActivity.this, UpdateTvshowActivity.class);
                intent.putExtra("selectedTvshow", tvshow);
                startActivityForResult(intent, 22);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setTvshowView() {
        //initialize views
        icon = (ImageView) findViewById(R.id.activity_details_imgResource);
        title = (TextView) findViewById(R.id.activity_details_title);
        description = (TextView) findViewById(R.id.activity_details_description);
        dateAdded = (TextView) findViewById(R.id.activity_details_dateAdded);
        status = (TextView) findViewById(R.id.activity_details_status);
        rating = (RatingBar) findViewById(R.id.tvshow_rating_bar_details);

        //fill views with data
        Glide.with(this).load(tvshow.getImgPath()).into(icon);
        title.setText(tvshow.getTitle());
        String dateText = "Date added: " + tvshow.getDateadded();
        dateAdded.setText(dateText);
        String statusText = "Status: " + tvshow.getStatus();
        status.setText(statusText);
        description.setText(tvshow.getDescription());
        float ratingFloat = (float)tvshow.getRating();
        rating.setRating(ratingFloat);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 22) {
            super.onActivityResult(requestCode, resultCode, data);
            tvshow = (Tvshow) data.getSerializableExtra("selectedTvshow");
            setTvshowView();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tvshow_details, menu);
        return true;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        DataSource datasource = new DataSource(this);
        datasource.deleteTvshow(tvshow);
        //showGameDeletedToast();
        finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete_tvshow) {
            //showing deletedialog
            DialogFragment dialog = new ConfirmDeleteDialog();
            Bundle bundle = new Bundle();
            bundle.putString("message", "Are you sure you want to delete the game?");
            bundle.putString("positiveButton", "Yes");
            dialog.setArguments(bundle);
            dialog.show(getSupportFragmentManager(), "ConfirmDeleteDialog");
        }
        return super.onOptionsItemSelected(item);
    }

}
