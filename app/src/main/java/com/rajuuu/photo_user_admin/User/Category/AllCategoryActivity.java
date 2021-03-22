package com.rajuuu.photo_user_admin.User.Category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import com.rajuuu.photo_user_admin.Config.Constant;
import com.rajuuu.photo_user_admin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class AllCategoryActivity extends AppCompatActivity {

    //  Categort ///
    RecyclerView recyclerViewCat;
    private List<CategoryModel> categoryList = new ArrayList<>();
    private CategoryAdapter mAdapter;
    //  End Categort ///

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category);

        recyclerViewCat = findViewById(R.id.recyclerViewCat);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("All Category");

        recyclerViewCat.setItemAnimator(new DefaultItemAnimator());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AllCategoryActivity.ReadJSON2().execute(Constant.API_CATEGORY);

            }
        });
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
                JSONArray jsonArray =  jsonObject.getJSONArray("category");

                for(int i =0;i<jsonArray.length(); i++){
                    JSONObject productObject = jsonArray.getJSONObject(i);

                    categoryList.add(new CategoryModel(
                            productObject.getString("category_id"),
                            productObject.getString("category_name"),
                            productObject.getString("category_image")
                    ));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (getApplicationContext()!=null) {

                GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
                recyclerViewCat.setLayoutManager(layoutManager);
                mAdapter = new CategoryAdapter( categoryList);
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