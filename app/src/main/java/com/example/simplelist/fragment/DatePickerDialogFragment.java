package com.example.simplelist.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.simplelist.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DatePickerDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatePickerDialogFragment extends DialogFragment {


    public static final String ARGS_GET_DATE = "argsDate";
    public static final String EXTRA_DATE_RESPONSE = "dateResponse";
    private Date mCurrentDate;
    private DatePicker mDatePicker;

    public DatePickerDialogFragment() {
        // Required empty public constructor
    }


    public static DatePickerDialogFragment newInstance(Date date) {
        DatePickerDialogFragment fragment = new DatePickerDialogFragment();
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
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_date_picker_dialog,null);
        mDatePicker = view.findViewById(R.id.date_picker);
        initDatePicker();
        return new AlertDialog.Builder(getActivity())
                .setTitle("Select your Date")
                .setView(view)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Date selectedDate = getSelectedDate();
                        setResult(selectedDate);
                    }
                })
                .setNegativeButton("Cancel" , null)
                .create();
    }

    private void initDatePicker (){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mCurrentDate);
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        mDatePicker.init(year, monthOfYear, dayOfMonth, null);
    }

    private Date getSelectedDate(){
        int year = mDatePicker.getYear();
        int month = mDatePicker.getMonth();
        int day = mDatePicker.getDayOfMonth();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mCurrentDate);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year,month,day,hour,min);
        return gregorianCalendar.getTime();
    }
    private void setResult(Date date){
        Fragment fragment = getTargetFragment();
        Date d = date;
        Intent intent =new Intent();
        intent.putExtra(EXTRA_DATE_RESPONSE,d);
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
    }
}