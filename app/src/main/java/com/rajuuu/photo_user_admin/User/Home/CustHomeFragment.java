package com.rajuuu.photo_user_admin.User.Home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.rajuuu.photo_user_admin.Config.AutoScrollViewPager;
import com.rajuuu.photo_user_admin.Config.Constant;
import com.rajuuu.photo_user_admin.Config.CustPrograssbar;
import com.rajuuu.photo_user_admin.Login.RegisterActivity;
import com.rajuuu.photo_user_admin.R;
import com.rajuuu.photo_user_admin.User.Category.AllCategoryActivity;
import com.rajuuu.photo_user_admin.User.Category.CategoryAdapter;
import com.rajuuu.photo_user_admin.User.Category.CategoryModel;
import com.rajuuu.photo_user_admin.User.Category.ViewSingleCatProductActivity;
import com.rajuuu.photo_user_admin.User.Category.ViewSingleCatProductModel;
import com.rajuuu.photo_user_admin.User.UserMainActivity;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class CustHomeFragment extends Fragment {

    // Banner
    AutoScrollViewPager viewPager;
    TabLayout tabview;
    ArrayList<BannerModel> arrayListBanner;
    ProgressBar bar2;
    // End Banner

    //  Categort ///
    RecyclerView recyclerViewCat;
    private List<CategoryModel> categoryList = new ArrayList<>();
    private CategoryAdapter mAdapter;
    TextView txt_viewllCat;
    //  End Categort ///

    //  Wedding ///
    RecyclerView recyclerViewWedding;
    private List<ViewSingleCatProductModel> weddingList = new ArrayList<>();
    private WeddingAdapter mAdapterWedding;
    TextView txt_viewllWedding;
    String widd;
    //  End Wedding ///

    //  Event ///
    RecyclerView recyclerViewEvent;
    private List<ViewSingleCatProductModel> eventList;
    private WeddingAdapter mAdapterEvent;
    String eidd;
    TextView txt_viewllEvent;
    //  End Event ///

    //  Trend ///
    RecyclerView recyclerViewTrend;
    private List<ViewSingleCatProductModel> trendList = new ArrayList<>();
    private WeddingAdapter mAdapterTrend;
    //  End Trend ///

    TextView districtView;
    LinearLayout mainLay;
    AlertDialog alertDialog;
    public static CustPrograssbar custPrograssbar;

    SharedPreferences mPreferences;
    SharedPreferences.Editor editor;
    MaterialSpinner city_spinner;
    private ArrayList<String> cityName;
    private JSONArray result;
    String ciNamee,usr_id,city_id, state_idd,city_namee;
    int citySts = 0;

    Button btnFilter;
    EditText stateTxtt,cityTxtt,keywordTxtt;
    ArrayList<String> listState;
    ArrayList<String> listStateId;
    String stateIdd = "";
    ArrayList<String> listCity;
    ArrayList<String> listCityId;
    String cityIdd = "";

    public CustHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cust_home, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        tabview = view.findViewById(R.id.tabview);
        bar2=(ProgressBar) view.findViewById(R.id.progressBar2);
        districtView= view.findViewById(R.id.districtView);
        mainLay= view.findViewById(R.id.mainLay);
        recyclerViewCat = view.findViewById(R.id.recyclerViewCat);
        recyclerViewWedding = view.findViewById(R.id.recyclerViewWedding);
        recyclerViewEvent = view.findViewById(R.id.recyclerViewEvent);
        recyclerViewTrend = view.findViewById(R.id.recyclerViewTrend);
        txt_viewllCat = view.findViewById(R.id.txt_viewllCat);
        txt_viewllWedding = view.findViewById(R.id.txt_viewllWedding);
        txt_viewllEvent = view.findViewById(R.id.txt_viewllEvent);
        btnFilter = view.findViewById(R.id.btnFilter);
        stateTxtt = view.findViewById(R.id.stateTxtt);
        cityTxtt = view.findViewById(R.id.cityTxtt);
        keywordTxtt = view.findViewById(R.id.keywordTxtt);

        recyclerViewCat.setItemAnimator(new DefaultItemAnimator());
        recyclerViewWedding.setItemAnimator(new DefaultItemAnimator());
        recyclerViewEvent.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTrend.setItemAnimator(new DefaultItemAnimator());

        custPrograssbar = new CustPrograssbar();
        arrayListBanner = new ArrayList<>();
        cityName = new ArrayList<String>();
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = mPreferences.edit();

        usr_id  = mPreferences.getString("user_id", "");
        state_idd  = mPreferences.getString("user_state", "");
        city_id  = mPreferences.getString("user_city", "");
        districtView.setText(ciNamee);

        listState = new ArrayList<>();
        listStateId = new ArrayList<>();



        if (!city_id.isEmpty()) {
            mainLay.setVisibility(View.VISIBLE);

                    getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute(Constant.API_BANNER);
                new ReadJSON2().execute(Constant.API_CATEGORY);
                new checkState().execute(Constant.API_STATE);
//                new ReadJSON3().execute(Constant.API_HOME_WEDDING+usr_id+"&lid="+city_id);
//                new ReadJSON4().execute(Constant.API_HOME_EVENT+usr_id+"&lid="+city_id);

            }
        });

        }


