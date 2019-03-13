package com.example.asone_android.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asone_android.Base.BaseActivity;
import com.example.asone_android.R;
import com.example.asone_android.activity.fragment.CollectFragment;
import com.example.asone_android.activity.fragment.HomeFragment;
import com.example.asone_android.activity.fragment.PeopleFragment;
import com.example.asone_android.activity.fragment.VoiceFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private LinearLayout mLlHome, mLlPeople, mLlVoice, mLlCollect;
    private ImageView mIvHome, mIvPeople, mIvVoice, mIvCollect;
    private TextView mTvHome, mTvPeople, mTvVoice, mTvCollect;

    private HomeFragment homeFragment = new HomeFragment();
    private PeopleFragment peopleFragment = new PeopleFragment();
    private VoiceFragment voiceFragment = new VoiceFragment();
    private CollectFragment collectFragment = new CollectFragment();
    List<Fragment> mFragmentList = new ArrayList<>();
    FragmentAdapter mFragmentAdapter;


    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initData() {
        mFragmentList.add(homeFragment);
        mFragmentList.add(peopleFragment);
        mFragmentList.add(voiceFragment);
        mFragmentList.add(collectFragment);
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mFragmentAdapter);
        viewPager.addOnPageChangeListener(this);

    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.viewpager);
        mLlHome = findViewById(R.id.ll_home);
        mLlPeople = findViewById(R.id.ll_people);
        mLlVoice = findViewById(R.id.ll_voice);
        mLlCollect = findViewById(R.id.ll_collect);
        mIvHome = findViewById(R.id.iv_home);
        mIvPeople = findViewById(R.id.iv_people);
        mIvVoice = findViewById(R.id.iv_voice);
        mIvCollect = findViewById(R.id.iv_collect);
        mTvHome = findViewById(R.id.tv_home);
        mTvPeople = findViewById(R.id.tv_people);
        mTvVoice = findViewById(R.id.tv_voice);
        mTvCollect = findViewById(R.id.tv_collect);
        mLlHome.setOnClickListener(this);
        mLlPeople.setOnClickListener(this);
        mLlVoice.setOnClickListener(this);
        mLlCollect.setOnClickListener(this);

    }

    private void AllGray() {
        mIvHome.setImageResource(R.mipmap.house_black);
        mIvPeople.setImageResource(R.mipmap.people_black);
        mIvVoice.setImageResource(R.mipmap.voice_black);
        mIvCollect.setImageResource(R.mipmap.collet_blacl);
        mTvHome.setTextColor(ContextCompat.getColor(this, R.color.black_home_icon));
        mTvPeople.setTextColor(ContextCompat.getColor(this, R.color.black_home_icon));
        mTvVoice.setTextColor(ContextCompat.getColor(this, R.color.black_home_icon));
        mTvCollect.setTextColor(ContextCompat.getColor(this, R.color.black_home_icon));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home:
                selectHome();
                viewPager.setCurrentItem(0);
                break;
            case R.id.ll_people:
                selectPeople();
                viewPager.setCurrentItem(1);
                break;
            case R.id.ll_voice:
                selectVoice();
                viewPager.setCurrentItem(2);
                break;
            case R.id.ll_collect:
                selectCollect();
                viewPager.setCurrentItem(3);
                break;
        }
    }

    private void selectHome() {
        AllGray();
        mIvHome.setImageResource(R.mipmap.house_gray);
        mTvHome.setTextColor(ContextCompat.getColor(this, R.color.gray_bf));
    }

    private void selectPeople() {
        AllGray();
        mIvPeople.setImageResource(R.mipmap.people_gray);
        mTvPeople.setTextColor(ContextCompat.getColor(this, R.color.gray_bf));
    }

    private void selectVoice() {
        AllGray();
        mIvVoice.setImageResource(R.mipmap.voice_gray);
        mTvVoice.setTextColor(ContextCompat.getColor(this, R.color.gray_bf));
    }


    private void selectCollect() {
        AllGray();
        mIvCollect.setImageResource(R.mipmap.collet_gray);
        mTvCollect.setTextColor(ContextCompat.getColor(this, R.color.gray_bf));
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i) {
            case 0:
                selectHome();
                break;
            case 1:
                selectPeople();
                break;
            case 2:
                selectVoice();
                break;
            case 3:
                selectCollect();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager mFragmentManager) {
            super(mFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
