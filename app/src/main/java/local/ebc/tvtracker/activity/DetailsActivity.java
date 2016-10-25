package local.ebc.tvtracker.activity;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import local.ebc.tvtracker.database.DataSource;
import local.ebc.tvtracker.model.Tvshow;
import local.ebc.tvtracker.R;
import local.ebc.tvtracker.utility.ConfirmDeleteDialog;

public class DetailsActivity extends AppCompatActivity  implements ConfirmDeleteDialog.ConfirmDeleteDialogListener {

    Tvshow tvshow;
    private ImageView icon;
    private TextView title;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvshow = (Tvshow) getIntent().getSerializableExtra("selectedTvshow");
        setTvshowView();
    }

    private void setTvshowView() {
        icon = (ImageView) findViewById(R.id.activity_details_imgResource);
        title = (TextView) findViewById(R.id.activity_details_title);
        description = (TextView) findViewById(R.id.activity_details_description);

        Glide.with(this).load(tvshow.getImgPath()).into(icon);
        title.setText(tvshow.getTitle());
        description.setText(tvshow.getDescription());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tvshow_details, menu);
        return true;
    }
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User clicked on the confirm button of the Dialog, delete the game from Database
        DataSource datasource = new DataSource(this);
        // We only need the id of the game to delete it
        datasource.deleteTvshow(tvshow);
        // Game has been deleted, go back to MainActivity
        //showGameDeletedToast();
        finish();
    }
    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // Do nothing, Dialog will disappear
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete_tvshow) {
            // Show the ConfirmDeleteDialog
            DialogFragment dialog = new ConfirmDeleteDialog();
            Bundle bundle = new Bundle();
            bundle.putString("message", "Are you sure you want to delete the game?");
            bundle.putString("positiveButton", "positive");
            dialog.setArguments(bundle);
            dialog.show(getSupportFragmentManager(), "ConfirmDeleteDialog");
        }
        return super.onOptionsItemSelected(item);
    }
}