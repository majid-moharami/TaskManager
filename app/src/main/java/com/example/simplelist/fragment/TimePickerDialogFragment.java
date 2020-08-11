package com.example.simplelist.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.example.simplelist.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimePickerDialogFragment extends DialogFragment {

    public static final String ARGS_GET_DATE = "currentDateOfParent";
    public static final String EXTRA_DATE_RESPONSE = "dateResponse";
    private TimePicker mTimePicker;
    private Date mCurrentDate;



    public TimePickerDialogFragment() {
        // Required empty public constructor
    }


    public static TimePickerDialogFragment newInstance(Date date) {
        TimePickerDialogFragment fragment = new TimePickerDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_GET_DATE,date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentDate = (Date) getArguments().getSerializable(ARGS_GET_DATE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_time_picker_dialog,null);
        mTimePicker=view.findViewById(R.id.time_picker_dialog);
        initTimePicker();
        return new AlertDialog.Builder(getActivity())
                .setTitle("Select Time")
                .setView(view)
                .setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Date date  =getSelectedTime();
                        setResult(date);
                    }
                })
                .setNegativeButton("cancel" , null)
                .create();
    }

    private void initTimePicker(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mCurrentDate);
        mTimePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        mTimePicker.setMinute(calendar.get(Calendar.MINUTE));
    }

    private Date getSelectedTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mCurrentDate);

        GregorianCalendar gregorianCalendar = new
                GregorianCalendar(
                        calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),mTimePicker.getHour(),mTimePicker.getMinute()
                );
        return gregorianCalendar.getTime();
    }

    private void setResult(Date date){
        Fragment fragment = getTargetFragment();
        Date selectDate = date;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE_RESPONSE,selectDate);
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK , intent);
    }
}