//        boolean FirstLaunchsts = mPreferences.getBoolean("FirstLaunchsts", false);
//        if (FirstLaunchsts) {
//            editor.putBoolean("FirstLaunchsts", false);
//            editor.commit();
//            pinCodeDialogFirst();
//        }

//        districtView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pinCodeDialogFirst2();
//            }
//        });

        stateTxtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_dialogbox_state, viewGroup, false);

                ArrayAdapter adapter;
                ListView listView = dialogView.findViewById(R.id.list_view);
                TextView cancelBtn = dialogView.findViewById(R.id.cancelBtn);

                Log.e("dfbsxsxsx", "" + listState);


                listView.setChoiceMode(listView.CHOICE_MODE_SINGLE);

                adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_single_choice, listState);
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
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new CustHomeFragment.checkCity().execute(Constant.API_CITY + stateIdd);
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

                    Toast.makeText(getActivity(), "Please Select State!", Toast.LENGTH_SHORT).show();

                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_dialogbox_city, viewGroup, false);

                    ArrayAdapter adapter;
                    ListView listView = dialogView.findViewById(R.id.list_view);
                    TextView cancelBtn = dialogView.findViewById(R.id.cancelBtn);

                    Log.e("dfbsxsxsxcityy", "" + listCity);

//                    listCity.clear();
//                    listCityId.clear();


                    listView.setChoiceMode(listView.CHOICE_MODE_SINGLE);

                    adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_single_choice, listCity);
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
                } else if (keywordTxtt.getText().toString().equals("")) {
                    keywordTxtt.requestFocus();
                    keywordTxtt.setError("Keyword Required");
                } else {
                    Intent intent = new Intent(getActivity(), FilterActivity.class);
                    intent.putExtra("statee_id",stateIdd);
                    intent.putExtra("cityy_id",cityIdd);
                    intent.putExtra("keywordd",keywordTxtt.getText().toString());
                    intent.putExtra("namee","Best For You");
                    startActivity(intent);
                }
            }
        });

        txt_viewllCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AllCategoryActivity.class);
                startActivity(intent);
            }
        });

        txt_viewllWedding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ViewTrendIndiaActivity.class);
                intent.putExtra("idd",usr_id);
                intent.putExtra("namee","Trending in India");
                startActivity(intent);
            }
        });
        txt_viewllEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ViewTrendStateActivity.class);
                intent.putExtra("idd",usr_id);
                intent.putExtra("namee","Trending in State");
                startActivity(intent);
            }
        });


        return view;
    }

    private void pinCodeDialogFirst() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.customview_pincode, null);

        cityName.clear();
        city_spinner= dialogView.findViewById(R.id.city_spinner);
        Button buttonOk= dialogView.findViewById(R.id.buttonOk);

        getCityDatas();
        city_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int i, long id, String item) {
//                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

                ciNamee = item;
                city_id = getCityName(i);
                editor.putString("city_id", city_id);
                editor.putString("city_name", ciNamee);
                editor.commit();
                citySts = 1;
            }
        });

