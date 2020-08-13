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
import android.widget.RadioButton;

import com.example.simplelist.R;
import com.example.simplelist.Stats;
import com.example.simplelist.model.Task;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ShowDetailDialogFragment extends DialogFragment {


    public static final String ARGUMENT_KEY_GET_TASK = "task argument key";
    public static final String EXTRA_KEY_TASK_RESPONSE = "taskResponse";
    public static final int REQUEST_CODE_DATE_PICKER = 0;
    public static final int REQUEST_CODE_TIME_PICKER = 1;
    public static final String DATE_DIALOG_FRAGMENT_TAG = "dateDialog_show_detail";
    public static final String TIME_PICKER_DIALOG_FRAGMENT_TAG = "timeDialog_show_detail";
    private Task mTask;
    private Button mButtonSave,mButtonEdit,mButtonDelete,mButtonDate , mButtonTime;
    private EditText mEditTextTitle ,mEditTextDescription;
    private RadioButton mRadioButtonTODO,mRadioButtonDONE,mRadioButtonDOING;
    private TextInputLayout mTextInputLayoutTitle,mTextInputLayoutDescription;
    public ShowDetailDialogFragment() {
        // Required empty public constructor
    }


    public static ShowDetailDialogFragment newInstance(Task task) {
        ShowDetailDialogFragment fragment = new ShowDetailDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_KEY_GET_TASK,task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTask = (Task) getArguments().getSerializable(ARGUMENT_KEY_GET_TASK);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_show_detials_dialog, null);
        findViews(view);
        initDialog();
        setListeners();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_DATE_PICKER) {
            mTask.setDate( (Date) data.getSerializableExtra(DatePickerDialogFragment.EXTRA_DATE_RESPONSE));
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
            String date = format.format(mTask.getDate());
            String time = timeFormat.format(mTask.getDate());
            mButtonDate.setText(date);
            mButtonTime.setText(time);
        }

        if (requestCode == REQUEST_CODE_TIME_PICKER){
            mTask.setDate( (Date) data.getSerializableExtra(DatePickerDialogFragment.EXTRA_DATE_RESPONSE));
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
            String date = format.format(mTask.getDate());
            String time = timeFormat.format(mTask.getDate());
            mButtonDate.setText(date);
            mButtonTime.setText(time);
        }
    }

    private void findViews(View view) {
        mEditTextTitle = view.findViewById(R.id.edit_text_title_detail_dialog);
        mEditTextDescription = view.findViewById(R.id.edit_text_description_detail_dialog);
        mButtonSave = view.findViewById(R.id.show_detail_btn_save);
        mButtonEdit = view.findViewById(R.id.show_detail_btn_edit);
        mButtonDelete = view.findViewById(R.id.show_detail_btn_delete);
        mButtonDate = view.findViewById(R.id.show_detail_dialog_button_date);
        mButtonTime = view.findViewById(R.id.show_detail_dialog_button_time);
        mRadioButtonTODO = view.findViewById(R.id.radioButton_TODO);
        mRadioButtonDOING = view.findViewById(R.id.radioButton_DOING);
        mRadioButtonDONE = view.findViewById(R.id.radioButton_DONE);
    }

    private void initDialog(){
        mEditTextTitle.setText(mTask.getName());
        mEditTextDescription.setText(mTask.getDescription());
        if (mTask.getStats().toString().equals("TODO"))
            mRadioButtonTODO.setChecked(true);
        if (mTask.getStats().toString().equals("DOING"))
            mRadioButtonDOING.setChecked(true);
        if (mTask.getStats().toString().equals("DONE"))
            mRadioButtonDONE.setChecked(true);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mTask.getDate());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        String date = format.format(mTask.getDate());
        String time = timeFormat.format(mTask.getDate());
        mButtonDate.setText(date);
        mButtonTime.setText(time);

       isClickableDetail(false);
    }

    private void isClickableDetail(boolean b) {
        mButtonDate.setEnabled(b);
        mButtonTime.setEnabled(b);
        mEditTextTitle.setEnabled(b);
        mEditTextDescription.setEnabled(b);
        mRadioButtonTODO.setEnabled(b);
        mRadioButtonDOING.setEnabled(b);
        mRadioButtonDONE.setEnabled(b);
    }

    private void setListeners(){
        mButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClickableDetail(true);
            }
        });

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask.setName(mEditTextTitle.getText().toString());
                mTask.setDescription(mEditTextDescription.getText().toString());
                if (mRadioButtonTODO.isChecked())
                    mTask.setStats(Stats.TODO);
                if (mRadioButtonDONE.isChecked())
                    mTask.setStats(Stats.DONE);
                if (mRadioButtonDOING.isChecked())
                    mTask.setStats(Stats.DOING);

                setResult(mTask);
                dismiss();
            }
        });

        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask.setStats(null);
                setResult(mTask);
                dismiss();
            }
        });

        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogFragment datePickerDialogFragment = DatePickerDialogFragment.newInstance(mTask.getDate());
                datePickerDialogFragment.setTargetFragment(ShowDetailDialogFragment.this , REQUEST_CODE_DATE_PICKER);
                datePickerDialogFragment.show(getFragmentManager() , DATE_DIALOG_FRAGMENT_TAG);
            }
        });

        mButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialogFragment timePickerDialogFragment = TimePickerDialogFragment.newInstance(mTask.getDate());
                timePickerDialogFragment.setTargetFragment(ShowDetailDialogFragment.this, REQUEST_CODE_TIME_PICKER);
                timePickerDialogFragment.show(getFragmentManager() , TIME_PICKER_DIALOG_FRAGMENT_TAG);
            }
        });

    }

    private void setResult(Task task){
        Fragment fragment = getTargetFragment();
        Intent intent = new Intent();
        intent.putExtra(EXTRA_KEY_TASK_RESPONSE,task);
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
    }
}