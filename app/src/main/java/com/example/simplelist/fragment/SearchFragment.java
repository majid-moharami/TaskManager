package com.example.simplelist.fragment;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simplelist.R;
import com.example.simplelist.model.Task;
import com.example.simplelist.repository.TaskDBRepository;
import com.example.simplelist.utils.ExtractingTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class SearchFragment extends Fragment {
    public static final int SHOW_DETAIL_SEARCH_TASK_REQUEST_CODE = 0;
    public static final String ARGS_KEY_USER_ID = "userId";
    private RecyclerView mRecyclerView;
    private List<Task> mAllTaskList = new ArrayList<>();
    private List<Task> mAllTask = new ArrayList<>();
    private TaskDBRepository mTaskRepository;
    private TaskSearchAdapter mTaskSearchAdapter;

    private Button mButtonSearch;
    private ImageView mButtonBack;
    private EditText mEditTextTitleSearch, mYearOrigin, mMonthOrigin, mDayOrigin, mYearDestination, mMonthDestination, mDayDestination,
            mHourOrigin, mMinuteOrigin, mHourDestination, mMinuteDestination;
    private CheckBox mCheckBoxSearch;
    private String mUserId;

    public static SearchFragment newInstance(String userId) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_KEY_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserId = getArguments().getString(ARGS_KEY_USER_ID);
        mTaskRepository = TaskDBRepository.getInstance(getActivity());
        mAllTask = mTaskRepository.getList();
        for (int i = 0; i < mAllTask.size(); i++) {
            if (mAllTask.get(i).getUserID().equals(mUserId)){
                mAllTaskList.add(mAllTask.get(i));
            }
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        findViews(view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        allListener();
        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.search_list_container);
        mEditTextTitleSearch = view.findViewById(R.id.search_title);
        mButtonSearch = view.findViewById(R.id.button_do_search);
        mButtonBack = view.findViewById(R.id.button_search_back);
        mCheckBoxSearch = view.findViewById(R.id.checkBox_by_date);
        mYearOrigin = view.findViewById(R.id.editTaxt_year_origin);
        mMonthOrigin = view.findViewById(R.id.editTaxt_month_origin);
        mDayOrigin = view.findViewById(R.id.editTaxt_day_origin);
        mYearDestination = view.findViewById(R.id.editTaxt_year_destination);
        mMonthDestination = view.findViewById(R.id.editTaxt_month_destination);
        mDayDestination = view.findViewById(R.id.editTaxt_day_destination);
        mHourOrigin = view.findViewById(R.id.editTaxt_hour_orogin);
        mMinuteOrigin = view.findViewById(R.id.editTaxt_minute_orogin);
        mHourDestination = view.findViewById(R.id.editTaxt_hour_destination);
        mMinuteDestination = view.findViewById(R.id.editTaxt_minute_destination);
    }

    private void allListener() {
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Task> taskList = new ArrayList<>();
                if (mEditTextTitleSearch.getText().length() != 0 && !mCheckBoxSearch.isChecked()) {
                    String title = mEditTextTitleSearch.getText().toString();
                    for (int i = 0; i < mAllTaskList.size(); i++) {
                        if (mAllTaskList.get(i).getTitle().equals(title) && mAllTaskList.get(i).getUserID().equals(mUserId))
                            taskList.add(mAllTaskList.get(i));
                    }
                    mTaskSearchAdapter = new TaskSearchAdapter(taskList);
                    mRecyclerView.setAdapter(mTaskSearchAdapter);
                    if (taskList.size() == 0) {
                        Toast.makeText(getActivity(), "No task found...", Toast.LENGTH_SHORT).show();
                    }
                } else if (mEditTextTitleSearch.getText().length() != 0 && mCheckBoxSearch.isChecked()) {
                    if ((mYearOrigin.getText().length() != 0 && mMonthOrigin.getText().length() != 0 && mDayOrigin.getText().length() != 0 &&
                            mHourOrigin.getText().length() != 0 && mMinuteOrigin.getText().length() != 0) && (mYearDestination.getText().length() != 0 && mMonthDestination.getText().length() != 0 && mDayDestination.getText().length() != 0 &&
                            mHourDestination.getText().length() != 0 && mMinuteDestination.getText().length() != 0)) {
                        mTaskSearchAdapter = new TaskSearchAdapter(findByTitleAndDate("Title"));
                        mRecyclerView.setAdapter(mTaskSearchAdapter);
                    } else {
                        Toast.makeText(getActivity(), "fill the blank text box ...", Toast.LENGTH_SHORT).show();
                    }
                } else if (mEditTextTitleSearch.getText().length() == 0 && mCheckBoxSearch.isChecked()) {
                    if ((mYearOrigin.getText().length() != 0 && mMonthOrigin.getText().length() != 0 && mDayOrigin.getText().length() != 0 &&
                            mHourOrigin.getText().length() != 0 && mMinuteOrigin.getText().length() != 0) && (mYearDestination.getText().length() != 0 && mMonthDestination.getText().length() != 0 && mDayDestination.getText().length() != 0 &&
                            mHourDestination.getText().length() != 0 && mMinuteDestination.getText().length() != 0)) {
                        mTaskSearchAdapter = new TaskSearchAdapter(findByTitleAndDate("noTitle"));
                        mRecyclerView.setAdapter(mTaskSearchAdapter);
                    } else {
                        Toast.makeText(getActivity(), "fill the blank text box ...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    private List<Task> findByTitleAndDate(String s) {

        List<Task> tasks = new ArrayList<>();
        String title = mEditTextTitleSearch.getText().toString();
        Calendar originCAL = Calendar.getInstance();
        originCAL.set(Calendar.YEAR, Integer.parseInt(mYearOrigin.getText().toString()));
        originCAL.set(Calendar.MONTH, Integer.parseInt(mMonthOrigin.getText().toString())-1);
        originCAL.set(Calendar.DAY_OF_MONTH, Integer.parseInt(mDayOrigin.getText().toString()));
        originCAL.set(Calendar.HOUR_OF_DAY, Integer.parseInt(mHourOrigin.getText().toString()));
        originCAL.set(Calendar.MINUTE, Integer.parseInt(mMinuteOrigin.getText().toString()));
        Calendar destinationCAL = Calendar.getInstance();
        destinationCAL.set(Calendar.YEAR, Integer.parseInt(mYearDestination.getText().toString()));
        destinationCAL.set(Calendar.MONTH, Integer.parseInt(mMonthDestination.getText().toString())-1);
        destinationCAL.set(Calendar.DAY_OF_MONTH, Integer.parseInt(mDayDestination.getText().toString()));
        destinationCAL.set(Calendar.HOUR_OF_DAY, Integer.parseInt(mHourDestination.getText().toString()));
        destinationCAL.set(Calendar.MINUTE, Integer.parseInt(mMinuteDestination.getText().toString()));

        Date minDate = originCAL.getTime();
        Date maxDate = destinationCAL.getTime();
        if (s.equals("title")) {
            for (int i = 0; i < mAllTaskList.size(); i++) {
                String taskTitle = mAllTaskList.get(i).getTitle();
                if (title.equals(taskTitle) && (mAllTaskList.get(i).getDate().after(minDate) && mAllTaskList.get(i).getDate().before(maxDate))) {
                    tasks.add(mAllTaskList.get(i));
                }
            }
        }else {
            for (int i = 0; i < mAllTaskList.size(); i++) {
                if ((mAllTaskList.get(i).getDate().compareTo(minDate)>=0 && mAllTaskList.get(i).getDate().compareTo(maxDate)<=0)) {
                    tasks.add(mAllTaskList.get(i));
                }
            }
        }
        return tasks;
    }

    private class TaskSearchHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewName, mTextViewState, mTextViewDate, mTextViewTime;
        private ImageView mImageViewTask;
        private Task mTask;

        public TaskSearchHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewName = itemView.findViewById(R.id.textView_name_list);
            mTextViewState = itemView.findViewById(R.id.textView_state);
            mImageViewTask = itemView.findViewById(R.id.imageView_task);
            mTextViewDate = itemView.findViewById(R.id.textView_date);
            mTextViewTime = itemView.findViewById(R.id.textView_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowDetailDialogFragment showDetailDialogFragment = ShowDetailDialogFragment.newInstance(mTask);
                    showDetailDialogFragment.setTargetFragment(SearchFragment.this, SHOW_DETAIL_SEARCH_TASK_REQUEST_CODE);
                    showDetailDialogFragment.show(getFragmentManager(), "showDetailDialog");
                }
            });
        }

        public void onBind(Task task) {
            mTask = task;
            mTextViewName.setText(task.getTitle());
            mTextViewState.setText(task.getStats().toString());

            mTextViewDate.setText(ExtractingTime.formatDate(task.getDate()));
            mTextViewTime.setText(ExtractingTime.formatTime(task.getDate()));

            if (task.getStats().toString() == "TODO")
                mImageViewTask.setImageResource(R.mipmap.ic_launcher_todo_image_asset1_foreground);
            if (task.getStats().toString() == "DOING")
                mImageViewTask.setImageResource(R.mipmap.ic_launcher_doing_image_asset_foreground);
            if (task.getStats().toString() == "DONE")
                mImageViewTask.setImageResource(R.mipmap.ic_launcher_done_image_asset_foreground);


            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (getAbsoluteAdapterPosition() % 2 == 0) {
                    itemView.setBackgroundResource(getAbsoluteAdapterPosition() % 4 == 0 ? R.drawable.dark_back_row : R.drawable.dark_back_row2);
                } else
                    itemView.setBackgroundResource((getAbsoluteAdapterPosition() + 2) % 3 == 0 ? R.drawable.dark_back_row : R.drawable.dark_back_row2);
            } else {
                itemView.setBackgroundResource(getAbsoluteAdapterPosition() % 2 == 0 ? R.drawable.dark_back_row : R.drawable.dark_back_row2);
            }

        }
    }


    private class TaskSearchAdapter extends RecyclerView.Adapter<SearchFragment.TaskSearchHolder> {

        List<Task> mTasks = new ArrayList<>();

        public TaskSearchAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        @NonNull
        @Override
        public SearchFragment.TaskSearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.row_list, parent, false);
            SearchFragment.TaskSearchHolder taskSearchHolder = new SearchFragment.TaskSearchHolder(view);
            return taskSearchHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull SearchFragment.TaskSearchHolder holder, int position) {
            Task task = mTasks.get(position);
//            if (getArguments().getInt(ARGS_COUNT_INT) == 0) {
//                if (task.getStats().toString().equals("TODO"))
//                    holder.onBind(task);
//            } else if (getArguments().getInt(ARGS_COUNT_INT) == 1) {
//                if (task.getStats().toString().equals("DOING"))
//                    holder.onBind(task);
//            } else if (getArguments().getInt(ARGS_COUNT_INT) == 2) {
//                if (task.getStats().toString().equals("DONE"))
//                    holder.onBind(task);
//            }
            holder.onBind(task);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }
}