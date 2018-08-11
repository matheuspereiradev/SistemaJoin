package com.app.join.sistemajoin;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(this, 4000);

    }
    @Override
    public void run(){
        startActivity(new Intent(this,ViewTelaLogin.class));
        finish();
    }

}
