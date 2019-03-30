package com.example.asone_android.net;

import com.example.asone_android.Base.BaseJson;
import com.google.android.exoplayer2.metadata.id3.ApicFrame;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicPutPresenter {
    private static final String TAG = "MusicPutPresenter";
    Gson mGson = new Gson();

    public interface PutAlbumView{
        void putAlbumSuccess(BaseJson baseJson);
    }

    public void putAlbumHot(int hotnum,String id,PutAlbumView view){
        Call<BaseJson> call = ApiClient.apiList.putMusicAlbumHouse(hotnum,id);
        call.enqueue(new Callback<BaseJson>() {
            @Override
            public void onResponse(Call<BaseJson> call, Response<BaseJson> response) {
                view.putAlbumSuccess(response.body());
            }

            @Override
            public void onFailure(Call<BaseJson> call, Throwable t) {

            }
        });
    }

    public interface PutArtistView{
        void putArtistSuccess(BaseJson baseJson);
    }

    public void putArtistHot(int hotnum,String id,PutArtistView view){
        Call<BaseJson> call = ApiClient.apiList.putArtistList(hotnum,id);
        call.enqueue(new Callback<BaseJson>() {
            @Override
            public void onResponse(Call<BaseJson> call, Response<BaseJson> response) {
                view.putArtistSuccess(response.body());
            }

            @Override
            public void onFailure(Call<BaseJson> call, Throwable t) {

            }
        });
    }
}
