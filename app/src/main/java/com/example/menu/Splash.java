package com.example.menu;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    private TextView tv;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView)findViewById(R.id.iv);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        tv.startAnimation(animation);
        iv.startAnimation(animation);

        final Intent intent = new Intent (Splash.this,Login.class);
        Thread timer = new Thread(){
            public void run (){
                try{
                    sleep(5000);
                }catch(InterruptedException e ){
                    e.printStackTrace();
                }finally{
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}
