package com.rajuuu.photo_user_admin.User.Home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rajuuu.photo_user_admin.R;
import com.rajuuu.photo_user_admin.User.Category.ViewSingleCatProductActivity;
import com.rajuuu.photo_user_admin.User.Category.ViewSingleCatProductModel;
import com.rajuuu.photo_user_admin.User.ViewProduct.ViewProductDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WeddingAdapter extends RecyclerView.Adapter<WeddingAdapter.MyViewHolder> {
    private List<ViewSingleCatProductModel> moviesList;
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_title,locationTxt,priceTxt,ratTxt;
        ImageView imageView;
        RatingBar ratingStar;

        MyViewHolder(View view) {
            super(view);
            txt_title = view.findViewById(R.id.txt_title);
            locationTxt = view.findViewById(R.id.locationTxt);
            priceTxt = view.findViewById(R.id.priceTxt);
            imageView = view.findViewById(R.id.imageView);
            ratingStar = view.findViewById(R.id.ratingStar);
            ratTxt = view.findViewById(R.id.ratTxt);
        }
    }
    public WeddingAdapter(List<ViewSingleCatProductModel> moviesList) {
        this.moviesList = moviesList;
    }
    @NonNull
    @Override
    public WeddingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wedding_list, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(WeddingAdapter.MyViewHolder holder, final int position) {

        final Context context = holder.imageView.getContext();

        final ViewSingleCatProductModel product = moviesList.get(position);

        Picasso.with(context).load(product.getVendor_image())
                .centerCrop()
                .fit()
                .noFade().placeholder(R.drawable.ezgifresize).into(holder.imageView);

        holder.txt_title.setText(product.getVendor_cname());
        holder.ratingStar.setRating(Float.parseFloat(product.getVendor_rating()));
        holder.ratTxt.setText(" ("+product.getVendor_rating()+")");
        holder.locationTxt.setText(product.getVendor_locationname());
        holder.priceTxt.setText("₹ "+product.getVendor_amount()+" / Per Day");

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context.getApplicationContext(), ViewProductDetailsActivity.class);

                i.putExtra("vendor_id",product.getVendor_id() );
                i.putExtra("vendor_desc",product.getVendor_desc() );
                i.putExtra("vendor_image",product.getVendor_image() );
                i.putExtra("vendor_category",product.getVendor_categoryname() );
                i.putExtra("vendor_cname",product.getVendor_cname() );
                i.putExtra("vendor_mobile",product.getVendor_mobile() );
                i.putExtra("vendor_whatsapp",product.getVendor_whatsapp() );
                i.putExtra("vendor_service",product.getVendor_service() );
                i.putExtra("vendor_liken",product.getVendor_liken() );
                i.putExtra("vendor_amount",product.getVendor_amount() );
                i.putExtra("vendor_rating",product.getVendor_rating() );
                i.putExtra("vendor_like",product.getVendor_like() );
                i.putExtra("vendor_wishlist",product.getVendor_wishlist() );
                i.putExtra("vendor_locationname",product.getVendor_locationname() );
                i.putExtra("vendor_achive",product.getVendor_ach() );
                i.putExtra("vendor_expr",product.getVendor_exp() );

                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });

    }
    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}