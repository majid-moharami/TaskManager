package com.example.simplelist.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.simplelist.R;
import com.example.simplelist.fragment.LoginFragment;
import com.example.simplelist.fragment.SignUpFragment;

public class SignUpActivity extends SingleActivity {


    @Override
    public Fragment createFragment() {
        return SignUpFragment.newInstance();
    }
}