//        city_spinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
////                actNameTxt.setText(getCustomerName2(i));
////                Toast.makeText(getActivity(), ""+adapterView+view+l, Toast.LENGTH_SHORT).show();
//                ciNamee = city_spinner.getSpinner().toString();
//                city_id = getCityName(i);
//                editor.putString("city_id", city_id);
//                editor.putString("city_name", ciNamee);
//                editor.commit();
//                citySts = 1;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                citySts = 0;
//            }
//        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (citySts == 0) {
                    Toast.makeText(getActivity(), "Please Select City", Toast.LENGTH_SHORT).show();
                    city_spinner.requestFocusFromTouch();
                    city_spinner.performClick();
                } else {
                    isPinCode();
                }
            }
        });

        builder.setView(dialogView);
        alertDialog = builder.create();

        alertDialog.show();

    }
    private void pinCodeDialogFirst2() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.customview_pincode, null);

        cityName.clear();
        city_spinner=(MaterialSpinner) dialogView.findViewById(R.id.city_spinner);
        Button buttonOk= dialogView.findViewById(R.id.buttonOk);

//        getCityDatas();
//        city_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
//
//            @Override public void onItemSelected(MaterialSpinner view, int i, long id, String item) {
////                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
//
//                ciNamee = item;
//                city_id = getCityName(i);
//                editor.putString("city_id", city_id);
//                editor.putString("city_name", ciNamee);
//                editor.commit();
//                citySts = 1;
//            }
//        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (citySts == 0) {
                    Toast.makeText(getActivity(), "Please Select City", Toast.LENGTH_SHORT).show();
                    city_spinner.requestFocusFromTouch();
                    city_spinner.performClick();
                } else {
                    isPinCode2();
                }
            }
        });

        builder.setView(dialogView);
        alertDialog = builder.create();

        alertDialog.show();

    }

    private void isPinCode() {

                        districtView.setText(ciNamee);
                        arrayListBanner.clear();
        categoryList.clear();
        weddingList.clear();
        eventList.clear();
        trendList.clear();
                        alertDialog.dismiss();
                        mainLay.setVisibility(View.VISIBLE);
                        custPrograssbar.prograssCreate(getActivity());

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new CustHomeFragment.ReadJSON().execute(Constant.API_BANNER);
                                new CustHomeFragment.ReadJSON2().execute(Constant.API_CATEGORY);
                                new CustHomeFragment.ReadJSON3().execute(Constant.API_TREND_INDIA+usr_id);
                                new CustHomeFragment.ReadJSON4().execute(Constant.API_TREND_STATE+usr_id+"&sid="+state_idd);
                            }
                        });


    }

    private void isPinCode2() {

        districtView.setText(ciNamee);
//                        arrayListBanner.clear();
        categoryList.clear();
        weddingList.clear();
        eventList.clear();
        trendList.clear();
        alertDialog.dismiss();
        mainLay.setVisibility(View.VISIBLE);
        custPrograssbar.prograssCreate(getActivity());

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new CustHomeFragment.ReadJSON2().execute(Constant.API_CATEGORY);
                new CustHomeFragment.ReadJSON3().execute(Constant.API_TREND_INDIA+usr_id);
                new CustHomeFragment.ReadJSON4().execute(Constant.API_TREND_STATE+usr_id+"&sid="+state_idd);
            }
        });


    }

    class ReadJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray =  jsonObject.getJSONArray("banner");

                for(int i =0;i<jsonArray.length(); i++){
                    JSONObject productObject = jsonArray.getJSONObject(i);

                    arrayListBanner.add(new BannerModel(
                            productObject.getString("banner_id"),
                            productObject.getString("banner_img")
                    ));

                    Log.e("hguybddd",""+arrayListBanner.toString());
//
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (getActivity()!=null) {
                //set layout manager and adapter for "GridView"
                SlidingImage_Adapter adapter = new SlidingImage_Adapter(getActivity(), arrayListBanner);
                adapter.notifyDataSetChanged();
                viewPager.setAdapter(adapter);
                bar2.setVisibility(View.GONE);

                viewPager.startAutoScroll();
                viewPager.setInterval(3000);
                viewPager.setCycle(true);
                viewPager.setStopScrollWhenTouch(true);
                tabview.setupWithViewPager(viewPager, true);
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

                for(int i =0;i<6; i++){
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

            if (getActivity()!=null) {
                //set layout manager and adapter for "GridView"
                GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
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

                    widd = productObject.getString("vendor_category");

                    weddingList.add(new ViewSingleCatProductModel(
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
            }

            if (getActivity()!=null) {
                //set layout manager and adapter for "GridView"

                mAdapterWedding = new WeddingAdapter(weddingList);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerViewWedding.setLayoutManager(mLayoutManager);
                recyclerViewWedding.setItemAnimator(new DefaultItemAnimator());
                recyclerViewWedding.setAdapter(mAdapterWedding);

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


    class ReadJSON4 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL4(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray =  jsonObject.getJSONArray("vendor");
                eventList = new ArrayList<>();
                eventList.clear();
                for(int i =0;i<jsonArray.length(); i++){
                    JSONObject productObject = jsonArray.getJSONObject(i);

                    eidd = productObject.getString("vendor_category");

                    eventList.add(new ViewSingleCatProductModel(
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
            }

            if (getActivity()!=null) {
                //set layout manager and adapter for "GridView"
                custPrograssbar.closePrograssBar();
                mAdapterEvent = new WeddingAdapter(eventList);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerViewEvent.setLayoutManager(mLayoutManager);
                recyclerViewEvent.setItemAnimator(new DefaultItemAnimator());
                recyclerViewEvent.setAdapter(mAdapterEvent);

            }

        }
    }

    private static String readURL4(String theUrl) {
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


    class ReadJSON5 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL5(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray =  jsonObject.getJSONArray("vendor");

                for(int i =0;i<jsonArray.length(); i++){
                    JSONObject productObject = jsonArray.getJSONObject(i);

                    trendList.add(new ViewSingleCatProductModel(
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
            }

            if (getActivity()!=null) {
                //set layout manager and adapter for "GridView"
                custPrograssbar.closePrograssBar();
                mAdapterTrend = new WeddingAdapter(trendList);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerViewTrend.setLayoutManager(mLayoutManager);
                recyclerViewTrend.setItemAnimator(new DefaultItemAnimator());
                recyclerViewTrend.setAdapter(mAdapterTrend);

            }

        }
    }

    private static String readURL5(String theUrl) {
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


    private void getCityDatas() {
        String DATA_URL = Constant.API_CITY;
//        Toast.makeText(FormActivity.this, DATA_URL, Toast.LENGTH_LONG).show();
        StringRequest stringRequest = new StringRequest(DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray("location");

                            //Calling method getcusomers to get the customer from the JSON Array
                            getCityName(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getCityName(JSONArray j) {
        //Traversing through all the items in the json array
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                cityName.add(json.getString("location_name"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (getActivity() != null) {
            //Setting adapter to show the items in the spinner
            city_spinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, cityName));
        }
    }

    //Method to get customer id of a particular position
    private String getCityName(int position){
        String price="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            price = json.getString("location_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return price;
    }

    @Override
    public void onResume() {
        try {
//            Toast.makeText(getActivity(), "bbbbbb", Toast.LENGTH_SHORT).show();
            weddingList.clear();
            eventList.clear();
            trendList.clear();
            if (!city_id.isEmpty()) {
                mainLay.setVisibility(View.VISIBLE);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new ReadJSON3().execute(Constant.API_TREND_INDIA+usr_id);
                        new ReadJSON4().execute(Constant.API_TREND_STATE+usr_id+"&sid="+state_idd);

                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onResume();
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

            progressDialog = ProgressDialog.show(getActivity(),
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

            progressDialog = ProgressDialog.show(getActivity(),
                    "Please wait",
                    "Loading...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray =  jsonObject.getJSONArray("location");


                listCity = new ArrayList<>();
                listCityId = new ArrayList<>();

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