package com.rajuuu.photo_user_admin.User.Category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rajuuu.photo_user_admin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private List<CategoryModel> moviesList;
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_title;
        ImageView imageView;

        MyViewHolder(View view) {
            super(view);
            txt_title = view.findViewById(R.id.txt_title);
            imageView = view.findViewById(R.id.imageView);
        }
    }
    public CategoryAdapter(List<CategoryModel> moviesList) {
        this.moviesList = moviesList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final Context context = holder.imageView.getContext();

        final CategoryModel movie = moviesList.get(position);


//        if (movie.getImg().isEmpty()) {
//            holder.imageView.setImageResource(R.mipmap.ic_launcher);
//        } else{
//            Picasso.with(context).load(movie.getImg()).into(holder.imageView);
//        }
        Glide.with(context).load(movie.getImg()).centerCrop().thumbnail(Glide.with(context).load(R.drawable.ezgifresize)).into(holder.imageView);


        holder.txt_title.setText(movie.getName());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,ViewSingleCatProductActivity.class);
                intent.putExtra("idd",movie.getId());
                intent.putExtra("namee",movie.getName());
                context.startActivity(intent);

            }
        });

    }
    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
