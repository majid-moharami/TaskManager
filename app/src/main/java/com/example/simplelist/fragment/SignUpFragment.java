package com.example.simplelist.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.simplelist.R;
import com.example.simplelist.model.User;
import com.example.simplelist.repository.UserRepository;


public class SignUpFragment extends Fragment {

    private Button mButtonSignUp;
    private EditText mEditTextUserName,mEditTextPassword;
    private TextView mTextViewWrong;

    private UserRepository mUserRepository = UserRepository.getInstance();
    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        findViews(view);
        allListener();
        return view;
    }

    private void findViews(View view) {
        mButtonSignUp = view.findViewById(R.id.button_signUp_signUp);
        mEditTextUserName = view.findViewById(R.id.edittxt_user_name_signUp);
        mEditTextPassword = view.findViewById(R.id.editText_password_signUp);
        mTextViewWrong = view.findViewById(R.id.textView_wrong_signUp);
    }

    private void allListener(){
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (mEditTextUserName.getText().length() != 0 && mEditTextPassword.getText().length() != 0){

                    for (int i = 0; i < mUserRepository.getList().size() ; i++) {
                        if (!mEditTextUserName.getText().toString().equals(mUserRepository.getList().get(i).getUserName()) &&
                              mEditTextPassword.getText().toString().equals(mUserRepository.getList().get(i).getUser_ID())
                           ){
                            mTextViewWrong.setText("This user already exist. please enter a new name or password");
                            return;
                        }
                    }

                    User user = new User(mEditTextUserName.getText().toString() , mEditTextPassword.getText().toString());
                    mUserRepository.insert(user);
                    getActivity().finish();
                }else {
                    mTextViewWrong.setText("please fill the information completely");
                }
            }
        });
    }
}