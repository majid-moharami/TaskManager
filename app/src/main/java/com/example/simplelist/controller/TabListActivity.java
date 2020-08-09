package com.example.simplelist.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.simplelist.R;
import com.example.simplelist.fragment.ListFragment;
import com.example.simplelist.repository.TaskRepository;
import com.example.simplelist.utils.DepthPageTransformer;
import com.example.simplelist.utils.ZoomOutPageTransformer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TabListActivity extends AppCompatActivity {
    private ViewPager2 mViewPager2;
    private TabLayout mTabLayout;
    private FloatingActionButton mActionButtonAdd;
    public static final String EXTRA_KEY_NAME_STRING_TAB_LIST_ACTIVITY = "com.example.nameStringTabList";
    public static final String EXTRA_KEY_LIST_COUNT_TAB_LIST_ACTIVITY = "com.example.listCountTabList";


    public static Intent newIntent(Context context, String name, int count) {
        Intent intent = new Intent(context, TabListActivity.class);
        intent.putExtra(EXTRA_KEY_NAME_STRING_TAB_LIST_ACTIVITY, name);
        intent.putExtra(EXTRA_KEY_LIST_COUNT_TAB_LIST_ACTIVITY, count);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_list);
        String name = getIntent().getStringExtra(EXTRA_KEY_NAME_STRING_TAB_LIST_ACTIVITY);
        int n = getIntent().getIntExtra(EXTRA_KEY_LIST_COUNT_TAB_LIST_ACTIVITY, 0);

        //it make first instance of repository
        TaskRepository taskRepository = TaskRepository.getInstance(name, n);
        findViews();
        createViewPager();
        createTabBar();
    }

    private void createViewPager() {
        FragmentStateAdapter TaskAdapter = new TaskPagerListAdapter(this);
        mViewPager2.setAdapter(TaskAdapter);
        mViewPager2.setPageTransformer(new DepthPageTransformer());
    }

    private void createTabBar() {
        new TabLayoutMediator(mTabLayout, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0)
                    tab.setText("TODO");
                else if (position == 1)
                    tab.setText("DOING");
                else if (position == 2)
                    tab.setText("DONE");
            }
        }).attach();
    }

    private void findViews() {
        mViewPager2 = findViewById(R.id.view_pager_tab_list);
        mTabLayout = findViewById(R.id.tab_layout);
        mActionButtonAdd = findViewById(R.id.button_add);
    }

    private class TaskPagerListAdapter extends FragmentStateAdapter {

        public TaskPagerListAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return ListFragment.newInstance(0);
            } else if (position == 1) {
                return ListFragment.newInstance(1);
            } else if (position == 2) {
                return ListFragment.newInstance(2);
            }
            return ListFragment.newInstance(10);
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

}