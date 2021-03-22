package com.rajuuu.photo_user_admin.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rajuuu.photo_user_admin.Config.Constant;
import com.rajuuu.photo_user_admin.R;
import com.rajuuu.photo_user_admin.User.Category.ViewSingleCatProductModel;
import com.rajuuu.photo_user_admin.User.Wishlist.WishlistActivity;
import com.rajuuu.photo_user_admin.User.Wishlist.WishlistAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText inputFname, inputLname, inputDob, inputNumber, inputEmail, inputAddress,
            inputPassword, inputState,inputCity;
    Button btnSubmit;
    private DatePickerDialog mDatePickerDialog2;

    ArrayList<String> listState;
    ArrayList<String> listStateId;
    String stateIdd = "";
    ArrayList<String> listCity;
    ArrayList<String> listCityId;
    String cityIdd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputFname = findViewById(R.id.inputFname);
        inputLname = findViewById(R.id.inputLname);
        inputDob = findViewById(R.id.inputDob);
        inputNumber = findViewById(R.id.inputNumber);
        inputEmail = findViewById(R.id.inputEmail);
        inputAddress = findViewById(R.id.inputAddress);
        inputPassword = findViewById(R.id.inputPassword);
        btnSubmit = findViewById(R.id.btnSubmit);
        inputState = findViewById(R.id.inputState);
        inputCity = findViewById(R.id.inputCity);

        listState = new ArrayList<>();
        listStateId = new ArrayList<>();

        listCity = new ArrayList<>();
        listCityId = new ArrayList<>();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new RegisterActivity.checkState().execute(Constant.API_STATE);
            }
        });

        setDateTimeField2();
        inputDob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDatePickerDialog2.show();
                return false;
            }
        });


        inputState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_dialogbox_state, viewGroup, false);

                ArrayAdapter adapter;
                ListView listView = dialogView.findViewById(R.id.list_view);
                TextView cancelBtn = dialogView.findViewById(R.id.cancelBtn);

                Log.e("dfbsxsxsx", "" + listState);


                listView.setChoiceMode(listView.CHOICE_MODE_SINGLE);

                adapter = new ArrayAdapter(RegisterActivity.this, android.R.layout.simple_list_item_single_choice, listState);
                listView.setAdapter(adapter);

                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        alertDialog.dismiss();

                        inputState.setText(listState.get(i));
                        stateIdd = listStateId.get(i);


                        if (!stateIdd.isEmpty()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new RegisterActivity.checkCity().execute(Constant.API_CITY + stateIdd);
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

        inputCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (stateIdd.isEmpty()) {

                    Toast.makeText(RegisterActivity.this, "Please Select State!", Toast.LENGTH_SHORT).show();

                } else {

                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_dialogbox_city, viewGroup, false);

                ArrayAdapter adapter;
                ListView listView = dialogView.findViewById(R.id.list_view);
                TextView cancelBtn = dialogView.findViewById(R.id.cancelBtn);

                Log.e("dfbsxsxsxcityy", "" + listCity);

//                    listCity.clear();
//                    listCityId.clear();


                listView.setChoiceMode(listView.CHOICE_MODE_SINGLE);

                adapter = new ArrayAdapter(RegisterActivity.this, android.R.layout.simple_list_item_single_choice, listCity);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);

                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        alertDialog.dismiss();

                        inputCity.setText(listCity.get(i));
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

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputFname.getText().toString().equals("")) {
                    inputFname.requestFocus();
                    inputFname.setError("FirstName is required");
                } else if (inputLname.getText().toString().equals("")) {
                    inputLname.setError("LastName is required");
                    inputLname.requestFocus();
                } else if (inputDob.getText().toString().equals("")) {
                    inputDob.requestFocus();
                    inputDob.setError("DOB required");
                } else if (inputNumber.getText().toString().equals("")) {
                    inputNumber.requestFocus();
                    inputNumber.setError("Mobile Number required");
                } else if (inputEmail.getText().toString().equals("")) {
                    inputEmail.requestFocus();
                    inputEmail.setError("Email required");
                } else if (inputAddress.getText().toString().equals("")) {
                    inputAddress.requestFocus();
                    inputAddress.setError("Address required");
                } else if (inputPassword.getText().toString().equals("")) {
                    inputPassword.requestFocus();
                    inputPassword.setError("Password required");
                } else {
                    getRegister();
                }
            }
        });
    }

    private void setDateTimeField2() {

        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);

                inputDob.setText(fdate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog2.getDatePicker().setMaxDate(System.currentTimeMillis());
//        mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private void getRegister() {

        final ProgressDialog progressDialog = ProgressDialog.show(RegisterActivity.this,
                "Please wait",
                "Loading...");

        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        String url = Constant.API_REGISTER;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //let's parse json data
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject obj = jsonObject.getJSONObject("reg");
                    String sts = obj.getString("sts");
                    String msg = obj.getString("msg");

                    if (sts.equals("Success")) {
                        Toast.makeText(RegisterActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(RegisterActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                    }

                    progressDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
//                    Toast.makeText(ProfileActivity.this, "POST DATA : unable to Parse Json", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Post Data : Response Failed", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("fname", "" + inputFname.getText().toString());
                params.put("lname", "" + inputLname.getText().toString());
                params.put("dob", "" + inputDob.getText().toString());
                params.put("mobile", "" + inputNumber.getText().toString());
                params.put("email", "" + inputEmail.getText().toString());
                params.put("address", "" + inputAddress.getText().toString());
                params.put("state", "" + stateIdd);
                params.put("city", "" + cityIdd);
                params.put("password", "" + inputPassword.getText().toString());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type","application/json;charset=utf-8");
                params.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
//                params.put("Authorization","Bearer "+sToken);
//                params.put("X-Requested-With", "XMLHttpRequest");
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }



    class checkState extends AsyncTask<String, Integer, String> {

        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            return readURL2(params[0]);
        }

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(RegisterActivity.this,
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


    class checkCity extends AsyncTask<String, Integer, String> {

        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(RegisterActivity.this,
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