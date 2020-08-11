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
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddTaskDialogFragment extends DialogFragment {

    public static final String ARGS_GET_TAB_STATE_NUM = "com.example.simplelist.tab_state";
    public static final String EXTRA_RESPOSE_TASK = "extra_respose_task";
    public static final String DATE_DIALOG_FRAGMENT_TAG = "DateDialogFragmentTag";
    public static final int REQUEST_CODE_DATE_PICKER = 0;
    public static final int REQUEST_CODE_TIME_PICKER = 1;
    public static final String TIME_PICKER_DIALOG_FRAGMENT_TAG = "timePicker";
    private TextInputLayout mTextInputLayoutTitle,mTextInputLayoutDescriptions;
    private EditText mEditTextTitle;
    private Button mButtonDate,mButtonTime,mButtonSave,mButtonCancel;
    private Date mDate;
    private Stats s;
    // TODO: Rename and change types and number of parameters
    public static AddTaskDialogFragment newInstance(int n) {
        AddTaskDialogFragment fragment = new AddTaskDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_GET_TAB_STATE_NUM,n);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDate = new Date();
        if (getArguments().getInt(ARGS_GET_TAB_STATE_NUM)==0)
            s=Stats.TODO;
        if (getArguments().getInt(ARGS_GET_TAB_STATE_NUM)==1)
            s=Stats.DOING;
        if (getArguments().getInt(ARGS_GET_TAB_STATE_NUM)==2)
            s=Stats.DONE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_task_dialog, null);
        findViews(view);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        String date = format.format(mDate);
        String time = timeFormat.format(mDate);
        mButtonDate.setText(date);
        mButtonTime.setText(time);
        allListener();

        return view;
    }


    private void findViews(View view) {
        mTextInputLayoutTitle = view.findViewById(R.id.text_input_layout_title);
        mEditTextTitle = view.findViewById(R.id.edit_text_title);
        mTextInputLayoutDescriptions = view.findViewById(R.id.text_input_layout_description);
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
        Task task = new Task();
        task.setName(mEditTextTitle.getText().toString());
        task.setDate(mDate);
        task.setStats(s);
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