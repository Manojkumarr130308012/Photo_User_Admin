package com.rajuuu.photo_user_admin.User.Wishlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.rajuuu.photo_user_admin.Config.Constant;
import com.rajuuu.photo_user_admin.R;
import com.rajuuu.photo_user_admin.User.Category.AllCategoryActivity;
import com.rajuuu.photo_user_admin.User.Category.CategoryAdapter;
import com.rajuuu.photo_user_admin.User.Category.CategoryModel;
import com.rajuuu.photo_user_admin.User.Category.ViewSingleCatProductModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class WishlistActivity extends AppCompatActivity {

    RecyclerView recyclerViewCat;
    private List<ViewSingleCatProductModel> categoryList = new ArrayList<>();
    private WishlistAdapter mAdapter;
    LinearLayout lvl_notfound,listLay;

    String usr_id;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    public static WishlistActivity wishFragment = null;


    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        recyclerViewCat = findViewById(R.id.recyclerViewCat);
        toolbar = findViewById(R.id.toolbar);
        lvl_notfound = findViewById(R.id.lvl_notfound);
        listLay = findViewById(R.id.listLay);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Wishlist");

        wishFragment = this;

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
        usr_id  = mPreferences.getString("user_id", "");

        recyclerViewCat.setItemAnimator(new DefaultItemAnimator());


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new WishlistActivity.ReadJSON2().execute(Constant.API_VIEW_WISHLIST+usr_id);

            }
        });
    }

    public static WishlistActivity getInstance() {
        return wishFragment;
    }

    public void emptyShow() {
//        Toast.makeText(getActivity(), "111", Toast.LENGTH_SHORT).show();
        lvl_notfound.setVisibility(View.VISIBLE);
        listLay.setVisibility(View.GONE);
    }

    class ReadJSON2 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL2(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray =  jsonObject.getJSONArray("vendor");

                for(int i =0;i<jsonArray.length(); i++){
                    JSONObject productObject = jsonArray.getJSONObject(i);

                    categoryList.add(new ViewSingleCatProductModel(
                            productObject.getString("vendor_id"),
                            productObject.getString("vendor_desc"),
                            productObject.getString("vendor_image"),
                            productObject.getString("vendor_category"),
                            productObject.getString("vendor_cname"),
                            productObject.getString("vendor_mobile"),
                            productObject.getString("vendor_whatsapp"),
                            productObject.getString("vendor_service"),
                            productObject.getString("vendor_liken"),
                            productObject.getString("vendor_amount"),
                            productObject.getString("vendor_rating"),
                            productObject.getString("vendor_like"),
                            productObject.getString("vendor_wishlist"),
                            productObject.getString("vendor_categoryname"),
                            productObject.getString("vendor_locationname"),
                            productObject.getString("vendor_ach"),
                            productObject.getString("vendor_exp")
                    ));
                }

            } catch (JSONException e) {
                e.printStackTrace();
                lvl_notfound.setVisibility(View.VISIBLE);
                listLay.setVisibility(View.GONE);
            }

            if (getApplicationContext()!=null) {

                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, 1);
                recyclerViewCat.setLayoutManager(layoutManager);
                mAdapter = new WishlistAdapter(categoryList);
                recyclerViewCat.setAdapter(mAdapter);

            }

        }
    }

    private static String readURL2(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);
            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();
            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
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