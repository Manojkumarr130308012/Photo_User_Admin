package com.rajuuu.photo_user_admin.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.rajuuu.photo_user_admin.BuildConfig;
import com.rajuuu.photo_user_admin.ChangePassword.ChangePasswordActivity;
import com.rajuuu.photo_user_admin.Config.Constant;
import com.rajuuu.photo_user_admin.Login.LoginActivity;
import com.rajuuu.photo_user_admin.R;
import com.rajuuu.photo_user_admin.User.Category.AllCategoryActivity;
import com.rajuuu.photo_user_admin.User.Home.CustHomeFragment;
import com.rajuuu.photo_user_admin.User.Wishlist.WishlistActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    String name, mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);


        mDrawerLayout = findViewById(R.id.drawerLayout);

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mPreferences.edit();
        name = mPreferences.getString("user_fname", "");
        mail = mPreferences.getString("user_mobile", "");


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);
        mDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        // so friends my item listener is not working beacouse of some line of code i miss..
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // i miss these line so now lets check it..
        View header = navigationView.inflateHeaderView(R.layout.myheader);
        TextView header_usrname = header.findViewById(R.id.header_usrname);
        TextView header_usrmail = header.findViewById(R.id.header_usrmail);
        header_usrname.setText(name);
        header_usrmail.setText(mail);

        // friends now create fragments

        CustHomeFragment fragment = new CustHomeFragment();
        setTitle("Home Page");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment, "Home");
        fragmentTransaction.commit();

        // so now implement onNavigationItemselected
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        if (id == R.id.home) {
            CustHomeFragment fragment = new CustHomeFragment();
            setTitle("testttt");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Home");
            fragmentTransaction.commit();
        }

        else if (id == R.id.shareapp) {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My Erode (Smart City)");
                String shareMessage= "\nMy Dude app provides all Home based Service & Maintenance through online. Install it now and Book your service.\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "Share Via"));
            } catch(Exception e) {
                //e.toString();
            }
        }
        else if (id == R.id.wishlist) {
            Intent intent = new Intent(UserMainActivity.this, WishlistActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        }
        else if (id == R.id.category) {
            Intent intent = new Intent(UserMainActivity.this, AllCategoryActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        }
        else if (id == R.id.cpassword) {
            Intent intent = new Intent(UserMainActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        }
//        else if (id == R.id.foodMenu) {
//            Intent intent = new Intent(UserMainActivity.this, ViewFoodActivity.class);
//            startActivity(intent);
//            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
//        }else if (id == R.id.contactMenu) {
//            Intent intent = new Intent(UserMainActivity.this, ContactUsActivity.class);
//            startActivity(intent);
//            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
//        }

        else if (id == R.id.logout) {
            Toast.makeText(UserMainActivity.this, "Logged Out Successfully", Toast.LENGTH_LONG).show();
            SharedPreferences spreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor spreferencesEditor = spreferences.edit();
            spreferencesEditor.clear();
            spreferencesEditor.commit();
            // Closing the current activity.
            finish();

            // Redirect to Main Login activity after log out.
            Intent intent = new Intent(UserMainActivity.this, LoginActivity.class);

            startActivity(intent);
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_wishlist:
                Intent intent = new Intent(UserMainActivity.this,WishlistActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserMainActivity.this);
            builder.setTitle(R.string.app_name);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage("Do you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            moveTaskToBack(true);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }




}