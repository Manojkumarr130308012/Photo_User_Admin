package com.rajuuu.photo_user_admin.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.rajuuu.photo_user_admin.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText inputEmail;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        inputEmail = findViewById(R.id.inputEmail);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputEmail.getText().toString().equals("")) {
                    inputEmail.requestFocus();
                    inputEmail.setError("Registered Email Required");
                }  else{
                    getData2();
                }
            }
        });
    }

    private void getData2() {

        final ProgressDialog progressDialog = ProgressDialog.show(ForgotPasswordActivity.this,
                "Please wait",
                "Loading...");

        RequestQueue requestQueue= Volley.newRequestQueue(ForgotPasswordActivity.this);
        String url = Constant.API_FORGOT_PASSWORD;
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
                        String idd = obj.getString("cusId");
                        String otpp = obj.getString("otp");
//                        Toast.makeText(ForgotPasswordActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ForgotPasswordActivity.this, ForgotOTPActivity.class);
                        intent.putExtra("maill",inputEmail.getText().toString() );
                        intent.putExtra("idd",idd );
                        intent.putExtra("otpp",otpp );
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ForgotPasswordActivity.this, "Post Data : Response Failed", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("email", ""+inputEmail.getText().toString());
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