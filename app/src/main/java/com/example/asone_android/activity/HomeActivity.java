package com.example.asone_android.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.asone_android.Base.BaseActivity;
import com.example.asone_android.R;
import com.example.asone_android.activity.fragment.AllArtistFragment;
import com.example.asone_android.activity.fragment.AllHouseFragment;
import com.example.asone_android.activity.fragment.CollectFragment;
import com.example.asone_android.activity.fragment.DisclaimerFragment;
import com.example.asone_android.activity.fragment.HomeFragment;
import com.example.asone_android.activity.fragment.MusicListFragment;
import com.example.asone_android.activity.fragment.PeopleFragment;
import com.example.asone_android.activity.fragment.VoiceFragment;
import com.example.asone_android.app.Constant;
import com.example.asone_android.bean.EventBusMessage;
import com.example.asone_android.bean.Music;
import com.example.asone_android.bean.VersionInfo;
import com.example.asone_android.net.MusicPresenter;
import com.example.asone_android.utils.ACache;
import com.example.asone_android.utils.AppUtils;
import com.example.asone_android.utils.ExoUtils;
import com.example.asone_android.utils.PhoneUtil;
import com.example.asone_android.utils.StringUtils;
import com.example.asone_android.utils.TimeUtils;
import com.example.asone_android.utils.version.VersionUpdataHelper;
import com.example.asone_android.view.TimeTextView;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.metadata.emsg.EventMessage;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, MusicPresenter.GetBVersionView {
    private static final String TAG = "HomeActivity";
    private ViewPager viewPager;
    private LinearLayout mLlHome, mLlPeople, mLlVoice, mLlCollect,mLlRecommend;
    private ImageView mIvHome, mIvPeople, mIvVoice, mIvCollect, mIvCloseMusic, mIvPlay,mIvRecommend;
    private TextView mTvHome, mTvPeople, mTvVoice, mTvCollect, mTvMusicName, mTvAllTime,mTvRecommend;
    private LinearLayout mLlPlay;
    private SeekBar progressBar;
    private TextView mTvTime;

    private HomeFragment homeFragment = new HomeFragment();
    private PeopleFragment peopleFragment = new PeopleFragment();
    private VoiceFragment voiceFragment = new VoiceFragment();
    private CollectFragment collectFragment = new CollectFragment();
    private AllHouseFragment mAllHouseFragment = new AllHouseFragment();
    List<Fragment> mFragmentList = new ArrayList<>();
    FragmentAdapter mFragmentAdapter;
    MusicPresenter mMusicPresenter = new MusicPresenter();
    private ExoPlayer player;
    int musicPosition = 0;
    private List<Music> musicList = new ArrayList<>();
    Handler progressHandler;


    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initData() {
        mFragmentList.add(homeFragment);
        mFragmentList.add(mAllHouseFragment);
        mFragmentList.add(peopleFragment);
        mFragmentList.add(voiceFragment);
        mFragmentList.add(collectFragment);
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mFragmentAdapter);
        viewPager.addOnPageChangeListener(this);
        mMusicPresenter.getVersionInfo(this);
        player = ExoUtils.initPlayer(this);
        player.setPlayWhenReady(true);
        progressHandler = new Handler(getMainLooper());
        progressHandler.postDelayed(runnableProgress, 500);
        player.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Log.i(TAG, "onPlayerStateChanged: playbackState===" + playbackState);
                Log.i(TAG, "onPlayerStateChanged: playWhenReady===" + playWhenReady);
                if (playbackState == 4) {
                    mIvPlay.setImageResource(R.mipmap.play_none);
                    progressBar.setProgress(0);
                    showShortToast("即将播放下一首");
                    progressHandler.postDelayed(() -> playMusic(musicPosition + 1, musicList), 1000);
                }
                if (playbackState == Player.STATE_READY) {
                    if (playWhenReady) {
                        Log.i(TAG, "playMusic: " + player.getDuration() + "");
                        String time = "/" + TimeUtils.getAATTTime(player.getDuration());
                        mTvAllTime.setText(time);
                    } else {
                    }
                }
            }
        });
    }


    @Override
    protected void initView() {
        viewPager = findViewById(R.id.viewpager);
        mLlHome = findViewById(R.id.ll_home);
        mLlPeople = findViewById(R.id.ll_people);
        mLlVoice = findViewById(R.id.ll_voice);
        mLlRecommend = findViewById(R.id.ll_recommend);
        mLlCollect = findViewById(R.id.ll_collect);
        mIvHome = findViewById(R.id.iv_home);
        mIvRecommend = findViewById(R.id.iv_recommend);
        mTvRecommend = findViewById(R.id.tv_recommend);
        mIvPeople = findViewById(R.id.iv_people);
        mIvVoice = findViewById(R.id.iv_voice);
        mIvCollect = findViewById(R.id.iv_collect);
        progressBar = findViewById(R.id.pb_week);
        mTvHome = findViewById(R.id.tv_home);
        mTvPeople = findViewById(R.id.tv_people);
        mTvTime = findViewById(R.id.tv_time);
        mTvVoice = findViewById(R.id.tv_voice);
        mTvAllTime = findViewById(R.id.tv_all_time);
        mTvCollect = findViewById(R.id.tv_collect);
        mLlPlay = findViewById(R.id.ll_play);
        mIvCloseMusic = findViewById(R.id.iv_close_music);
        mTvMusicName = findViewById(R.id.tv_music_name);
        mIvPlay = findViewById(R.id.iv_play);
        mLlHome.setOnClickListener(this);
        mLlPeople.setOnClickListener(this);
        mLlVoice.setOnClickListener(this);
        mLlCollect.setOnClickListener(this);
        mIvCloseMusic.setOnClickListener(this);
        mLlRecommend.setOnClickListener(this);
        mIvPlay.setOnClickListener(this);
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i(TAG, "onStartTrackingTouch: " + seekBar.getProgress());
            }


            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.seekTo((long) seekBar.getProgress());
            }

        });

    }

    private void AllGray() {
        mIvHome.setImageResource(R.mipmap.house_black);
        mIvPeople.setImageResource(R.mipmap.people_black);
        mIvVoice.setImageResource(R.mipmap.voice_black);
        mIvCollect.setImageResource(R.mipmap.collet_blacl);
        mIvRecommend.setImageResource(R.mipmap.icon_fragment_recommend);
        mTvHome.setTextColor(ContextCompat.getColor(this, R.color.black_home_icon));
        mTvPeople.setTextColor(ContextCompat.getColor(this, R.color.black_home_icon));
        mTvVoice.setTextColor(ContextCompat.getColor(this, R.color.black_home_icon));
        mTvCollect.setTextColor(ContextCompat.getColor(this, R.color.black_home_icon));
        mTvRecommend.setTextColor(ContextCompat.getColor(this, R.color.black_home_icon));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home:
                selectHome();
                viewPager.setCurrentItem(0);
                break;
            case R.id.ll_recommend:
                selectRecommend();
                viewPager.setCurrentItem(1);
                break;
            case R.id.ll_people:
                selectPeople();
                viewPager.setCurrentItem(2);
                break;
            case R.id.ll_voice:
                selectVoice();
                viewPager.setCurrentItem(3);
                break;
            case R.id.ll_collect:
                selectCollect();
                viewPager.setCurrentItem(4);
                break;
            case R.id.iv_close_music:
                player.stop();
                mLlPlay.setVisibility(View.GONE);
                break;
            case R.id.iv_play:
                int playbackState = player == null ? Player.STATE_IDLE : player.getPlaybackState();
                if (playbackState == Player.STATE_READY) {
                    if (player.getPlayWhenReady()) {
                        player.setPlayWhenReady(false);
                        mIvPlay.setImageResource(R.mipmap.play_none);
                        mTvMusicName.setMovementMethod(ScrollingMovementMethod.getInstance());
                    } else {
                        player.setPlayWhenReady(true);
                        mIvPlay.setImageResource(R.mipmap.play_in);
                    }
                }
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

    private void selectRecommend() {
        AllGray();
        mIvRecommend.setImageResource(R.mipmap.icon_fragment_recommend_write);
        mTvRecommend.setTextColor(ContextCompat.getColor(this, R.color.gray_bf));
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
               selectRecommend();
                break;
            case 2:
                removeAllFragment();
                selectPeople();
                break;
            case 3:
                removeAllFragment();
                selectVoice();
                break;
            case 4:
                removeAllFragment();
                selectCollect();
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
            showShortToast(getString(R.string.has_tip));
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
        if (eventBusMessage.getCode() == EventBusMessage.HOME_PLAY_MUSIC) {
            runOnUiThread(() -> {
                int position = eventBusMessage.getCode1();
                playMusic(position, eventBusMessage.getMusics());
            });
        }
        if (eventBusMessage.getCode() == EventBusMessage.ADD_ALL_ARTIST_FRAGMENT) {
            int type = eventBusMessage.getCode1();
            String typeContent = eventBusMessage.getMsg();
            addAllArtistFragment(type, typeContent);
        }
        if (eventBusMessage.getCode() == EventBusMessage.ADD_ALL_HOUSE_FRAGMENT) {
            addAllHouseFragment();
        }
        if (eventBusMessage.getCode() == EventBusMessage.ADD_DISCLAIMER) {
            addDisclaimerFragment();
        }
        if (eventBusMessage.getCode() == EventBusMessage.ADD_MUSIC_LIST) {
            addMusicListFragment(eventBusMessage.getBundle());
        }
    }

    private void playMusic(int position, List<Music> musicList) {
        Log.i(TAG, "playMusic: " + position);
        if (position >= musicList.size()) {
            position = 0;
        }
        if (musicList != this.musicList) {
            this.musicList.clear();
            this.musicList.addAll(musicList);
        }

        mLlPlay.setVisibility(View.VISIBLE);
        this.musicPosition = position;
        String url = AppUtils.getDownLoadFileUrl(this.musicList.get(position).getAudioId());
        if (player.getPlayWhenReady()){
            player.stop();
        }
        player.prepare(ExoUtils.getMediaSourse(this, url), true, true);
        mTvMusicName.setText(StringUtils.ToDBC(musicList.get(position).getTitle()));
        mIvPlay.setImageResource(R.mipmap.play_in);
        player.setPlayWhenReady(true);

    }

    private List<Fragment> fragments = new ArrayList<>();

    public void addAllArtistFragment(int type, String typeContent) {
        if (type <= -1) {
            type = 0;
        }
        if (typeContent == null) {
            typeContent = "";
        }
        AllArtistFragment fragment = new AllArtistFragment();
        Bundle args = new Bundle();
        args.putString(AllArtistFragment.Companion.getFilter(), typeContent);
        args.putInt(AllArtistFragment.Companion.getType(), type);
        fragment.setArguments(args);
        fragments.add(fragment);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.rl_fragment, fragment).addToBackStack("").commit();
    }

    public void addMusicListFragment(Bundle bundle) {
        MusicListFragment fragment = new MusicListFragment();
        fragment.setArguments(bundle);
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

    Runnable runnableProgress = () -> {
        progressBar.setMax((int) player.getDuration());
        progressBar.setProgress((int) player.getCurrentPosition());
        lastRunProgress();
        mTvTime.setText(TimeUtils.getAATTTime(player.getContentPosition()));
    };

    private void lastRunProgress() {
        progressHandler.postDelayed(runnableProgress, 500);
    }
}
