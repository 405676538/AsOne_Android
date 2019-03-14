package com.example.asone_android.net;

import android.util.Log;

import com.example.asone_android.Base.BaseJson;
import com.example.asone_android.bean.UpLoad;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicPresenter {
    private static final String TAG = "MusicPresenter";


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


}
