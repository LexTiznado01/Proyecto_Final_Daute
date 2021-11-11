package com.itca.proyecto_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.itca.proyecto_final.ui.login.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;
import android.os.Bundle;


public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String urlGif = "https://proyectofinaldaute2.000webhostapp.com/img/img.gif";
        ImageView imageView = (ImageView) findViewById(R.id.sphlas);
        Uri uri = Uri.parse(urlGif);
        Glide.with(getApplicationContext()).load(uri).into(imageView);

        TimerTask time = new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(splash.this, LoginActivity.class);
                startActivity(i);
                finish();
            }

        };

        Timer t = new Timer();
        t.schedule(time,3700);
    }
}