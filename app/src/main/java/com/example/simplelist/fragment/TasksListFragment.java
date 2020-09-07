package com.example.simplelist.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simplelist.R;
import com.example.simplelist.model.Task;
import com.example.simplelist.repository.TaskDBRepository;
import com.example.simplelist.utils.ExtractingTime;
import com.example.simplelist.utils.PhotoScale;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TasksListFragment extends Fragment {

    public static final int REQUEST_CODE_SHOW_DETALE_TASK = 0;
    private RecyclerView mRecyclerView;
    private TaskDBRepository mTaskRepository;
    private TasksListFragment.TaskAdapter mTaskAdapter;

    public static TasksListFragment newInstance() {
        TasksListFragment fragment = new TasksListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskRepository = TaskDBRepository.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tasks_list, container, false);
        findViews(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data==null)
            return;

        if (requestCode == REQUEST_CODE_SHOW_DETALE_TASK){
            Task task = (Task) data.getSerializableExtra(ShowDetailDialogFragment.EXTRA_KEY_TASK_RESPONSE);
            if (task.getStats() == null){
                mTaskRepository.delete(task);
            }else {
                mTaskRepository.update(task);
            }
            updateUI();
        }
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.task_recycler);

    }
    private void updateUI(){
        if (mTaskAdapter == null) {
            mTaskAdapter = new TasksListFragment.TaskAdapter(mTaskRepository.getList());
            mRecyclerView.setAdapter(mTaskAdapter);
        } else {
            mTaskAdapter.setTasks(mTaskRepository.getList());
            mTaskAdapter.notifyDataSetChanged();
        }
    }

    private class TaskHolder extends RecyclerView.ViewHolder{
        private TextView mTextViewName, mTextViewState, mTextViewDate, mTextViewTime;
        private ImageView mImageViewTask;
        private Task mTask;
        public TaskHolder(@NonNull View itemView) {
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
                    showDetailDialogFragment.setTargetFragment(TasksListFragment.this, REQUEST_CODE_SHOW_DETALE_TASK);
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

            File photoFile = mTaskRepository.getPhotoFile(getActivity(),mTask);

            if (photoFile!=null && photoFile.exists()){
                Bitmap bitmap = PhotoScale.getScaledBitmap(photoFile.getPath(), getActivity());
                mImageViewTask.setImageBitmap(bitmap);
            }
            else{
                if (task.getStats().toString().equals("TODO"))
                    mImageViewTask.setImageResource(R.mipmap.ic_launcher_todo_image_asset1_foreground);

                if (task.getStats().toString().equals("DOING"))
                    mImageViewTask.setImageResource(R.mipmap.ic_launcher_doing_image_asset_foreground);

                if (task.getStats().toString().equals("DONE"))
                    mImageViewTask.setImageResource(R.mipmap.ic_launcher_done_image_asset_foreground);
            }


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


    private class TaskAdapter extends RecyclerView.Adapter<TasksListFragment.TaskHolder> {

        List<Task> mTasks = new ArrayList<>();

        public void setTasks(List<Task> tasks) {
            mTasks = tasks;
        }

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        @NonNull
        @Override
        public TasksListFragment.TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.row_list, parent, false);
            TasksListFragment.TaskHolder taskHolder = new TasksListFragment.TaskHolder(view);
            return taskHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TasksListFragment.TaskHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.onBind(task);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }
}