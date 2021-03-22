package com.rajuuu.photo_user_admin.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rajuuu.photo_user_admin.Config.Constant;
import com.rajuuu.photo_user_admin.R;
import com.rajuuu.photo_user_admin.User.UserMainActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText inputPassword,inputEmail;
    TextView gotoRegister,forgotPassword;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        inputPassword = findViewById(R.id.inputPassword);
        inputEmail = findViewById(R.id.inputEmail);
        gotoRegister = findViewById(R.id.gotoRegister);
        forgotPassword = findViewById(R.id.forgotPassword);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputEmail.getText().toString().equals("")) {
                    inputEmail.requestFocus();
                    inputEmail.setError("Mobile Number required");
                } else if (inputPassword.getText().toString().equals("")) {
                    inputPassword.requestFocus();
                    inputPassword.setError("Password required");
                } else {
                    getLogin();
                }
            }
        });
    }

    private void getLogin() {

        final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this,
                "Please wait",
                "Loading...");

        RequestQueue requestQueue= Volley.newRequestQueue(LoginActivity.this);
        String url = Constant.API_USER_LOGIN;
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

                        String cusId = obj.getString("cusId");
                        String cusFname = obj.getString("cusFname");
                        String cusLname = obj.getString("cusLname");
                        String cusMobile = obj.getString("cusMobile");
                        String cusEmail = obj.getString("cusEmail");
                        String cusAddress = obj.getString("cusAddress");
                        String cusDob = obj.getString("cusDob");
                        String cusState = obj.getString("cusState");
                        String cusCity = obj.getString("cusCity");

                        mEditor.putString("user_id", cusId);
                        mEditor.putString("user_fname", cusFname);
                        mEditor.putString("user_lname", cusLname);
                        mEditor.putString("user_mobile", cusMobile);
                        mEditor.putString("user_email", cusEmail);
                        mEditor.putString("user_address", cusAddress);
                        mEditor.putString("user_dob", cusDob);
                        mEditor.putString("user_state", cusState);
                        mEditor.putString("user_city", cusCity);
                        mEditor.putBoolean("custLoggedIn", true);
                        mEditor.putBoolean("FirstLaunchsts", true);
                        mEditor.commit();

                        Toast.makeText(LoginActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, UserMainActivity.class));
                        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                    } else {
                        Toast.makeText(LoginActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(LoginActivity.this, "Post Data : Response Failed", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("mobile", ""+inputEmail.getText().toString());
                params.put("password", ""+inputPassword.getText().toString());
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