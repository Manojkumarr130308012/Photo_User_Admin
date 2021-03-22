package com.rajuuu.photo_user_admin.User.ViewProduct.Gallery.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rajuuu.photo_user_admin.R;
import com.rajuuu.photo_user_admin.User.Category.CategoryAdapter;
import com.rajuuu.photo_user_admin.User.Category.CategoryModel;
import com.rajuuu.photo_user_admin.User.Category.ViewSingleCatProductActivity;
import com.rajuuu.photo_user_admin.User.ViewProduct.Gallery.FullScreenActivity;
import com.rajuuu.photo_user_admin.User.ViewProduct.Gallery.Model.GImagesModel;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CustomGalleryAdapter extends RecyclerView.Adapter<CustomGalleryAdapter.MyViewHolder> {
    private List<GImagesModel> moviesList;
    private Activity _activity;


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView myImageView;

        MyViewHolder(View view) {
            super(view);
            myImageView = view.findViewById(R.id.myImageView);
        }
    }
    public CustomGalleryAdapter(Activity activity,List<GImagesModel> moviesList) {
        this._activity = activity;
        this.moviesList = moviesList;
    }
    @NonNull
    @Override
    public CustomGalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_gridview, parent, false);
        return new CustomGalleryAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(CustomGalleryAdapter.MyViewHolder holder, final int position) {

        final Context context = holder.myImageView.getContext();

        final GImagesModel movie = moviesList.get(position);

        Glide.with(context).load(movie.getImage_src()).centerCrop().thumbnail(Glide.with(context).load(R.drawable.ezgifresize)).into(holder.myImageView);

//        holder.myImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                imageDialogFirst(movie.getImage_src(),context);
//                Intent i = new Intent(_activity, FullScreenActivity.class);
//                i.putExtra("position", _postion);
//                _activity.startActivity(i);
//            }
//        });
//
        holder.myImageView.setOnClickListener(new OnImageClickListener(position));

    }


    class OnImageClickListener implements View.OnClickListener {

        int _postion;

        // constructor
        public OnImageClickListener(int position) {
            this._postion = position;
        }

        @Override
        public void onClick(View v) {
            // on selecting grid view image
            // launch full screen activity
            Intent i = new Intent(_activity, FullScreenActivity.class);
            i.putExtra("position", _postion);
            _activity.startActivity(i);
        }

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    private void imageDialogFirst(String imggg,Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        View myView = LayoutInflater.from(context).inflate(R.layout.customview_image, null);

        ImageView imageView = myView.findViewById(R.id.myImageView);
        Button btnClose = myView.findViewById(R.id.btnClose);

        Glide.with(context).load(imggg).centerCrop().thumbnail(Glide.with(context).load(R.drawable.ezgifresize)).into(imageView);

        // close button click event
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _activity.finish();
            }
        });

        builder.setView(myView);
        AlertDialog alertDialog = builder.create();

        alertDialog.show();

    }
}


