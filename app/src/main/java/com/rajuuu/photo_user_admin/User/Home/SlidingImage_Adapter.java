package com.rajuuu.photo_user_admin.User.Home;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.rajuuu.photo_user_admin.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SlidingImage_Adapter extends PagerAdapter {


    private List<BannerModel> IMAGES;
    private LayoutInflater inflater;
    private Context context;
    private Activity activity;


    public SlidingImage_Adapter(Context context, ArrayList<BannerModel> IMAGES) {
        this.context = context;
        this.IMAGES=IMAGES;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);

        Picasso.with(context).load(IMAGES.get(position).getImg()).into(imageView);

//        Toast.makeText(context, ""+IMAGES.get(position).getLink(), Toast.LENGTH_SHORT).show();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, ""+IMAGES.get(position).getLink(), Toast.LENGTH_SHORT).show();
            }
        });

        view.addView(imageLayout, 0);


        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}