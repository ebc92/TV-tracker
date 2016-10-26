package local.ebc.tvtracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;

import local.ebc.tvtracker.adapter.TvshowItemAdapter;
import local.ebc.tvtracker.database.DataSource;
import local.ebc.tvtracker.model.Tvshow;
import local.ebc.tvtracker.R;
import local.ebc.tvtracker.utility.ConfirmDeleteDialog;

public class MainActivity extends AppCompatActivity implements ConfirmDeleteDialog.ConfirmDeleteDialogListener{
    public static final String EXTRA_TVSHOW_ID = "extraTvshowId";
    private DataSource datasource;
    private List<Tvshow> tvshowList;
    private RecyclerView tvshowListView;
    private TvshowItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTvshowActivity.class);
                startActivityForResult(intent, 11);
            }
        });

        tvshowList = new ArrayList<>();
        tvshowListView = (RecyclerView) findViewById(R.id.recyclerView_tvshow_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        tvshowListView.setLayoutManager(mLayoutManager);
        tvshowListView.setHasFixedSize(true);
        adapter = new TvshowItemAdapter(tvshowList, this);
        tvshowListView.setAdapter(adapter);
        tvshowListRefresh();
    }

    private void tvshowListRefresh() {
        datasource = new DataSource(this);
        tvshowList = datasource.getAllTvshows();
        adapter.updateList(tvshowList);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onResume() {
        super.onResume();
        tvshowListRefresh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11 && resultCode == RESULT_OK) {
            int tvshowId = data.getIntExtra(EXTRA_TVSHOW_ID, -1);
            if (tvshowId != -1) {
                datasource = new DataSource(this);
                Tvshow tvshow = datasource.getTvshow(tvshowId);
                tvshowList.add(tvshow);
                tvshowListRefresh();
            }
        }
        if (requestCode == 31 && resultCode == RESULT_OK) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_delete_all) {
            //showing the deletedialog
            DialogFragment dialog = new ConfirmDeleteDialog();
            Bundle bundle = new Bundle();
            bundle.putString("message", "You are about to delete all TV shows! Are you sure?");
            bundle.putString("positiveButton", "Delete All");
            dialog.setArguments(bundle);
            dialog.show(getSupportFragmentManager(), "ConfirmDeleteDialog");

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // Create a DBCRUD object, and pass it the context of this activity
        DataSource datasource = new DataSource(this);
        // Delete the list of games from Database
        datasource.deleteAllTvshows();
        // Remove all games from temporary list
        tvshowList.removeAll(tvshowList);
        // Display toast with Feedback
        //showToast(getString(R.string.action_database_clear));
        // Notify adapter Content has changed
        tvshowListRefresh();
    }

    @Override
    public void onDialogNegativeClick(android.support.v4.app.DialogFragment dialog) {

    }
}
