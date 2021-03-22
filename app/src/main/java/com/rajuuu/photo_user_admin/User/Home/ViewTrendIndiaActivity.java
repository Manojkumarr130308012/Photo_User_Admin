package com.rajuuu.photo_user_admin.User.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.ListView;

import com.rajuuu.photo_user_admin.Config.Constant;
import com.rajuuu.photo_user_admin.Config.CustPrograssbar;
import com.rajuuu.photo_user_admin.R;
import com.rajuuu.photo_user_admin.User.Category.ViewSingleCatProductActivity;
import com.rajuuu.photo_user_admin.User.Category.ViewSingleCatProductAdapter;
import com.rajuuu.photo_user_admin.User.Category.ViewSingleCatProductModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ViewTrendIndiaActivity extends AppCompatActivity {
    ArrayList<ViewSingleCatProductModel> arrayList;
    ListView lv;
    public static CustPrograssbar custPrograssbar;
    Toolbar toolbar;
    String cidd,namee,usr_id,city_id;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trend_india);

        toolbar = findViewById(R.id.toolbar);
        lv = (ListView) findViewById(R.id.list_view);

        Intent i = getIntent();
        cidd = i.getStringExtra("idd");
        namee = i.getStringExtra("namee");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(namee);

        arrayList = new ArrayList<>();
        custPrograssbar = new CustPrograssbar();

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
        usr_id  = mPreferences.getString("user_id", "");
        city_id  = mPreferences.getString("city_id", "");

//        Toast.makeText(ViewSingleCatProductActivity.this, "ewferw "+cidd, Toast.LENGTH_SHORT).show();

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                new ReadJSON2().execute(Constant.API_CATEGORY_PRODUCTS+usr_id+"&cid="+idd+"&lid="+city_id);
//
//            }
//        });
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

                    arrayList.add(new ViewSingleCatProductModel(
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
                custPrograssbar.closePrograssBar();
            }

            if (getApplicationContext()!=null) {
                //set layout manager and adapter for "GridView"
                custPrograssbar.closePrograssBar();
                ViewSingleCatProductAdapter adapter = new ViewSingleCatProductAdapter(
                        getApplicationContext(), R.layout.view_single_cat_list_layout, arrayList
                );
                lv.setAdapter(adapter);
                custPrograssbar.closePrograssBar();

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
    protected void onResume() {
        super.onResume();
        arrayList.clear();
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new ViewTrendIndiaActivity.ReadJSON2().execute(Constant.API_TREND_INDIA+usr_id);
//                        Toast.makeText(ViewSingleCatProductActivity.this, ""+cidd, Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
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