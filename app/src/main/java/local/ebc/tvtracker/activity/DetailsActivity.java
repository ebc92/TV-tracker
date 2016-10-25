package local.ebc.tvtracker.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import local.ebc.tvtracker.database.DataSource;
import local.ebc.tvtracker.model.Tvshow;
import local.ebc.tvtracker.R;

public class DetailsActivity extends AppCompatActivity {

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
}