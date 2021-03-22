package com.rajuuu.photo_user_admin.Splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.rajuuu.photo_user_admin.Admin.AdminMainActivity;
import com.rajuuu.photo_user_admin.Login.LoginActivity;
import com.rajuuu.photo_user_admin.R;
import com.rajuuu.photo_user_admin.User.UserMainActivity;


public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_DELAY = 3000;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
//    private GifImageView gifImageView;


    /**
     * Fields
     */
    private ImageView mImageViewLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setBackgroundDrawable(null);


        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor mEditor = mPreferences.edit();
        mEditor.putBoolean("FirstLaunchsts", false);
        mEditor.commit();
//        gifImageView = (GifImageView)findViewById(R.id.gifImageView);

//        try{
//            InputStream inputStream = getAssets().open("dudeani.gif");
//            byte[] bytes = IOUtils.toByteArray(inputStream);
//            gifImageView.setBytes(bytes);
//            gifImageView.startAnimation();
//        }
//        catch (IOException ex)
//        {
//
//        }

        initializeViews();
        animateLogo();
        goToMainPage();

    }

    /**
     * This method initializes the views
     */
    private void initializeViews() {
        mImageViewLogo = findViewById(R.id.image_view_logo);
    }

    /**
     * This method takes user to the main page
     */
    private void goToMainPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPreferences = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);
                boolean custLoggedIn = mPreferences.getBoolean("custLoggedIn", false);
                boolean adminLoggedIn = mPreferences.getBoolean("adminLoggedIn", false);
                if (custLoggedIn) {
                    SplashScreen.this.startActivity(new Intent(SplashScreen.this, UserMainActivity.class));
                    SplashScreen.this.finish();
                } else if (adminLoggedIn) {
                    SplashScreen.this.startActivity(new Intent(SplashScreen.this, AdminMainActivity.class));
                    SplashScreen.this.finish();
                }
                else {
                    SplashScreen.this.startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    SplashScreen.this.finish();
                }
            }
        }, SPLASH_DELAY);
    }

    /**
     * This method animates the logo
     */
    private void animateLogo() {
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_without_duration);
        fadeInAnimation.setDuration(SPLASH_DELAY);

        mImageViewLogo.startAnimation(fadeInAnimation);
    }
}