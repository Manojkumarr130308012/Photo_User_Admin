package com.rajuuu.photo_user_admin.ChangePassword;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rajuuu.photo_user_admin.Config.Constant;
import com.rajuuu.photo_user_admin.Login.ForgotPageActivity;
import com.rajuuu.photo_user_admin.Login.LoginActivity;
import com.rajuuu.photo_user_admin.R;
import com.rajuuu.photo_user_admin.User.UserMainActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText inputNpass,inputCpass;
    Button btnSubmit;
    String usr_id;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        inputNpass = findViewById(R.id.inputNpass);
        inputCpass = findViewById(R.id.inputCpass);
        btnSubmit = findViewById(R.id.btnSubmit);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
        usr_id  = mPreferences.getString("user_id", "");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputNpass.getText().toString().isEmpty()) {
                    inputNpass.requestFocus();
                    inputNpass.setError("New Password Required");
                } else  if (inputCpass.getText().toString().isEmpty()) {
                    inputCpass.requestFocus();
                    inputCpass.setError("Confirm Password Required");
                } else if (inputNpass.getText().toString().equals(inputCpass.getText().toString())) {
                    submitData();
                } else {
                    inputCpass.requestFocus();
                    inputCpass.setError("Password does not match!");
                }
            }
        });
    }

    private void submitData() {

        final ProgressDialog progressDialog = ProgressDialog.show(ChangePasswordActivity.this,
                "Please wait",
                "Loading...");

        RequestQueue requestQueue= Volley.newRequestQueue(ChangePasswordActivity.this);
        String url = Constant.API_FORGOT_CHANGE_PASSWORD;
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
                        Toast.makeText(ChangePasswordActivity.this, "Password Changed Success!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChangePasswordActivity.this, UserMainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(ChangePasswordActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ChangePasswordActivity.this, "Post Data : Response Failed", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("cid", ""+usr_id);
                params.put("password", ""+inputCpass.getText().toString());
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
}