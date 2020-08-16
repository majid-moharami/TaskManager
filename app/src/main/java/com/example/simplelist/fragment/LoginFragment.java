package com.example.simplelist.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.simplelist.R;
import com.example.simplelist.controller.SignUpActivity;
import com.example.simplelist.controller.TabListActivity;
import com.example.simplelist.repository.UserRepository;


public class LoginFragment extends Fragment {

    private TextView mTextViewWrong;
    private EditText mEditTextName , mEditTextPssword;
    private Button mButtonLogin,mButtonSignUp;

    private UserRepository mUserRepository = UserRepository.getInstance();

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
        mButtonLogin = view.findViewById(R.id.button_login);
        mButtonSignUp = view.findViewById(R.id.button_signUp);
        mEditTextName = view.findViewById(R.id.edittxt_user_name);
        mEditTextPssword = view.findViewById(R.id.editText_password);
        mTextViewWrong = view.findViewById(R.id.textView_wrong);
    }

    private void allListener() {
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                if (mEditTextPssword.getText().length()==0 && mEditTextName.getText().length()==0){
                    Animation animation = new RotateAnimation(1.0f,0.0f);
                    animation.setDuration(500);
                    mButtonLogin.startAnimation(animation);
                    mTextViewWrong.setText("please fill the information completely");
                }

                for (int i = 0; i < mUserRepository.getList().size(); i++) {
                    if (mUserRepository.getList().get(i).getUserName().equals(mEditTextName.getText().toString()) &&
                            mUserRepository.getList().get(i).getUser_ID().equals(mEditTextPssword.getText().toString())){
                        Intent intent = TabListActivity.newIntent(getActivity(),mEditTextPssword.getText().toString());
                        startActivity(intent);
                    }
                }
            }
        });

        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
