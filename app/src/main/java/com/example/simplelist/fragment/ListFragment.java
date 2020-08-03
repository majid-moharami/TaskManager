package com.example.simplelist.fragment;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.example.simplelist.R;
import com.example.simplelist.Stats;
import com.example.simplelist.model.Task;
import com.example.simplelist.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment {

    public static final String ARGS_COUNT_INT = "countInt";
    public static final String ARGS_STRING_NAME = "name String";
    private RecyclerView mRecyclerView;
    private Button mButtonAdd;


    private TaskRepository mTaskRepository = TaskRepository.getInstance();

    public ListFragment() {
        // Required empty public constructor
    }


    public static ListFragment newInstance(int count, String name) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_COUNT_INT, count);
        args.putString(ARGS_STRING_NAME, name);
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
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findAllView(view);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

        updateUI();

        allListener();
        return view;
    }

    private void updateUI() {
        NameAdapter adapter = new NameAdapter(mTaskRepository.getList());
        mRecyclerView.setAdapter(adapter);
    }

    private void allListener() {
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = new AlphaAnimation(1.0f, 0.0f);
                animation.setDuration(500);
                mButtonAdd.startAnimation(animation);
                Task task = new Task(mTaskRepository.getName());
                mTaskRepository.insert(task);
                updateUI();
            }
        });
    }

    private void findAllView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_list_container);
        mButtonAdd = view.findViewById(R.id.button_add);
    }

    private class NameHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewName, mTextViewState;

        public NameHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewName = itemView.findViewById(R.id.textView_name_list);
            mTextViewState = itemView.findViewById(R.id.textView_state);
        }

        public void onBind(Task task) {
            mTextViewName.setText(task.getName());
            mTextViewState.setText(task.getStats().toString());
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (getAdapterPosition() % 2 == 0) {
                    itemView.setBackgroundResource(getAdapterPosition() % 4 == 0 ? R.drawable.dark_back_row : R.drawable.dark_back_row2);
                } else
                    itemView.setBackgroundResource((getAdapterPosition() + 2) % 3 == 0 ? R.drawable.dark_back_row : R.drawable.dark_back_row2);
            } else {
                itemView.setBackgroundResource(getAdapterPosition() % 2 == 0 ? R.drawable.dark_back_row : R.drawable.dark_back_row2);
            }

        }
    }

    private class NameAdapter extends RecyclerView.Adapter<NameHolder> {

        List<Task> mTasks = new ArrayList<>();

        public NameAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        @NonNull
        @Override
        public NameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.row_list, parent, false);
            NameHolder nameHolder = new NameHolder(view);
            return nameHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull NameHolder holder, int position) {
            Task task = mTaskRepository.get(position);
            holder.onBind(task);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }
}