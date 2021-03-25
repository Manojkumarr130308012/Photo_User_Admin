package com.rajuuu.photo_user_admin.User.ViewProduct;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.rajuuu.photo_user_admin.Config.Constant;
import com.rajuuu.photo_user_admin.R;
import com.rajuuu.photo_user_admin.User.UserMainActivity;
import com.rajuuu.photo_user_admin.User.ViewProduct.Gallery.GalleryActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ViewProductDetailsActivity extends AppCompatActivity {

    Toolbar toolbar;
    String vendor_id,vendor_desc,vendor_image,vendor_category,vendor_cname,vendor_mobile,
            vendor_whatsapp,vendor_service,vendor_liken,vendor_amount,vendor_rating,
            vendor_like,vendor_wishlist,vendor_locationname,usr_id,vendor_achive,vendor_expr;
    ImageView imageDisplay,whatsBtn,callBtn,img_wishNo,img_wishYes,img_likeNo,img_likeYes;
    TextView nameTxt,priceTxt,ratTxt,descTxt,totLike,locationTxt,experTxt,achiTxt;
    Button galleryBtn,enquiryBtn;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    LinearLayout mainLay,serviceLay;
    ChipGroup chipGroup;
    RatingBar ratingStar;

    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product_details);

        toolbar = findViewById(R.id.toolbar);
        chipGroup = findViewById(R.id.chipGroup);
        mainLay = findViewById(R.id.mainLay);
        img_wishNo = findViewById(R.id.img_wishNo);
        img_likeNo = findViewById(R.id.img_likeNo);
        img_likeYes = findViewById(R.id.img_likeYes);
        img_wishYes = findViewById(R.id.img_wishYes);
        imageDisplay = findViewById(R.id.imageDisplay);
        nameTxt = findViewById(R.id.nameTxt);
        callBtn = findViewById(R.id.callBtn);
        whatsBtn = findViewById(R.id.whatsBtn);
        galleryBtn = findViewById(R.id.galleryBtn);
        priceTxt = findViewById(R.id.priceTxt);
        ratTxt = findViewById(R.id.ratTxt);
        descTxt = findViewById(R.id.descTxt);
        enquiryBtn = findViewById(R.id.enquiryBtn);
        totLike = findViewById(R.id.totLike);
        locationTxt = findViewById(R.id.locationTxt);
        serviceLay = findViewById(R.id.serviceLay);
        ratingStar = findViewById(R.id.ratingStar);
        experTxt = findViewById(R.id.experTxt);
        achiTxt = findViewById(R.id.achiTxt);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
        usr_id  = mPreferences.getString("user_id", "");

        Intent intent = getIntent();
        vendor_id = intent.getStringExtra("vendor_id");
        vendor_desc = intent.getStringExtra("vendor_desc");
        vendor_image = intent.getStringExtra("vendor_image");
        vendor_category = intent.getStringExtra("vendor_category");
        vendor_cname = intent.getStringExtra("vendor_cname");
        vendor_mobile = intent.getStringExtra("vendor_mobile");
        vendor_whatsapp = intent.getStringExtra("vendor_whatsapp");
        vendor_service = intent.getStringExtra("vendor_service");
        vendor_liken = intent.getStringExtra("vendor_liken");
        vendor_amount = intent.getStringExtra("vendor_amount");
        vendor_rating = intent.getStringExtra("vendor_rating");
        vendor_like = intent.getStringExtra("vendor_like");
        vendor_wishlist = intent.getStringExtra("vendor_wishlist");
        vendor_locationname = intent.getStringExtra("vendor_locationname");
        vendor_achive = intent.getStringExtra("vendor_achive");
        vendor_expr = intent.getStringExtra("vendor_expr");


        if (vendor_service.isEmpty()){
            serviceLay.setVisibility(View.GONE);
        } else {
            serviceLay.setVisibility(View.VISIBLE);
        }

        String[] arrSplit = vendor_service.split(", ");
        for (int i=0; i < arrSplit.length; i++)
        {
            System.out.println(arrSplit[i]);

            try {
                LayoutInflater inflater = LayoutInflater.from(ViewProductDetailsActivity.this);

                // Create a Chip from Layout.
                Chip newChip = (Chip) inflater.inflate(R.layout.layout_chip_entry, chipGroup, false);
                newChip.setText(arrSplit[i]);


                chipGroup.addView(newChip);

              /*  // Set Listener for the Chip:
                newChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        handleChipCheckChanged((Chip) buttonView, isChecked);
                    }
                });

                newChip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleChipCloseIconClicked((Chip) v);
                    }
                });

*/
//                                    this.editTextKeyword.setText("");

            } catch (Exception e) {
                e.printStackTrace();
//                                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }



//        Toast.makeText(this, ""+vendor_liken, Toast.LENGTH_SHORT).show();

        whatsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+91"+vendor_whatsapp));
                startActivity(intent);
            }
        });
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+vendor_mobile));
                startActivity(intent);
            }
        });
        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(ViewProductDetailsActivity.this, ""+vendor_id, Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(ViewProductDetailsActivity.this, GalleryActivity.class);
                intent1.putExtra("vidd",vendor_id);
                startActivity(intent1);
            }
        });

        enquiryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewProductDetailsActivity.this);
                alertDialogBuilder.setMessage("Are you sure, You wanted to make Enquiry!");
                        alertDialogBuilder.setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        sendData();
                                    }
                                });
                alertDialogBuilder.setNegativeButton("N0", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
                        alertDialog.dismiss();
                    }
                });


                alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        if (vendor_wishlist.equals("1")) {
            img_wishNo.setVisibility(View.GONE);
            img_wishYes.setVisibility(View.VISIBLE);
        } else {
            img_wishYes.setVisibility(View.GONE);
            img_wishNo.setVisibility(View.VISIBLE);
        }

        if (vendor_like.equals("1")) {
            img_likeNo.setVisibility(View.GONE);
            img_likeYes.setVisibility(View.VISIBLE);
        } else {
            img_likeYes.setVisibility(View.GONE);
            img_likeNo.setVisibility(View.VISIBLE);
        }

        getSupportActionBar().setTitle(vendor_category+" Details");
