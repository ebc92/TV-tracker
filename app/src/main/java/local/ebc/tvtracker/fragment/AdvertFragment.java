package local.ebc.tvtracker.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import local.ebc.tvtracker.R;

/**
 * Created by ebc on 07.11.2016.
 */

public class AdvertFragment extends Fragment {

    private ImageView adView;

    public AdvertFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_advert, container, false);
        adView = (ImageView) v.findViewById(R.id.advertisement_banner);
        //adView.setImageResource(R.drawable.advertisement);
        Glide.with(getActivity()).load(R.mipmap.advertisement).into(adView);

        return v;
    }
}
