package com.example.fidias.geinbipub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("GEINBIPUB", "MainActovity.onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
