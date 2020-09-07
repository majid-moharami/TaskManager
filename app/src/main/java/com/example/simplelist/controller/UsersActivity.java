package com.example.simplelist.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.simplelist.R;
import com.example.simplelist.Stats;
import com.example.simplelist.fragment.ListFragment;
import com.example.simplelist.fragment.TasksListFragment;
import com.example.simplelist.fragment.UsersFragment;
import com.example.simplelist.utils.DepthPageTransformer;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class UsersActivity extends AppCompatActivity {
    private ViewPager2 mViewPager2;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context , UsersActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_list);
        findViews();
        setSupportActionBar(mToolbar);
        createViewPager();
        createTabBar();
    }

    private void findViews() {
        mViewPager2 = findViewById(R.id.view_pager_tab_list);
        mTabLayout = findViewById(R.id.tab_layout);
        mToolbar  =findViewById(R.id.toolbar_tab_activity);
    }

    private void createViewPager() {
        FragmentStateAdapter TaskAdapter = new AdminPagerAdapter(this);
        mViewPager2.setAdapter(TaskAdapter);
        mViewPager2.setPageTransformer(new DepthPageTransformer());
    }


    private void createTabBar() {
        new TabLayoutMediator(mTabLayout, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0)
                    tab.setText("Users");
                else if (position == 1)
                    tab.setText("All Task");
            }
        }).attach();
    }

    private class AdminPagerAdapter extends FragmentStateAdapter {

        public AdminPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return UsersFragment.newInstance();
            } else if (position == 1) {
                return TasksListFragment.newInstance();
            }
            return UsersFragment.newInstance();
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

}