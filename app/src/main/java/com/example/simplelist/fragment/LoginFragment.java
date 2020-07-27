package com.example.simplelist.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.simplelist.R;
import com.example.simplelist.controller.ListActivity;


public class LoginFragment extends Fragment {

    private TextView mTextViewWrong;
    private EditText mEditTextName , mEditTextNumber;
    private Button mButtonCreate;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        findAllView(view);
        allListener();
        return view;
    }


    private void findAllView(View view) {
        mButtonCreate = view.findViewById(R.id.button_create);
        mEditTextName = view.findViewById(R.id.edittxt_name);
        mEditTextNumber = view.findViewById(R.id.editTextNumber);
        mTextViewWrong = view.findViewById(R.id.textView_wrong);
    }

    private void allListener() {
        mButtonCreate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (mEditTextName.getText().length()!=0 && mEditTextName.getText().length()!=0){
                    Intent intent = ListActivity.newIntent(getActivity(),String.valueOf(mEditTextName.getText()),Integer.parseInt(String.valueOf(mEditTextNumber.getText())));
                    startActivity(intent);
                }else {
                    mTextViewWrong.setText("please fill the name and number completely");
                }
            }
        });
    }
}
