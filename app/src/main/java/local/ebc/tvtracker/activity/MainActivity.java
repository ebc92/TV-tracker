package local.ebc.tvtracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import local.ebc.tvtracker.adapter.TvshowItemAdapter;
import local.ebc.tvtracker.database.DataSource;
import local.ebc.tvtracker.fragment.ProgressFragment;
import local.ebc.tvtracker.model.Tvshow;
import local.ebc.tvtracker.R;
import local.ebc.tvtracker.utility.ConfirmDeleteDialog;

public class MainActivity extends AppCompatActivity implements ConfirmDeleteDialog.ConfirmDeleteDialogListener{

    //Declare global activity variables.
    public static final String EXTRA_TVSHOW_ID = "extraTvshowId";
    FragmentTransaction transaction;
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

        /* Android Studio generated FloatingActionButton Code.
        The onClickListener starts the "Add new TV show" activity.
         */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTvshowActivity.class);
                startActivity(intent);
            }
        });

        /* The fragmentLoad method is called to load the progress bar fragment.
        Then the TV show list and recycler view is initialized.*/
        fragmentLoad();
        tvshowList = new ArrayList<>();
        tvshowListView = (RecyclerView) findViewById(R.id.recyclerView_tvshow_list);


        /* First he layout manager is assigned to the recycler view. Then The adapter
        is initialized with the tvshowList and assigned to the recycler view.
        Finally, the datasource is refreshed with tvshowListRefresh method.
         */
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        tvshowListView.setLayoutManager(mLayoutManager);
        tvshowListView.setHasFixedSize(true);
        adapter = new TvshowItemAdapter(tvshowList, this);
        tvshowListView.setAdapter(adapter);
        tvshowListRefresh();
    }

    /* Load or refresh the fragment. Fragment is added with .replace
    (over e.g. .add or .attach) to prevent duplicate fragments.
     */
    private void fragmentLoad(){
        Fragment fragment = new ProgressFragment();
        FragmentManager manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.advertisment_container, fragment, "ad_ini");
        transaction.commit();
    }

    /* Create a new instance of the datasource and
    retrieve all tvshows. The tvshows is stored in
    the array tvshowList. The adapter is notified.
    */
    private void tvshowListRefresh() {
        datasource = new DataSource(this);
        tvshowList = datasource.getAllTvshows();
        adapter.updateList(tvshowList);
        adapter.notifyDataSetChanged();
    }

    /* The list and the fragment is refreshed in case the
    data has changed while the activity has been paused.
    This also eliminates the needs for onResult handling.
    */
    @Override
    public void onResume() {
        super.onResume();
        tvshowListRefresh();
        fragmentLoad();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Show option dialog if "Delete all" button is clicked.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* Handle action bar item clicks here. The action bar will
        automatically handle clicks on the Home/Up button, so long
        as you specify a parent activity in AndroidManifest.xml.
       */
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

    /* If the user agrees, all tvshows will be deleted from the
    local list and the local database. The user is notified with
    a toast. List and fragment is refreshed.
     */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        DataSource datasource = new DataSource(this);
        datasource.deleteAllTvshows();
        tvshowList.removeAll(tvshowList);
        Toast toast = Toast.makeText(this, "All TV shows has been deleted.", Toast.LENGTH_LONG);
        toast.show();
        tvshowListRefresh();
        fragmentLoad();
    }

    @Override
    public void onDialogNegativeClick(android.support.v4.app.DialogFragment dialog) {
        //Nothing happens
    }
}
