package com.example.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class PhoneFragment extends AppCompatActivity {

    private SharedPreferenceConfig preferenceConfig;

   /* @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.phone, container, false);

        return v;
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone);
        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());
    }

    public void logoutUser(View view) {
        preferenceConfig.writeLoginStatus(false);
        startActivity(new Intent(PhoneFragment.this, Login.class));
        finish();
    }
}
