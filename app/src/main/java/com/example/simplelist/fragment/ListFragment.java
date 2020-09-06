package com.example.simplelist.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simplelist.R;
import com.example.simplelist.controller.SearchActivity;
import com.example.simplelist.model.Task;
import com.example.simplelist.repository.TaskDBRepository;
import com.example.simplelist.utils.ExtractingTime;
import com.example.simplelist.utils.PhotoScale;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment {


    public static final String ARGS_TAB_STATES = "stateTab";
    public static final int ADD_TASK_REQUEST_CODE = 0;
    public static final int SHOW_DETAIL_TASK_REQUEST_CODE = 1;
    public static final String ARGS_KEY_USER_ID = "userId";
    public static final String EXTRA_KEY_USER_ID = "userID";
    public static final int REQUEST_CODE_SHOW_PHOTO_DIALOG = 2;

    private RecyclerView mRecyclerView;
    private FloatingActionButton mButtonAdd;
    private TextView mTextViewWarning;
    private int mTabPosition;
    private List<Task> mTasksOfTab = new ArrayList<>();
    private TaskDBRepository mTaskRepository;
    private NameAdapter mNameAdapter;
    private String mUserId;

    /**
     * @param state  is show position of viewPager and we bind the task with this param
     * @param userID is the user id of who login to program and bind the task specialy for this user
     * @return
     */
    public static ListFragment newInstance(String state, String userID) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_TAB_STATES, state);
        args.putString(ARGS_KEY_USER_ID, userID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskRepository = TaskDBRepository.getInstance(getActivity());
        mTabPosition = getArguments().getInt(ARGS_TAB_STATES);
        setHasOptionsMenu(true);
        mUserId = getArguments().getString(ARGS_KEY_USER_ID);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findAllView(view);

        orientationDiscriminant();

        fillListOfTab();

        updateUI();

        allListener();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.search_icon:
                clickSearchIcon();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fillListOfTab();
        updateUI();
    }

    private void findAllView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_list_container);
        mButtonAdd = view.findViewById(R.id.button_add);
        mTextViewWarning = view.findViewById(R.id.warning);

    }

    private void allListener() {
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskDialogFragment addTaskDialogFragment = AddTaskDialogFragment.newInstance(getArguments().getString(ARGS_TAB_STATES), getArguments().getString(ARGS_KEY_USER_ID));
                addTaskDialogFragment.setTargetFragment(ListFragment.this, ADD_TASK_REQUEST_CODE);
                addTaskDialogFragment.show(getFragmentManager(), "addTask");

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == ADD_TASK_REQUEST_CODE) {
            Task task = (Task) data.getSerializableExtra(AddTaskDialogFragment.EXTRA_RESPOSE_TASK);
            mTasksOfTab.add(task);
            mTaskRepository.insert(task);
            updateUI();
        }

        if (requestCode == SHOW_DETAIL_TASK_REQUEST_CODE) {

            //getting the task from result of show detail dialog
            Task task = (Task) data.getSerializableExtra(ShowDetailDialogFragment.EXTRA_KEY_TASK_RESPONSE);

            //if state is null it mean task will be delete from repository
            if (task.getStats() == null) {
                for (int i = 0; i < mTasksOfTab.size(); i++) {
                    if (mTasksOfTab.get(i).getTaskID() == task.getTaskID()) {
                        Task task1 = mTasksOfTab.get(i);
                        mTasksOfTab.remove(task1);
                        mTaskRepository.delete(task1);
                        updateUI();
                        return;
                    }
                }
            }
            else {
                for (int i = 0; i < mTasksOfTab.size(); i++) {
                    //find the response task from tasks of tab
                    if (mTasksOfTab.get(i).getTaskID() == task.getTaskID()) {

                        //if response task state is equale with task of repository just update the ui
                        // else the state not equale the task in the mTaskOfTab will remove
                        if (getArguments().getString(ARGS_TAB_STATES).equals(mTasksOfTab.get(i).getStats())) {
                            mTaskRepository.update(task);
                            updateUI();
                        } else {
                            mTasksOfTab.remove(i);
                            mTaskRepository.update(task);
                            updateUI();
                        }
                    }
                }
            }
        }
        if (requestCode==REQUEST_CODE_SHOW_PHOTO_DIALOG){
            updateUI();
        }
    }

    private void fillListOfTab() {

        List<Task> tasks0 = new ArrayList<>();
        List<Task> tasks1 = new ArrayList<>();
        List<Task> tasks2 = new ArrayList<>();
        if (!mUserId.equals("ADMIN")){
            if (mTaskRepository.getList() != null) {
                for (int i = 0; i < mTaskRepository.getList().size(); i++) {
                    if (mTaskRepository.getList().get(i).getStats().toString().equals("TODO") &&
                            mTaskRepository.getList().get(i).getUserID().equals(getArguments().getString(ARGS_KEY_USER_ID))) {
                        tasks0.add(mTaskRepository.getList().get(i));
                    }
                    if (mTaskRepository.getList().get(i).getStats().toString().equals("DOING") &&
                            mTaskRepository.getList().get(i).getUserID().equals(getArguments().getString(ARGS_KEY_USER_ID))) {
                        tasks1.add(mTaskRepository.getList().get(i));
                    }
                    if (mTaskRepository.getList().get(i).getStats().toString().equals("DONE") &&
                            mTaskRepository.getList().get(i).getUserID().equals(getArguments().getString(ARGS_KEY_USER_ID))) {
                        tasks2.add(mTaskRepository.getList().get(i));
                    }
                }
            }
        }else {
            if (mTaskRepository.getList() != null) {
                for (int i = 0; i < mTaskRepository.getList().size(); i++) {
                    if (mTaskRepository.getList().get(i).getStats().toString().equals("TODO")) {
                        tasks0.add(mTaskRepository.getList().get(i));
                    }
                    if (mTaskRepository.getList().get(i).getStats().toString().equals("DOING")) {
                        tasks1.add(mTaskRepository.getList().get(i));
                    }
                    if (mTaskRepository.getList().get(i).getStats().toString().equals("DONE")) {
                        tasks2.add(mTaskRepository.getList().get(i));
                    }
                }
            }
        }
        if (getArguments().getString(ARGS_TAB_STATES).equals("TODO")) {
            mTasksOfTab = tasks0;
        }
        if (getArguments().getString(ARGS_TAB_STATES).equals("DOING")) {
            mTasksOfTab = tasks1;
        }
        if (getArguments().getString(ARGS_TAB_STATES).equals("DONE")) {
            mTasksOfTab = tasks2;
        }

    }

    private void orientationDiscriminant() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

    private void updateUI() {
        fillListOfTab();
        if (mTasksOfTab.size() == 0) {
            mTextViewWarning.setVisibility(View.VISIBLE);
        } else mTextViewWarning.setVisibility(View.GONE);
        if (mNameAdapter == null) {
            mNameAdapter = new NameAdapter(mTasksOfTab);
            mRecyclerView.setAdapter(mNameAdapter);
        } else {
            mNameAdapter.setTasks(mTasksOfTab);
            mNameAdapter.notifyDataSetChanged();
        }
    }

    private void clickSearchIcon() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        intent.putExtra(EXTRA_KEY_USER_ID, getArguments().getString(ARGS_KEY_USER_ID));
        startActivity(intent);
    }

    private class TaskHolder extends RecyclerView.ViewHolder {

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
                    showDetailDialogFragment.setTargetFragment(ListFragment.this, SHOW_DETAIL_TASK_REQUEST_CODE);
                    showDetailDialogFragment.show(getFragmentManager(), "showDetailDialog");
                }
            });
            mImageViewTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowPhotoDialogFragment showPhotoDialogFragment = ShowPhotoDialogFragment.newInstance(mTask.getTaskID().toString());
                    showPhotoDialogFragment.setTargetFragment(ListFragment.this, REQUEST_CODE_SHOW_PHOTO_DIALOG);
                    showPhotoDialogFragment.show(getFragmentManager() , "showPhotoDialog");
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

    private class NameAdapter extends RecyclerView.Adapter<TaskHolder> {

        List<Task> mTasks = new ArrayList<>();

        public void setTasks(List<Task> tasks) {
            mTasks = tasks;
        }

        public NameAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.row_list, parent, false);
            TaskHolder taskHolder = new TaskHolder(view);
            return taskHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            Task task = mTasksOfTab.get(position);
            if (getArguments().getString(ARGS_TAB_STATES).equals("TODO")) {
                if (task.getStats().toString().equals("TODO"))
                    holder.onBind(task);
            } else if (getArguments().getString(ARGS_TAB_STATES).equals("DOING")) {
                if (task.getStats().toString().equals("DOING"))
                    holder.onBind(task);
            } else if (getArguments().getString(ARGS_TAB_STATES).equals("DONE")) {
                if (task.getStats().toString().equals("DONE"))
                    holder.onBind(task);
            }
        }

        @Override
        public int getItemCount() {
            return mTasksOfTab.size();
        }
    }

}