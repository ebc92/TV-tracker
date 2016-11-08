package local.ebc.tvtracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;
import local.ebc.tvtracker.activity.TvshowDetailsActivity;
import local.ebc.tvtracker.model.Tvshow;
import local.ebc.tvtracker.R;

/**
 * Created by ebc on 19.10.2016.
 */

public class TvshowItemAdapter extends RecyclerView.Adapter<TvshowItemAdapter.ViewHolder> {
    private final List<Tvshow> tvshowArrayList;
    private final Context context;

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

    //Get the TV show count.
    @Override
    public int getItemCount() {
        return tvshowArrayList.size() ;
    }

    //Get the TV show using the position
    private Tvshow getItem(int position) {
        return tvshowArrayList.get(position);
    }

    //Get the TV show ID using the position.
    @Override
    public long getItemId(int position) {
        return tvshowArrayList.get(position).getId();
    }

    public void updateList(List<Tvshow> newlist) {
        // Set new updated list
        tvshowArrayList.clear();
        tvshowArrayList.addAll(newlist);
    }


    /* The ViewHolder is used so that the findViewById results
    can be cached. This allows for resource friendly recycling in
    the recycler view when it is scrolled.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView title;
        private final TextView date;
        private final TextView status;
        private final ImageView icon;
        private final RatingBar rating;

        //initialize the variables
        public ViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.tvshow_title_text);
            date = (TextView) view.findViewById(R.id.tvshow_date_text);
            status = (TextView) view.findViewById(R.id.tvshow_status_text);
            icon = (ImageView) view.findViewById(R.id.tvshow_icon_imageView);
            rating = (RatingBar) view.findViewById(R.id.tvshow_rating_bar);

            view.setOnClickListener(this);
        }


        /* If a recycler view item is clicked, the position is retrieved.
        The details activity is started and the position is sent to the
        activity with an intent.
         */
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, TvshowDetailsActivity.class);
            Tvshow tvshow = getItem(getAdapterPosition());
            intent.putExtra("selectedTvshow", tvshow);
            context.startActivity(intent);
        }


        /* When the method recieves a TV show object,
        it takes the data from the object and puts it in
        the views.
         */
        public void populateRow(Tvshow tvshow) {
            title.setText(tvshow.getTitle());
            String dateText = "Date added: " + tvshow.getDateadded();
            date.setText(dateText);
            String statusText = "Status: " + tvshow.getStatus();
            status.setText(statusText);
            float ratingFloat = (float)tvshow.getRating();
            rating.setRating(ratingFloat);
            Glide.with(itemView.getContext()).load(tvshow.getImgPath()).into(icon);

        }
    }
}
