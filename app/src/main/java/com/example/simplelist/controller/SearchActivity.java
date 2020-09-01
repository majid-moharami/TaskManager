package com.example.simplelist.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.simplelist.R;
import com.example.simplelist.fragment.ListFragment;
import com.example.simplelist.fragment.SearchFragment;

public class SearchActivity extends SingleActivity {

    public static final String EXTRA_KEY_USER_ID = "userID";
    @Override
    public Fragment createFragment() {
        return SearchFragment.newInstance(getIntent().getStringExtra(EXTRA_KEY_USER_ID));
    }
}