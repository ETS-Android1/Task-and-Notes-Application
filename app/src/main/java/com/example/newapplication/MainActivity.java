package com.example.newapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Launcher Page
 */

public class MainActivity extends AppCompatActivity {

    private ImageView loadLogo;
    private TextView loginText;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        loadLogo = (ImageView) findViewById(R.id.imageLogo);
        loginText = (TextView) findViewById(R.id.textLoginClick);
        signUpButton = (Button) findViewById(R.id.buttonSignUp);

        Picasso.get().load(R.drawable.logo).resize(600,600).centerCrop().into(loadLogo);

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));
            }
        });

    }
}