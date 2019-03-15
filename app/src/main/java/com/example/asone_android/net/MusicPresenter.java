package com.example.asone_android.net;

import android.util.Log;

import com.example.asone_android.Base.BaseJson;
import com.example.asone_android.bean.Music;
import com.example.asone_android.bean.MusicAlbum;
import com.example.asone_android.bean.MusicFieldInfo;
import com.example.asone_android.bean.UpLoad;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicPresenter {
    private static final String TAG = "MusicPresenter";
    Gson mGson = new Gson();

    /** 上传文件 */
    public interface UpLoadView{
        void Fails(String s);
        void upLoadSuccess(String id,int type);
    }

    private UpLoadView upLoadView;


    public void postUpLoad(File file,UpLoadView upLoadView,int type){
        this.upLoadView = upLoadView;
        RequestBody body = RequestBody.create(MediaType.parse("image/jpg"),file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file",file.getName(),body);

        RequestBody bodyType = RequestBody.create(MediaType.parse("multipart/form-data"),"1");
        Map<String,RequestBody> map = new HashMap<>();
        map.put("type",bodyType);

        Call<UpLoad> call = ApiClient.apiList.postUpLoad(map,part);
        call.enqueue(new Callback<UpLoad>() {
            @Override
            public void onResponse(Call<UpLoad> call, Response<UpLoad> response) {
                UpLoad upLoad = response.body();
                if (upLoad != null) {
                    Log.i(TAG, "onResponse: "+upLoad);
                    upLoadView.upLoadSuccess(upLoad.getId(),type);
                }else {
                    Log.i(TAG, "onResponse: null respose");
                    upLoadView.Fails("null respose");
                }
            }

            @Override
            public void onFailure(Call<UpLoad> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
                upLoadView.Fails(t.getMessage());

            }
        });
    }

    public interface CreatMusicView{
        void Fails(String s);
        void creatMusicSuccess(String hint);
    }

    private CreatMusicView creatMusicView;

    public void creatMusic(String audioId ,String imgId,String title, String musicLabel,String artist,String country,CreatMusicView creatMusicView){
        this.creatMusicView = creatMusicView;
        Call<UpLoad> call = ApiClient.apiList.postMusic(audioId, imgId, title, musicLabel, artist, country);
        call.enqueue(new Callback<UpLoad>() {
            @Override
            public void onResponse(Call<UpLoad> call, Response<UpLoad> response) {
                UpLoad upLoad = response.body();
                if (upLoad != null) {
                    Log.i(TAG, "onResponse: "+upLoad);
                    creatMusicView.creatMusicSuccess(upLoad.getId());
                }else {
                    Log.i(TAG, "onResponse: null respose");
                    creatMusicView.Fails("null respose");
                }
            }

            @Override
            public void onFailure(Call<UpLoad> call, Throwable t) {

            }
        });
    }

    public interface GetMusicView{
        void getMusicDuccess(List<Music> album);
    }

    public void getAlbumMusic(String musicLabel,GetMusicView getMusicView){
        Call<List<MusicFieldInfo>> call = ApiClient.apiList.getMusicAlbum(musicLabel);
        call.enqueue(new Callback<List<MusicFieldInfo>>() {
            @Override
            public void onResponse(Call<List<MusicFieldInfo>> call, Response<List<MusicFieldInfo>> response) {
                List<MusicFieldInfo> list = response.body();
                if (list == null || list.size() == 0){
                    Log.i(TAG, "onFailure: no data");
                    return;
                }
                List<Music> musicList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    musicList.add(list.get(i).getFields());
                }
                getMusicView.getMusicDuccess(musicList);
            }

            @Override
            public void onFailure(Call<List<MusicFieldInfo>> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t);
            }
        });
    }

    public interface CreatHouseAlbumMusic{
        void albumUpSuccess(BaseJson json);
    }

    public void creatHouseAlbum(MusicAlbum album,CreatHouseAlbumMusic creatHouseAlbumMusic){
        Call<BaseJson> call = ApiClient.apiList.addHouseAlbum(album.getImgUrl(),mGson.toJson(album.getMusicList()));
        call.enqueue(new Callback<BaseJson>() {
            @Override
            public void onResponse(Call<BaseJson> call, Response<BaseJson> response) {
                creatHouseAlbumMusic.albumUpSuccess(response.body());
            }

            @Override
            public void onFailure(Call<BaseJson> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

}
