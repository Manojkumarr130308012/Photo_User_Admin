package com.rajuuu.photo_user_admin.User.Wishlist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rajuuu.photo_user_admin.Config.Constant;
import com.rajuuu.photo_user_admin.R;
import com.rajuuu.photo_user_admin.User.Category.ViewSingleCatProductModel;
import com.rajuuu.photo_user_admin.User.Home.WeddingAdapter;
import com.rajuuu.photo_user_admin.User.ViewProduct.ViewProductDetailsActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.MyViewHolder> {
    private List<ViewSingleCatProductModel> moviesList;
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_title,locationTxt,priceTxt,ratbarTxt;
        ImageView imageView,img_wishYes,arrowImg;
        String usr_id;

        MyViewHolder(View view) {
            super(view);
            txt_title = view.findViewById(R.id.titleTxt);
            arrowImg = view.findViewById(R.id.arrowImg);
            locationTxt = view.findViewById(R.id.locationTxt);
            priceTxt = view.findViewById(R.id.priceTxt);
            imageView = view.findViewById(R.id.list_image);
            ratbarTxt = view.findViewById(R.id.ratbarTxt);
            img_wishYes = view.findViewById(R.id.img_wishYes);
        }
    }
    public WishlistAdapter(List<ViewSingleCatProductModel> moviesList) {
        this.moviesList = moviesList;
    }
    @NonNull
    @Override
    public WishlistAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wish_list_layout, parent, false);
        return new WishlistAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(WishlistAdapter.MyViewHolder holder, final int position) {

        final Context context = holder.imageView.getContext();

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor mEditor = mPreferences.edit();
        holder.usr_id  = mPreferences.getString("user_id", "");

        final ViewSingleCatProductModel product = moviesList.get(position);

        Picasso.with(context).load(product.getVendor_image())
                .centerCrop()
                .fit()
                .noFade().placeholder(R.drawable.ezgifresize).into(holder.imageView);

        holder.txt_title.setText(product.getVendor_cname());
        holder.locationTxt.setText(product.getVendor_locationname());
        holder.ratbarTxt.setText("  "+product.getVendor_rating()+"  ");
        holder.priceTxt.setText("₹ "+product.getVendor_amount()+"/Per Day");

        holder.img_wishYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = ProgressDialog.show(context,
                        "Please wait",
                        "Loading...");


                String defaultLink = Constant.API_REMOVE_WISHLIST;


                RequestQueue requestQueue;
                requestQueue = Volley.newRequestQueue(context);
                StringRequest strReq = new StringRequest(Request.Method.POST,
                        defaultLink, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
//                Log.d(TAG, "Register Response: " + response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            JSONObject obj = jObj.getJSONObject("reg");
                            String sts = obj.getString("sts");
                            if (sts.equals("Success")) {
                                notifyDataSetChanged();
                                moviesList.remove(position);
                                if (moviesList.size() == 0) {
                                    WishlistActivity.getInstance().emptyShow();
                                }

                            } else {
                                Toast.makeText(context, "Please try again!", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
//                    toast("Json error: " + e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
//                toast("Unknown Error occurred");
//                            progressDialog.hide();
                        progressDialog.dismiss();
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() {
                        // Posting parameters to login url
                        Map<String, String> params = new HashMap<>();
                        params.put("uid", ""+holder.usr_id);
                        params.put("vid", ""+moviesList.get(position).getVendor_id());
                        return params;
                    }

                };
                requestQueue.add(strReq);
            }
        });

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

        holder.arrowImg.setOnClickListener(new View.OnClickListener() {
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