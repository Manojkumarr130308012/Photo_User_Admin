package com.rajuuu.photo_user_admin.User.Category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rajuuu.photo_user_admin.Config.Constant;
import com.rajuuu.photo_user_admin.Config.CustPrograssbar;
import com.rajuuu.photo_user_admin.Login.RegisterActivity;
import com.rajuuu.photo_user_admin.R;
import com.rajuuu.photo_user_admin.User.Home.CustHomeFragment;
import com.rajuuu.photo_user_admin.User.Home.FilterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class ViewSingleCatProductActivity extends AppCompatActivity {

    ArrayList<ViewSingleCatProductModel> arrayList;
    ListView lv;
    public static CustPrograssbar custPrograssbar;
    Toolbar toolbar;
    String cidd,namee,usr_id,city_id;

    TextView seg_filter,seg_sort;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    ArrayList<String> listSort;

    ArrayList<String> listState;
    ArrayList<String> listStateId;
    String stateIdd = "";
    ArrayList<String> listCity;
    ArrayList<String> listCityId;
    String cityIdd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_cat_product);

        toolbar = findViewById(R.id.toolbar);
        lv = (ListView) findViewById(R.id.list_view);
        seg_filter = findViewById(R.id.seg_filter);
        seg_sort = findViewById(R.id.seg_sort);

        Intent i = getIntent();
        cidd = i.getStringExtra("idd");
        namee = i.getStringExtra("namee");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(namee);

        arrayList = new ArrayList<>();
        custPrograssbar = new CustPrograssbar();

        listSort = new ArrayList<>();
        listSort.add("Lowest to Highest");
        listSort.add("Highest to Lowest");
        listSort.add("Highest Rating");

        listState = new ArrayList<>();
        listStateId = new ArrayList<>();

        listCity = new ArrayList<>();
        listCityId = new ArrayList<>();

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
        usr_id  = mPreferences.getString("user_id", "");
        city_id  = mPreferences.getString("city_id", "");


        seg_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewSingleCatProductActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_dialogbox_filter, viewGroup, false);

                Button btnFilter = dialogView.findViewById(R.id.btnFilter);
                EditText stateTxtt = dialogView.findViewById(R.id.stateTxtt);
                EditText cityTxtt = dialogView.findViewById(R.id.cityTxtt);
                TextView cancelBtn = dialogView.findViewById(R.id.cancelBtn);

                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();

                stateTxtt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewSingleCatProductActivity.this);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_dialogbox_state, viewGroup, false);

                        ArrayAdapter adapter;
                        ListView listView = dialogView.findViewById(R.id.list_view);
                        TextView cancelBtn = dialogView.findViewById(R.id.cancelBtn);

                        Log.e("dfbsxsxsx", "" + listState);


                        listView.setChoiceMode(listView.CHOICE_MODE_SINGLE);

                        adapter = new ArrayAdapter(ViewSingleCatProductActivity.this, android.R.layout.simple_list_item_single_choice, listState);
                        listView.setAdapter(adapter);

                        builder.setView(dialogView);
                        AlertDialog alertDialog = builder.create();

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                alertDialog.dismiss();

                                stateTxtt.setText(listState.get(i));
                                stateIdd = listStateId.get(i);


                                if (!stateIdd.isEmpty()) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new checkCity().execute(Constant.API_CITY + stateIdd);
                                        }
                                    });
                                }

//                        Toast.makeText(RegisterActivity.this, "Selected -> " + listStateId.get(i), Toast.LENGTH_SHORT).show();
                            }
                        });

                        cancelBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                            }
                        });

                        alertDialog.show();

                    }
                });

                cityTxtt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (stateIdd.isEmpty()) {

                            Toast.makeText(ViewSingleCatProductActivity.this, "Please Select State!", Toast.LENGTH_SHORT).show();

                        } else {

                            AlertDialog.Builder builder = new AlertDialog.Builder(ViewSingleCatProductActivity.this);
                            ViewGroup viewGroup = findViewById(android.R.id.content);
                            View dialogView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_dialogbox_city, viewGroup, false);

                            ArrayAdapter adapter;
                            ListView listView = dialogView.findViewById(R.id.list_view);
                            TextView cancelBtn = dialogView.findViewById(R.id.cancelBtn);

                            Log.e("dfbsxsxsxcityy", "" + listCity);

//                    listCity.clear();
//                    listCityId.clear();


                            listView.setChoiceMode(listView.CHOICE_MODE_SINGLE);

                            adapter = new ArrayAdapter(ViewSingleCatProductActivity.this, android.R.layout.simple_list_item_single_choice, listCity);
                            adapter.notifyDataSetChanged();
                            listView.setAdapter(adapter);

                            builder.setView(dialogView);
                            AlertDialog alertDialog = builder.create();

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    alertDialog.dismiss();

                                    cityTxtt.setText(listCity.get(i));
                                    cityIdd = listCityId.get(i);

