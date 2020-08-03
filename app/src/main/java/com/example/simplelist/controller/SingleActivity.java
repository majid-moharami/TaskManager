package com.example.simplelist.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.simplelist.R;

public abstract class  SingleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_layout_activity);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container,createFragment())
                .commit();

    }
    public abstract Fragment createFragment();
}