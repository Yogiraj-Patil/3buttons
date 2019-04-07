package com.example.a3buttons;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class DashActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        setuppager(viewPager);

        TabLayout tabs = (TabLayout) findViewById(R.id.tablayout);
        tabs.setupWithViewPager(viewPager);

        ActionBar supportactionbar = getSupportActionBar();
        if(supportactionbar != null){
            supportactionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            supportactionbar.setDisplayHomeAsUpEnabled(true);
        }


        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashActivity.this,Add_Policy.class));
            }
        });

    }

    private void setuppager(ViewPager viewPager){
        CusAdapter adapter = new CusAdapter(getSupportFragmentManager());
        adapter.addFragment(new SearchFragment(),"Search Policy");
        adapter.addFragment(new Update_Fragment(),"Update Policy");
        viewPager.setAdapter(adapter);

    }


    static class CusAdapter extends FragmentPagerAdapter{

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public CusAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return mFragmentList.get(i);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String Title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(Title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