//                        Toast.makeText(RegisterActivity.this, "Selected -> " + listStateId.get(i), Toast.LENGTH_SHORT).show();
                                }
                            });

                            cancelBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog.dismiss();
                                }
                            });

                            alertDialog.show();

                        }

                    }
                });

                btnFilter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (stateTxtt.getText().toString().equals("")) {
                            stateTxtt.requestFocus();
                            stateTxtt.setError("State Required");
                        } else if (cityTxtt.getText().toString().equals("")) {
                            cityTxtt.requestFocus();
                            cityTxtt.setError("City Required");
                        } else {

                            arrayList.clear();
//
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new ReadJSON3().execute(Constant.API_CAT_FILTER+usr_id+"&cid="+cidd+"&lid="+cityIdd+"&sid="+stateIdd);
                                    alertDialog.dismiss();
                                }
                            });
                        }
                    }
                });



                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();

            }
        });

        seg_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewSingleCatProductActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_dialogbox_sort, viewGroup, false);

                ArrayAdapter adapter;
                ListView listView = dialogView.findViewById(R.id.list_view);
                TextView cancelBtn = dialogView.findViewById(R.id.cancelBtn);

                Log.e("dfbsxsxsx", "" + listSort);


                listView.setChoiceMode(listView.CHOICE_MODE_SINGLE);

                adapter = new ArrayAdapter(ViewSingleCatProductActivity.this, android.R.layout.simple_list_item_single_choice, listSort);
                listView.setAdapter(adapter);

                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        alertDialog.dismiss();
                        arrayList.clear();

                        if (cityIdd.isEmpty() && listSort.get(i).equals("Lowest to Highest")) {
                            new ReadJSON2().execute(Constant.API_CAT_SORT + usr_id + "&cid=" + cidd+"&sort="+"asc"+"&vp="+"vendor_amount");
                        } else if (cityIdd.isEmpty() && listSort.get(i).equals("Highest to Lowest")) {
                            new ReadJSON2().execute(Constant.API_CAT_SORT + usr_id + "&cid=" + cidd+"&sort="+"desc"+"&vp="+"vendor_amount");
                        } else if (cityIdd.isEmpty() && listSort.get(i).equals("Highest Rating")) {
                            new ReadJSON2().execute(Constant.API_CAT_SORT + usr_id + "&cid=" + cidd+"&sort="+"desc"+"&vp="+"vendor_rating");
                        } else  if (!cityIdd.isEmpty() && listSort.get(i).equals("Lowest to Highest")) {
                            new ReadJSON2().execute(Constant.API_CAT_FILTER_SORT + usr_id + "&cid=" + cidd+"&sort="+"asc"+"&lid="+cityIdd+"&sid="+stateIdd+"&vp="+"vendor_amount");
                        } else if (!cityIdd.isEmpty() && listSort.get(i).equals("Highest to Lowest")) {
                            new ReadJSON2().execute(Constant.API_CAT_FILTER_SORT + usr_id + "&cid=" + cidd+"&sort="+"desc"+"&lid="+cityIdd+"&sid="+stateIdd+"&vp="+"vendor_amount");
                        } else if (!cityIdd.isEmpty() && listSort.get(i).equals("Highest Rating")) {
                            new ReadJSON2().execute(Constant.API_CAT_FILTER_SORT + usr_id + "&cid=" + cidd+"&sort="+"desc"+"&lid="+cityIdd+"&sid="+stateIdd+"&vp="+"vendor_rating");
                        }

//                        Toast.makeText(ViewSingleCatProductActivity.this, "Selected -> " + listSort.get(i), Toast.LENGTH_SHORT).show();
                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();

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

    class ReadJSON3 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL3(params[0]);
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

    private static String readURL3(String theUrl) {
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
                        new checkState().execute(Constant.API_STATE);
                        new ReadJSON2().execute(Constant.API_CATEGORY_PRODUCTS+usr_id+"&cid="+cidd);
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


    //////////////////  Filter  ////////////////////

    class checkState extends AsyncTask<String, Integer, String> {

        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            return readURLState(params[0]);
        }

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(ViewSingleCatProductActivity.this,
                    "Please wait",
                    "Loading...");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray =  jsonObject.getJSONArray("state");

                for(int i =0;i<jsonArray.length(); i++){
                    JSONObject productObject = jsonArray.getJSONObject(i);

                    listState.add(productObject.getString("state_name"));
                    listStateId.add(productObject.getString("state_id"));
                }
                progressDialog.dismiss();

            } catch (JSONException e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }

        }
    }

    private static String readURLState(String theUrl) {
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


    class checkCity extends AsyncTask<String, Integer, String> {

        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            return readURLCity(params[0]);
        }

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(ViewSingleCatProductActivity.this,
                    "Please wait",
                    "Loading...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray =  jsonObject.getJSONArray("location");

                for(int i =0;i<jsonArray.length(); i++){
                    JSONObject productObject = jsonArray.getJSONObject(i);

                    listCity.add(productObject.getString("location_name"));
                    listCityId.add(productObject.getString("location_id"));
                }
                progressDialog.dismiss();

            } catch (JSONException e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }

        }
    }

    private static String readURLCity(String theUrl) {
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