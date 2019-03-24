package com.example.asone_android.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asone_android.Base.BaseActivity;
import com.example.asone_android.R;
import com.example.asone_android.activity.fragment.AllArtistFragment;
import com.example.asone_android.activity.fragment.AllHouseFragment;
import com.example.asone_android.activity.fragment.CollectFragment;
import com.example.asone_android.activity.fragment.DisclaimerFragment;
import com.example.asone_android.activity.fragment.HomeFragment;
import com.example.asone_android.activity.fragment.PeopleFragment;
import com.example.asone_android.activity.fragment.VoiceFragment;
import com.example.asone_android.app.Constant;
import com.example.asone_android.bean.EventBusMessage;
import com.example.asone_android.bean.VersionInfo;
import com.example.asone_android.net.MusicPresenter;
import com.example.asone_android.utils.AppUtils;
import com.example.asone_android.utils.PhoneUtil;
import com.example.asone_android.utils.version.VersionUpdataHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, MusicPresenter.GetBVersionView {
    private static final String TAG = "HomeActivity";
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
    MusicPresenter mMusicPresenter = new MusicPresenter();


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
        mMusicPresenter.getVersionInfo(this);
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
            default:
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
                removeAllFragment();
                selectHome();
                break;
            case 1:
                removeAllFragment();
                selectPeople();
                break;
            case 2:
                removeAllFragment();
                selectVoice();
                break;
            case 3:
                removeAllFragment();
                selectCollect();
                break;
            default:
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void getVersionSuccess(VersionInfo versionInfo) {
        int localCode = PhoneUtil.getVersion(mContext);
        int neCode = versionInfo.getVersionCode();
        if (neCode > localCode) {
            AlertDialog.Builder builder = AppUtils.showDialog(this, () -> download(versionInfo));
            builder.show();
        }
    }

    @Override
    public void versionFails() {
    }

    void download(VersionInfo versionInfo) {
        if (checkPermission(Constant.sPermissionsArray[4], Constant.sPermissionsArray[5])) {
            new VersionUpdataHelper((Activity) mContext, AppUtils.getDownLoadFileUrl(versionInfo.getApkId()), true, "");
        } else {
            showShortToast(getString(R.string.storage_permission_not_has_tip));
        }
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

    @Override
    public void onEventMainThread(EventBusMessage eventBusMessage) {
        super.onEventMainThread(eventBusMessage);
        if (eventBusMessage.getCode() == EventBusMessage.ADD_ALL_ARTIST_FRAGMENT) {
            addAllArtistFragment();
        }
        if (eventBusMessage.getCode() == EventBusMessage.ADD_ALL_HOUSE_FRAGMENT) {
            addAllHouseFragment();
        }
        if (eventBusMessage.getCode() == EventBusMessage.ADD_DISCLAIMER) {
            addDisclaimerFragment();
        }
    }

    private List<Fragment> fragments = new ArrayList<>();

    public void addAllArtistFragment() {
        AllArtistFragment fragment = new AllArtistFragment();
        fragments.add(fragment);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.rl_fragment, fragment).addToBackStack("").commit();
    }

    public void addDisclaimerFragment() {
        DisclaimerFragment fragment = new DisclaimerFragment();
        fragments.add(fragment);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.rl_fragment, fragment).addToBackStack("").commit();
    }

    public void addAllHouseFragment() {
        AllHouseFragment fragment = new AllHouseFragment();
        fragments.add(fragment);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.rl_fragment, fragment).addToBackStack("").commit();
    }

    public void removeAllFragment() {
        EventBus.getDefault().post(new EventBusMessage(EventBusMessage.CAN_SCALL_HOME));
        for (Fragment fragment : fragments) {
            Log.i(TAG, "removeAllFragment: ");
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        fragments.clear();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
