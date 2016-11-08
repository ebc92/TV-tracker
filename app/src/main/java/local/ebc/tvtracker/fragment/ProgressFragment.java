package local.ebc.tvtracker.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.List;
import local.ebc.tvtracker.R;
import local.ebc.tvtracker.database.DataSource;
import local.ebc.tvtracker.model.Tvshow;

/**
 * Created by ebc on 07.11.2016.
 */

public class ProgressFragment extends Fragment {

    private TextView seenCountText;
    private ProgressBar progressBar;
    private DataSource datasource;

    public ProgressFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_progress, container, false);

        //Initialize views
        progressBar = (ProgressBar) v.findViewById(R.id.fragment_progress);
        seenCountText = (TextView) v.findViewById(R.id.fragment_seenTvshowsText);

        //Update the progressbar
        refreshProgress();

        return v;
    }

    public void refreshProgress(){
        //Create list of all TV shows
        List<Tvshow> tvshows;
        datasource = new DataSource(getActivity());
        tvshows = datasource.getAllTvshows();

        //Count entries in the list, then iterate through it.
        //During iteration finished TV shows are counted.
        int tvshowCount = tvshows.size();
        int finishedCount = 0;
        for (int i=0; i<tvshows.size(); i++) {
            Tvshow tvshow = tvshows.get(i);
            if (tvshow.getStatus().equals("Finished")){
                finishedCount++;
            }
        }

        //The result is presented in the progressBar view.
        String finishedString = "You have finished " + Integer.toString(finishedCount) + "/" + Integer.toString(tvshowCount) + " TV shows.";
        seenCountText.setText(finishedString);
        progressBar.setProgress(finishedCount);
        progressBar.setMax(tvshowCount);
    }
}
