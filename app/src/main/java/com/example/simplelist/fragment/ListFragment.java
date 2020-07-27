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
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.simplelist.R;

import java.util.zip.Inflater;


public class ListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private Button mButtonAdd;

    public ListFragment() {
        // Required empty public constructor
    }


    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
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

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        NameAdapter adapter = new NameAdapter();
        mRecyclerView.setAdapter(adapter);
        return view;
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
        public void onBind(String name , String state){
            mTextViewName.setText(name);
            mTextViewState.setText(state);
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
            holder.onBind("majid","stable");
        }

        @Override
        public int getItemCount() {
            return 100;
        }
    }
}