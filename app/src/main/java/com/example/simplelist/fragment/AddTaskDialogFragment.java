package com.example.simplelist.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.simplelist.R;
import com.example.simplelist.Stats;
import com.example.simplelist.model.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddTaskDialogFragment extends DialogFragment {

    public static final String ARGS_GET_TAB_STAET = "com.example.simplelist.tab_state";
    public static final String EXTRA_RESPOSE_TASK = "extra_respose_task";
    public static final String DATE_DIALOG_FRAGMENT_TAG = "DateDialogFragmentTag";
    public static final int REQUEST_CODE_DATE_PICKER = 0;
    public static final int REQUEST_CODE_TIME_PICKER = 1;
    public static final String TIME_PICKER_DIALOG_FRAGMENT_TAG = "timePicker";
    public static final String ARGS_KEY_USER_ID_FOR_MAKING_OBJECT = "userIdForMakingObject";
    private EditText mEditTextTitle,mEditTextDescription;
    private Button mButtonDate,mButtonTime,mButtonSave,mButtonCancel;
    private Date mDate;
    private Stats s;
    // TODO: Rename and change types and number of parameters
    public static AddTaskDialogFragment newInstance(String state , String userId) {
        AddTaskDialogFragment fragment = new AddTaskDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_GET_TAB_STAET,state);
        args.putString(ARGS_KEY_USER_ID_FOR_MAKING_OBJECT,userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDate = new Date();
        if (getArguments().getString(ARGS_GET_TAB_STAET).equals("TODO"))
            s=Stats.TODO;
        if (getArguments().getString(ARGS_GET_TAB_STAET).equals("DOING"))
            s=Stats.DOING;
        if (getArguments().getString(ARGS_GET_TAB_STAET).equals("DONE"))
            s=Stats.DONE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_task_dialog, null);
        findViews(view);
        initDialog();
        allListener();

        return view;
    }

    private void initDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        String date = format.format(mDate);
        String time = timeFormat.format(mDate);
        mButtonDate.setText(date);
        mButtonTime.setText(time);
    }


    private void findViews(View view) {
        mEditTextTitle = view.findViewById(R.id.edit_text_title);
        mEditTextDescription = view.findViewById(R.id.edit_text_description);
        mButtonDate = view.findViewById(R.id.add_dialog_button_date);
        mButtonTime = view.findViewById(R.id.add_dialog_button_time);
        mButtonSave = view.findViewById(R.id.add_dialog_button_save);
        mButtonCancel = view.findViewById(R.id.add_dialog_button_cancel);
    }
    private void allListener() {
        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogFragment datePickerDialogFragment = DatePickerDialogFragment.newInstance(mDate);
                datePickerDialogFragment.setTargetFragment(AddTaskDialogFragment.this , REQUEST_CODE_DATE_PICKER);
                datePickerDialogFragment.show(getFragmentManager() , DATE_DIALOG_FRAGMENT_TAG);
            }
        });
        mButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialogFragment timePickerDialogFragment = TimePickerDialogFragment.newInstance(mDate);
                timePickerDialogFragment.setTargetFragment(AddTaskDialogFragment.this, REQUEST_CODE_TIME_PICKER);
                timePickerDialogFragment.show(getFragmentManager() , TIME_PICKER_DIALOG_FRAGMENT_TAG);
            }
        });
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult();
                dismiss();
            }
        });
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void setResult(){
        Fragment fragment = getTargetFragment();
        Task task = new Task(getArguments().getString(ARGS_KEY_USER_ID_FOR_MAKING_OBJECT));
        task.setTitle(mEditTextTitle.getText().toString());
        task.setDate(mDate);
        task.setStats(s);
        task.setDescription(mEditTextDescription.getText().toString());
        Intent intent = new Intent();
        intent.putExtra(EXTRA_RESPOSE_TASK,task);
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_DATE_PICKER) {
            mDate = (Date) data.getSerializableExtra(DatePickerDialogFragment.EXTRA_DATE_RESPONSE);
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
            String date = format.format(mDate);
            String time = timeFormat.format(mDate);
            mButtonDate.setText(date);
            mButtonTime.setText(time);
        }
        if (requestCode == REQUEST_CODE_TIME_PICKER){
            mDate = (Date) data.getSerializableExtra(TimePickerDialogFragment.EXTRA_DATE_RESPONSE);
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
            String date = format.format(mDate);
            String time = timeFormat.format(mDate);
            mButtonDate.setText(date);
            mButtonTime.setText(time);
        }
    }
}