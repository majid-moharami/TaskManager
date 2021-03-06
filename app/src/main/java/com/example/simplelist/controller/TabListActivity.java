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
import com.example.simplelist.utils.DepthPageTransformer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TabListActivity extends AppCompatActivity {
    private ViewPager2 mViewPager2;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private FloatingActionButton mActionButtonAdd;
    public static final String EXTRA_KEY_USER_ID_FOR_TAB_LIST_ACTIVITY = "com.example.userIDForTabList";


    public static Intent newIntent(Context context,  String userID) {
        Intent intent = new Intent(context, TabListActivity.class);
        intent.putExtra(EXTRA_KEY_USER_ID_FOR_TAB_LIST_ACTIVITY, userID);
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
        mToolbar  =findViewById(R.id.toolbar_tab_activity);
    }

    private class TaskPagerListAdapter extends FragmentStateAdapter {

        public TaskPagerListAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return ListFragment.newInstance(Stats.TODO.toString(), getIntent().getStringExtra(EXTRA_KEY_USER_ID_FOR_TAB_LIST_ACTIVITY));
            } else if (position == 1) {
                return ListFragment.newInstance(Stats.DOING.toString(),getIntent().getStringExtra(EXTRA_KEY_USER_ID_FOR_TAB_LIST_ACTIVITY));
            } else if (position == 2) {
                return ListFragment.newInstance(Stats.DONE.toString(),getIntent().getStringExtra(EXTRA_KEY_USER_ID_FOR_TAB_LIST_ACTIVITY));
            }
            return ListFragment.newInstance(Stats.TODO.toString(),getIntent().getStringExtra(EXTRA_KEY_USER_ID_FOR_TAB_LIST_ACTIVITY));
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

}