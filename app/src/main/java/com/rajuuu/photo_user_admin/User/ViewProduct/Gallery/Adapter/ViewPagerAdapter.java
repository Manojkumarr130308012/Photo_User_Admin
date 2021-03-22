package com.rajuuu.photo_user_admin.User.ViewProduct.Gallery.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.rajuuu.photo_user_admin.R;

import java.util.Objects;

public class ViewPagerAdapter extends PagerAdapter {

    // Context object
    Context context;

    // Array of images
    String[] images;

    // Layout Inflater
    LayoutInflater mLayoutInflater;

int postion;

int a=0;
    // Viewpager Constructor
    public ViewPagerAdapter(Context context, String[] images,int postion) {
        this.context = context;
        this.images = images;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.postion=postion;

    }

    @Override
    public int getCount() {
        // return the number of images
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container,  int position) {
        // inflating the item.xml
        View itemView = mLayoutInflater.inflate(R.layout.item, container, false);

        // referencing the image view from the item.xml file
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageViewMain);

        // setting the image in the imageView
//        imageView.setImageResource();
        images[0]=images[this.postion];
        for (int i=0;i<images.length;i++){
            for (int j=1;j<i+1;j++){
                images[i+1]=images[i];
            }

        }

        Glide.with(context).load(imageView).centerCrop().thumbnail(Glide.with(context).load(images[position])).into(imageView);

//



        // Adding the View
        Objects.requireNonNull(container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((LinearLayout) object);
    }
}