//        Picasso.with(getApplicationContext()).load(vendor_image)
//                .centerCrop()
//                .fit()
//                .noFade().placeholder(R.drawable.ezgifresize).into(imageDisplay);
        Glide.with(getApplicationContext()).load(vendor_image).centerCrop().thumbnail(Glide.with(getApplicationContext()).load(R.drawable.ezgifresize)).into(imageDisplay);
        nameTxt.setText(vendor_cname);
        priceTxt.setText("₹ "+vendor_amount+" / Per Day");
        ratTxt.setText(" ("+vendor_rating+")");
        ratingStar.setRating(Float.parseFloat(vendor_rating));
        descTxt.setText(vendor_desc);
        totLike.setText(vendor_liken);
        experTxt.setText(vendor_expr);
        achiTxt.setText(vendor_achive);
        locationTxt.setText(vendor_locationname);

        img_likeNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWriteUserReviewDialog();
            }
        });

        img_wishYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    final ProgressDialog progressDialog = ProgressDialog.show(ViewProductDetailsActivity.this,
                            "Please wait",
                            "Loading...");


                    String defaultLink = Constant.API_REMOVE_WISHLIST;


                    RequestQueue requestQueue;
                    requestQueue = Volley.newRequestQueue(ViewProductDetailsActivity.this);
                    StringRequest strReq = new StringRequest(Request.Method.POST,
                            defaultLink, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
//                Log.d(TAG, "Register Response: " + response.toString());
                            try {
                                JSONObject jObj = new JSONObject(response);
                                JSONObject obj = jObj.getJSONObject("reg");
                                String msg = obj.getString("msg");
                                String sts = obj.getString("sts");
                                if (sts.equals("Success")) {

                                    img_wishYes.setVisibility(View.GONE);
                                    img_wishNo.setVisibility(View.VISIBLE);

                                } else {
                                    Toast.makeText(ViewProductDetailsActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
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
                            params.put("uid", ""+usr_id);
                            params.put("vid", ""+vendor_id);
                            return params;
                        }

                    };
                    requestQueue.add(strReq);
            }
        });

        img_wishNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    final ProgressDialog progressDialog = ProgressDialog.show(ViewProductDetailsActivity.this,
                            "Please wait",
                            "Loading...");


                    String defaultLink = Constant.API_ADD_WISHLIST;


                    RequestQueue requestQueue;
                    requestQueue = Volley.newRequestQueue(ViewProductDetailsActivity.this);
                    StringRequest strReq = new StringRequest(Request.Method.POST,
                            defaultLink, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
//                Log.d(TAG, "Register Response: " + response.toString());
                            try {
                                JSONObject jObj = new JSONObject(response);
                                JSONObject obj = jObj.getJSONObject("reg");
                                String msggg = obj.getString("msg");
                                String sts = obj.getString("sts");
                                if (sts.equals("Success")) {

                                    img_wishNo.setVisibility(View.GONE);
                                    img_wishYes.setVisibility(View.VISIBLE);

                                } else {
                                    Toast.makeText(ViewProductDetailsActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
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
                            params.put("uid", ""+usr_id);
                            params.put("vid", ""+vendor_id);
                            return params;
                        }

                    };
                    requestQueue.add(strReq);

            }
        });
    }

    private void showWriteUserReviewDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //then we will inflate the custom alert dialog xml that we created
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.user_review, viewGroup, false);


        Button cancelButton = dialogView.findViewById(R.id.cancelButton);
        Button submitButton = dialogView.findViewById(R.id.submitButton);
        final RatingBar ratingBar = dialogView.findViewById(R.id.ratingBarDialog);


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar arg0, float rateValue, boolean arg2) {
                // TODO Auto-generated method stub
                Log.d("Rating", "your selected value is :"+rateValue);
            }
        });

        //Now we need an AlertDialog.Builder object
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = ProgressDialog.show(ViewProductDetailsActivity.this,
                        "Please wait",
                        "Loading...");

                Float ratinggg = ratingBar.getRating();
                int ratt = Math.round(ratinggg);
