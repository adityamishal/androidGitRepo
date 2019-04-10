package com.example.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LogoutActivity extends AppCompatActivity {

    private SharedPreferenceConfig preferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());
    }


    public void logoutUser(View view) {
        preferenceConfig.writeLoginStatus(false);
        startActivity(new Intent(LogoutActivity.this, Login.class));
        finish();
    }
}
