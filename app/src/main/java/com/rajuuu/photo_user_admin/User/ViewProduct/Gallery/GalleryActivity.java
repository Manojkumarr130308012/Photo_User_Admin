package com.rajuuu.photo_user_admin.User.ViewProduct.Gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rajuuu.photo_user_admin.Config.Constant;
import com.rajuuu.photo_user_admin.R;
import com.rajuuu.photo_user_admin.User.Category.CategoryAdapter;
import com.rajuuu.photo_user_admin.User.Category.CategoryModel;
import com.rajuuu.photo_user_admin.User.ViewProduct.Gallery.Adapter.CustomGalleryAdapter;
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

public class GalleryActivity extends AppCompatActivity {

    RelativeLayout lin1;
    RelativeLayout lin2;
    TextView Accesriesinfobtn,digitalsbtn;
    List<YoutubeVideo> youtubeVideos;
    // array of images
//    String[] images=new String[0];
//    String[] imagesid=new String[0];
    String vidd;

    RecyclerView recyclerViewFeed;
    YoutubeRecyclerAdapter mRecyclerAdapter;

    RecyclerView recyclerViewImage;
    private List<GImagesModel> categoryList = new ArrayList<>();
    private CustomGalleryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        lin1 = findViewById(R.id.lin1);
        lin2 = findViewById(R.id.lin2);
        recyclerViewFeed = findViewById(R.id.recyclerView);
        recyclerViewImage = findViewById(R.id.recyclerViewImage);
        Accesriesinfobtn =findViewById(R.id.personalinfobtn);
        digitalsbtn =findViewById(R.id.Bussinessbtn);

        Intent i = getIntent();
        vidd = i.getStringExtra("vidd");



        Accesriesinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lin1.setVisibility(View.VISIBLE);
//                Accesriesinfobtn.setVisibility(View.VISIBLE);

                Accesriesinfobtn.setTextColor(getResources().getColor(R.color.colorAccent));
                digitalsbtn.setTextColor(getResources().getColor(R.color.md_grey_500));


            }
        });

        digitalsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lin2.setVisibility(View.VISIBLE);
                lin1.setVisibility(View.GONE);
//                Accesriesinfobtn.setVisibility(View.VISIBLE);

                Accesriesinfobtn.setTextColor(getResources().getColor(R.color.md_grey_500));
                digitalsbtn.setTextColor(getResources().getColor(R.color.colorAccent));

            }
        });




        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute(Constant.API_VENDOR_VIDEOS+vidd);
                new ReadJSON1().execute(Constant.API_VENDOR_IMAGES+vidd);
            }
        });

    }


    class ReadJSON extends AsyncTask<String, Integer, String> {
        ArrayList<YoutubeVideo> videoArrayList=new ArrayList<>();
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
                JSONArray jsonArray =  jsonObject.getJSONArray("videos");

                for(int i =0;i<jsonArray.length(); i++){
                    JSONObject productObject = jsonArray.getJSONObject(i);

                    YoutubeVideo video1 = new YoutubeVideo();
                    video1.setId(""+productObject.getString("video_id"));
                    String ytrl=""+productObject.getString("video_src");
                    String regExp = "/.*(?:youtu.be\\/|v\\/|u/\\w/|embed\\/|watch\\?.*&?v=)";
                    Pattern compiledPattern = Pattern.compile(regExp);
                    Matcher matcher = compiledPattern.matcher(ytrl);
                    if(matcher.find()){
                        int start = matcher.end();
                        System.out.println("ID : " + ytrl.substring(start, start+11));

                        video1.setVideoId(""+ytrl.substring(start, start+11));
                        videoArrayList.add(video1);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
//                custPrograssbar.closePrograssBar();
            }

            if (getApplicationContext()!=null) {
                //set layout manager and adapter for "GridView"
                if (videoArrayList.size() != 0){
                    mRecyclerAdapter = new YoutubeRecyclerAdapter(videoArrayList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(GalleryActivity.this);
                    recyclerViewFeed.setLayoutManager(mLayoutManager);
                    recyclerViewFeed.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewFeed.setAdapter(mRecyclerAdapter);
                }


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

    class ReadJSON1 extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            return readURL2(params[0]);
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray =  jsonObject.getJSONArray("images");

                for(int i =0;i<jsonArray.length(); i++){
                    JSONObject productObject = jsonArray.getJSONObject(i);

                    categoryList.add(new GImagesModel(
                            productObject.getString("image_id"),
                            productObject.getString("image_src")
                    ));

                }
            } catch (JSONException e) {
                e.printStackTrace();
//                custPrograssbar.closePrograssBar();
            }

            if (getApplicationContext()!=null) {

                GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
                recyclerViewImage.setLayoutManager(layoutManager);
                mAdapter = new CustomGalleryAdapter(GalleryActivity.this,categoryList);
                recyclerViewImage.setAdapter(mAdapter);

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


}