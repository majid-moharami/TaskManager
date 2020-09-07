package com.example.simplelist.fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.simplelist.R;
import com.example.simplelist.controller.TabListActivity;
import com.example.simplelist.model.Task;
import com.example.simplelist.model.User;
import com.example.simplelist.repository.TaskDBRepository;
import com.example.simplelist.repository.UserDBRepository;
import com.example.simplelist.utils.ExtractingTime;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private UserDBRepository mRepository;
    private TaskDBRepository mTaskDBRepository;
    private UsersFragment.UserAdapter mUserAdapter;

    public static UsersFragment newInstance() {
        UsersFragment fragment = new UsersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = UserDBRepository.getInstance(getActivity());
        mTaskDBRepository = TaskDBRepository.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_tasks, container, false);
        findViews(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        deleteTodoItem();
        return  view ;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.user_recycler);
    }

    private void updateUI(){
        if (mUserAdapter == null) {
            mUserAdapter = new UsersFragment.UserAdapter(mRepository.getList());
            mRecyclerView.setAdapter(mUserAdapter);
        } else {
            mUserAdapter.setUsers(mRepository.getList());
            mUserAdapter.notifyDataSetChanged();
        }
    }
    private void deleteTodoItem() {
        //Swipe to delete currentTodo Item
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            //private Drawable  icon = ContextCompat.getDrawable(getApplicationContext(),R.drawable.icon_delete);
            private ColorDrawable background = new ColorDrawable(Color.GREEN);


            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                mUserAdapter.deleteCurrentTodoItem(position);
                // showMySnackBar("Todo deleted");
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                    int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                View itemView = viewHolder.itemView;
                int backgroundCornerOffset = 20;

                if (dX > 0) { // Swiping to the right
                    background.setBounds(itemView.getLeft(), itemView.getTop(),
                            itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
                            itemView.getBottom());

                } else if (dX < 0) { // Swiping to the left
                    background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                            itemView.getTop(), itemView.getRight(), itemView.getBottom());
                } else { // view is unSwiped
                    background.setBounds(0, 0, 0, 0);
                }
                background.draw(c);
            }
        }).attachToRecyclerView(mRecyclerView);
    }

    private class UserHolder extends RecyclerView.ViewHolder{
        private TextView mUserName,mPassword,mDate;
        private User mUser;
        public UserHolder(@NonNull View itemView) {
            super(itemView);
            mUserName = itemView.findViewById(R.id.textView_username);
            mPassword = itemView.findViewById(R.id.textView_password);
            mDate = itemView.findViewById(R.id.textView_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(TabListActivity.newIntent(getActivity(),mUser.getUser_ID()));
                }
            });
        }
        public void userBind(User user){
            mUser = user;
            mUserName.setText(user.getUserName());
            mPassword.setText(user.getUser_ID());
            mDate.setText(ExtractingTime.formatDate(user.getDate()));
        }
    }

    private class UserAdapter extends RecyclerView.Adapter<UserHolder>{
        List<User> mUsers  =new ArrayList<>();

        public List<User> getUsers() {
            return mUsers;
        }

        public void setUsers(List<User> users) {
            mUsers = users;
        }

        public UserAdapter(List<User> users) {
            mUsers = users;
        }

        @NonNull
        @Override
        public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.admin_list_row, parent, false);
            UsersFragment.UserHolder userHolder = new UsersFragment.UserHolder(view);
            return userHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull UserHolder holder, int position) {
            User user = mUsers.get(position);
            holder.userBind(user);
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }

        public void deleteCurrentTodoItem(int position) {
            User user = mUsers.get(position);
            mRepository.delete(user);
            List<Task> tasks  =mTaskDBRepository.getList();
            for (int i = 0; i < tasks.size() ; i++) {
                if (tasks.get(i).getUserID().equals(user.getUser_ID()))
                    mTaskDBRepository.delete(tasks.get(i));
            }
            updateUI();
        }
    }
}