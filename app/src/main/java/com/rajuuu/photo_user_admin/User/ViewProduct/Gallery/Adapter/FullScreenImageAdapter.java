package com.rajuuu.photo_user_admin.User.ViewProduct.Gallery.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.rajuuu.photo_user_admin.R;
import com.rajuuu.photo_user_admin.User.ViewProduct.Gallery.FullScreenActivity;
import com.rajuuu.photo_user_admin.User.ViewProduct.Gallery.Model.GImagesModel;
import com.rajuuu.photo_user_admin.User.ViewProduct.Gallery.TouchImageView;

import java.util.ArrayList;
import java.util.List;

public class FullScreenImageAdapter extends PagerAdapter {
    private List<GImagesModel> _imagePaths;
    private Activity _activity;
    private LayoutInflater inflater;

    public FullScreenImageAdapter(Activity activity,List<GImagesModel> moviesList) {
        this._activity = activity;
        this._imagePaths = moviesList;
    }

    @Override
    public int getCount() {
        return this._imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TouchImageView imgDisplay;
        Button btnClose;

        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.activity_full_image, container,
                false);

        final GImagesModel movie = _imagePaths.get(position);

//        imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
        btnClose = (Button) viewLayout.findViewById(R.id.btnClose);

//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        Bitmap bitmap = BitmapFactory.decodeFile(movie.getImage_src(), options);
//        imgDisplay.setImageBitmap(bitmap);

        ImageView imageView = viewLayout.findViewById(R.id.myImageView);
        Glide.with(_activity).load(movie.getImage_src()).centerCrop().thumbnail(Glide.with(_activity).load(R.drawable.ezgifresize)).into(imageView);

        // close button click event
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _activity.finish();
            }
        });

        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

}