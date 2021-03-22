package com.rajuuu.photo_user_admin.User.ViewProduct.Gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.rajuuu.photo_user_admin.Config.Constant;
import com.rajuuu.photo_user_admin.R;
import com.rajuuu.photo_user_admin.User.ViewProduct.Gallery.Adapter.CustomGalleryAdapter;
import com.rajuuu.photo_user_admin.User.ViewProduct.Gallery.Adapter.FullScreenImageAdapter;
import com.rajuuu.photo_user_admin.User.ViewProduct.Gallery.Adapter.ViewPagerAdapter;
import com.rajuuu.photo_user_admin.User.ViewProduct.Gallery.Adapter.YoutubeRecyclerAdapter;
import com.rajuuu.photo_user_admin.User.ViewProduct.Gallery.Model.GImagesModel;
import com.rajuuu.photo_user_admin.User.ViewProduct.Gallery.Model.YoutubeVideo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FullScreenActivity extends AppCompatActivity {

    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;
    int position;
    String[] images;
    private List<GImagesModel> categoryList = new ArrayList<>();

    ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

//        viewPager = (ViewPager) findViewById(R.id.pager);

        Intent i = getIntent();
        position = i.getIntExtra("position", 1000);
        Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
        mViewPager = (ViewPager)findViewById(R.id.viewPagerMain);
if (position != 1000){
    this.runOnUiThread(new Runnable() {
        @Override
        public void run() {
            new ReadJSON().execute(Constant.API_VENDOR_IMAGES+"41");
        }
    });
}



    }

    class ReadJSON extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray =  jsonObject.getJSONArray("images");
                images = new String[jsonArray.length()];
                for(int i =0;i<jsonArray.length(); i++){
                    JSONObject productObject = jsonArray.getJSONObject(i);

//                    categoryList.add(new GImagesModel(
//                            productObject.getString("image_id"),
//                            productObject.getString("image_src")
//                    ));

                    Log.e("dddddddddddd",""+productObject.getString("image_src"));
                    images[i]=productObject.getString("image_src");
                }
            } catch (JSONException e) {
                e.printStackTrace();
//                custPrograssbar.closePrograssBar();
            }

            if (getApplicationContext()!=null) {

//                adapter = new FullScreenImageAdapter(FullScreenActivity.this,categoryList);
//                viewPager.setAdapter(adapter);
//
//                // displaying selected image first
//                viewPager.setCurrentItem(position);
                ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(FullScreenActivity.this, images,position);

                // Adding the Adapter to the ViewPager
                mViewPager.setAdapter(mViewPagerAdapter);
            }

        }
    }
    private static String readURL(String theUrl) {
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
}