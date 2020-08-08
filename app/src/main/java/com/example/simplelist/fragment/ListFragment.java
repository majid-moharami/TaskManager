package com.example.simplelist.fragment;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simplelist.R;
import com.example.simplelist.Stats;
import com.example.simplelist.model.Task;
import com.example.simplelist.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment {

    public static final String ARGS_COUNT_INT = "countInt";
    private RecyclerView mRecyclerView;
    private Button mButtonAdd;
    private TextView mTextViewWarning;
    private List<Task> mTasksOfTab = new ArrayList<>();
    private CardView mCardView;
    private TaskRepository mTaskRepository = TaskRepository.getInstance();

    public ListFragment() {
        // Required empty public constructor
    }


    public static ListFragment newInstance(int n) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_COUNT_INT,n);
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

        orientationDiscriminant();

        fillListOfTab();

        updateUI();

        allListener();

        return view;
    }

    private void fillListOfTab() {

        List<Task> tasks0 = new ArrayList<>();
        List<Task> tasks1 = new ArrayList<>();
        List<Task> tasks2 = new ArrayList<>();

        for (int i = 0; i <mTaskRepository.getList().size()  ; i++) {
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

        if (getArguments().getInt(ARGS_COUNT_INT)==0) mTasksOfTab.addAll(tasks0);
        if (getArguments().getInt(ARGS_COUNT_INT)==1) mTasksOfTab.addAll(tasks1);
        if (getArguments().getInt(ARGS_COUNT_INT)==2) mTasksOfTab.addAll(tasks2);
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
        if (mTasksOfTab.size()==0){
            mTextViewWarning.setVisibility(View.VISIBLE);
        }else mTextViewWarning.setVisibility(View.GONE);
        NameAdapter adapter = new NameAdapter(mTasksOfTab);
        mRecyclerView.setAdapter(adapter);
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(),resId);
        mRecyclerView.setLayoutAnimation(animation);
    }

    private void allListener() {
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = new AlphaAnimation(1.0f, 0.0f);
                animation.setDuration(500);
                mButtonAdd.startAnimation(animation);


                Task task = new Task(mTaskRepository.getName());
                if (mTasksOfTab.size()!=0)
                    task.setStats(mTasksOfTab.get(0).getStats());
                else {
                    if (getArguments().getInt(ARGS_COUNT_INT,4)==0)
                        task.setStats(Stats.TODO);
                    if (getArguments().getInt(ARGS_COUNT_INT,4)==1)
                        task.setStats(Stats.DOING);
                    if (getArguments().getInt(ARGS_COUNT_INT,4)==2)
                        task.setStats(Stats.DONE);
                }
                mTasksOfTab.add(task);
                mTaskRepository.insert(task);
                updateUI();
            }
        });
    }

    private void findAllView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_list_container);
        mButtonAdd = view.findViewById(R.id.button_add);
        mTextViewWarning=view.findViewById(R.id.warning);

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
                    mCardView.setBackgroundColor(getAdapterPosition() % 4 == 0 ? R.drawable.dark_back_row : R.drawable.dark_back_row2);
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
            Task task = mTasksOfTab.get(position);
            if (getArguments().getInt(ARGS_COUNT_INT)==0) {
                if (task.getStats().toString().equals("TODO"))
                    holder.onBind(task);
            }else if (getArguments().getInt(ARGS_COUNT_INT)==1){
                if (task.getStats().toString().equals("DOING"))
                    holder.onBind(task);
            }else if (getArguments().getInt(ARGS_COUNT_INT)==2){
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