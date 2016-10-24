package local.ebc.tvtracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_TVSHOW_ID = "extraTvshowId";
    DataSource datasource;
    List<Tvshow> tvshowList;
    RecyclerView tvshowListView;
    TvshowItemAdapter adapter;

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tvshowList = new ArrayList<Tvshow>();
        // tvshowListView = (RecyclerView) findViewByID(R.id.recyclerView_tvshow_list);
        tvshowListView = (RecyclerView) findViewById(R.id.recyclerView_tvshow_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        tvshowListView.setLayoutManager(mLayoutManager);
        tvshowListView.setHasFixedSize(true);
        adapter = new TvshowItemAdapter(tvshowList, this);
        Tvshow tvshow = new Tvshow(0, "Stranger Things", "Sci-Fi", "path", 1, 1, 1);
        tvshowListView.setAdapter(adapter);
        tvshowList.add(tvshow);
        adapter.notifyDataSetChanged();
    }
    private void tvshowListRefresh(){
        datasource = new DataSource(this);
        tvshowList = datasource.getAllTvshows();
        adapter.updateList(tvshowList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //TODO: resultcode && requestcode
        if (requestCode == 11 && resultCode == RESULT_OK) {
            long tvshowId = data.getLongExtra(EXTRA_TVSHOW_ID, -1);
            if (tvshowId != -1) {
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
