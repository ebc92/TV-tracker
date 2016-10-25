package local.ebc.tvtracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import local.ebc.tvtracker.activity.DetailsActivity;
import local.ebc.tvtracker.activity.MainActivity;
import local.ebc.tvtracker.model.Tvshow;
import local.ebc.tvtracker.R;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by ebc on 19.10.2016.
 */

public class TvshowItemAdapter extends RecyclerView.Adapter<TvshowItemAdapter.ViewHolder> {
    private final List<Tvshow> tvshowArrayList;
    private final Context context;
    File imgThumbnail;

    public TvshowItemAdapter(List<Tvshow> list, Context context){
        tvshowArrayList = list;
        this.context = context;
    }

    @Override
    public TvshowItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tvshow_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TvshowItemAdapter.ViewHolder holder, int position) {
        holder.populateRow(getItem(position));
    }


    @Override
    public int getItemCount() {
        return tvshowArrayList.size() ;
    }

    private Tvshow getItem(int position) {
        return tvshowArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tvshowArrayList.get(position).getId();
    }

    public void updateList(List<Tvshow> newlist) {
        // Set new updated list
        tvshowArrayList.clear();
        tvshowArrayList.addAll(newlist);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView title;
        private final TextView date;
        private final TextView status;
        private final ImageView icon;

        //initialize the variables
        public ViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.tvshow_title_text);
            date = (TextView) view.findViewById(R.id.tvshow_date_text);
            status = (TextView) view.findViewById(R.id.tvshow_status_text);
            icon = (ImageView) view.findViewById(R.id.tvshow_icon_imageView);

            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, DetailsActivity.class);
            // Get the correct game based on which listitem got clicked, and put it as parameter in the intent
            Tvshow tvshow = getItem(getAdapterPosition());
            Log.d("ITEM", Integer.toString(getAdapterPosition()));
            intent.putExtra("selectedTvshow", tvshow);
            // Open GameDetailsActivity
            context.startActivity(intent);
        }


        public void populateRow(Tvshow tvshow) {
            title.setText(tvshow.getTitle());
            date.setText(tvshow.getDescription());
            status.setText("Status: Watching");
            Glide.with(itemView.getContext()).load(tvshow.getImgPath()).into(icon);

        }
    }
}