//                Toast.makeText(ViewProductDetailsActivity.this, ""+ratinggg+"  "+ratt, Toast.LENGTH_SHORT).show();
                String defaultLink = Constant.API_ADD_LIKE;


                RequestQueue requestQueue;
                requestQueue = Volley.newRequestQueue(ViewProductDetailsActivity.this);
                StringRequest strReq = new StringRequest(Request.Method.POST,
                        defaultLink, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
//                Log.d(TAG, "Register Response: " + response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            JSONObject obj = jObj.getJSONObject("reg");
                            String msg = obj.getString("msg");
                            String sts = obj.getString("sts");
                            if (sts.equals("Success")) {

                                img_likeNo.setVisibility(View.GONE);
                                img_likeYes.setVisibility(View.VISIBLE);
                                int totLikee = Integer.parseInt(vendor_like+1);
                                totLike.setText(""+totLikee);

                            } else {
                                Toast.makeText(ViewProductDetailsActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                            alertDialog.dismiss();

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
                        params.put("uid", ""+usr_id);
                        params.put("vid", ""+vendor_id);
                        params.put("rating", ""+ratinggg);
                        return params;
                    }

                };
                requestQueue.add(strReq);
            }
        });
    }

    private void sendData() {

        final ProgressDialog progressDialog = ProgressDialog.show(ViewProductDetailsActivity.this,
                "Please wait",
                "Loading...");

        RequestQueue requestQueue= Volley.newRequestQueue(ViewProductDetailsActivity.this);
        String url = Constant.API_ENQUIERY;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //let's parse json data
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject obj = jsonObject.getJSONObject("reg");
                    String msg = obj.getString("msg");
                    String sts = obj.getString("sts");

                    if (sts.equals("Success")) {

                        Snackbar snackbar = Snackbar.make(mainLay, "Your Enquiery Success!.", Snackbar.LENGTH_LONG);
                        View snackBarView = snackbar.getView();
                        snackBarView.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.purple_500));
                        snackbar.show();
//                        Toast.makeText(ViewProductDetailsActivity.this, "Enquiery Success.", Toast.LENGTH_SHORT).show();
//                        finish();
//                        Intent intent = new Intent(ViewProductDetailsActivity.this, UserMainActivity.class);
//                        startActivity(intent);
//                        progressDialog.dismiss();

                    } else {
                        Toast.makeText(ViewProductDetailsActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                    }

                    progressDialog.dismiss();
                }
                catch (Exception e){
                    e.printStackTrace();
                    progressDialog.dismiss();
//                    Toast.makeText(ProfileActivity.this, "POST DATA : unable to Parse Json", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ViewProductDetailsActivity.this, "Post Data : Response Failed", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("uid", ""+usr_id);
                params.put("vid", ""+vendor_id);
                return params;
            }

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
//                params.put("Content-Type","application/json;charset=utf-8");
                params.put("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
//                params.put("Authorization","Bearer "+sToken);
//                params.put("X-Requested-With", "XMLHttpRequest");
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                onBackPressed();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}