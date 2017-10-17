package com.example.fidias.geinbipub;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class AgregarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("GEINBIPUB","AgregarActivity.onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
    }


}
