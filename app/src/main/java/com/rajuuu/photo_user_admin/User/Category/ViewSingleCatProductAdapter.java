package com.rajuuu.photo_user_admin.User.Category;

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

import com.rajuuu.photo_user_admin.R;
import com.rajuuu.photo_user_admin.User.ViewProduct.ViewProductDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewSingleCatProductAdapter extends ArrayAdapter<ViewSingleCatProductModel> {

    ArrayList<ViewSingleCatProductModel> data;
    Context context;
    int resource;

    public ViewSingleCatProductAdapter(Context context, int resource, ArrayList<ViewSingleCatProductModel> data) {
        super(context, resource, data);
        this.data = data;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // HashMap<String, String> resultp = new HashMap<String, String>();

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.view_single_cat_list_layout, null, true);

        }
        final ViewSingleCatProductModel product = getItem(position);
//
        ImageView list_image = (ImageView) convertView.findViewById(R.id.list_image);
        Picasso.with(getContext()).load(product.getVendor_image())
                .centerCrop()
                .fit()
                .noFade().placeholder(R.drawable.ezgifresize).into(list_image);
//
        TextView titleTxt = (TextView) convertView.findViewById(R.id.titleTxt);
        titleTxt.setText(product.getVendor_cname());
//
        TextView locationTxt = (TextView) convertView.findViewById(R.id.locationTxt);
        locationTxt.setText(product.getVendor_locationname());
//
        TextView priceTxt = (TextView) convertView.findViewById(R.id.priceTxt);
        priceTxt.setText("₹ "+product.getVendor_amount()+" / Per Day");
//
        TextView ratbarTxt = (TextView) convertView.findViewById(R.id.ratbarTxt);
        ratbarTxt.setText(product.getVendor_rating());


        TextView ratTxt = (TextView) convertView.findViewById(R.id.ratTxt);
        ratTxt.setText(" ("+product.getVendor_rating()+")");

        RatingBar ratingStar = (RatingBar) convertView.findViewById(R.id.ratingStar);
        ratingStar.setRating(Float.parseFloat(product.getVendor_rating()));

        convertView.setOnClickListener(new View.OnClickListener() {
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


        return convertView;

    }

}
