package com.example.simplelist.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.simplelist.R;
import com.example.simplelist.State;

import java.util.zip.Inflater;


public class ListFragment extends Fragment {

    public static final String ARGS_COUNT_INT = "countInt";
    public static final String ARGS_STRING_NAME = "name String";
    private RecyclerView mRecyclerView;
    private Button mButtonAdd;

    private int countDataSets;

    public ListFragment() {
        // Required empty public constructor
    }


    public static ListFragment newInstance(int count,String name) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_COUNT_INT,count);
        args.putString(ARGS_STRING_NAME,name);
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
        countDataSets=getArguments().getInt(ARGS_COUNT_INT);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        NameAdapter adapter = new NameAdapter();
        mRecyclerView.setAdapter(adapter);

        allListener();
        return view;
    }

    private void allListener() {
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = new AlphaAnimation(1.0f,0.0f);
                animation.setDuration(500);
                mButtonAdd.startAnimation(animation);
                countDataSets++;
                NameAdapter adapter = new NameAdapter();
                mRecyclerView.setAdapter(adapter);
            }
        });
    }

    private void findAllView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_list_container);
        mButtonAdd = view.findViewById(R.id.button_add);
    }

    private class NameHolder extends RecyclerView.ViewHolder{

        private TextView mTextViewName,mTextViewState;
        public NameHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewName=itemView.findViewById(R.id.textView_name_list);
            mTextViewState=itemView.findViewById(R.id.textView_state);
        }
        public void onBind(String name , State state){
            mTextViewName.setText(name);
            mTextViewState.setText(state.toString());
            itemView.setBackgroundResource(getAdapterPosition()%2==0 ? R.drawable.dark_back_row : R.drawable.dark_back_row2);
        }
    }

    private class NameAdapter extends RecyclerView.Adapter<NameHolder>{

        @NonNull
        @Override
        public NameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.row_list,parent,false);
            NameHolder nameHolder = new NameHolder(view);
            return nameHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull NameHolder holder, int position) {
            State state = State.TODO;
            if (position%2==0)
                state=State.DONE;
            if (position%5==0)
                state=State.DOING;

            holder.onBind(getArguments().getString(ARGS_STRING_NAME),state);
        }

        @Override
        public int getItemCount() {
            return countDataSets;
        }
    }
}