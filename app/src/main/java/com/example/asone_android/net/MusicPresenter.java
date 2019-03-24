package com.example.asone_android.net;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.asone_android.Base.BaseJson;
import com.example.asone_android.activity.fragment.CollectFragment;
import com.example.asone_android.app.BaseApplication;
import com.example.asone_android.bean.AllCollectArt;
import com.example.asone_android.bean.Artist;
import com.example.asone_android.bean.BaseListJson;
import com.example.asone_android.bean.CollectInfo;
import com.example.asone_android.bean.Country;
import com.example.asone_android.bean.EventBusMessage;
import com.example.asone_android.bean.Music;
import com.example.asone_android.bean.MusicAlbum;
import com.example.asone_android.bean.MusicAlbumInfo;
import com.example.asone_android.bean.MusicFieldInfo;
import com.example.asone_android.bean.Sound;
import com.example.asone_android.bean.UpLoad;
import com.example.asone_android.bean.VersionInfo;
import com.example.asone_android.utils.ACache;
import com.example.asone_android.utils.AppUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

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
                    Toast.makeText(BaseApplication.getAppContext(),"list为空",Toast.LENGTH_SHORT).show();
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

    public void creatHouseAlbum(String title,MusicAlbum album,CreatHouseAlbumMusic creatHouseAlbumMusic){
        Call<BaseJson> call = ApiClient.apiList.addHouseAlbum(title,album.getImgUrl(),album.getListIds());
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

    public interface GetMusicAlbumView{
        void getAlbumSuccess(List<MusicAlbumInfo> infos);
    }

    public void getMusicAlbum(GetMusicAlbumView albumView){
        Call<List<MusicAlbumInfo>> call = ApiClient.apiList.getMusicAlbumHouse();
        call.enqueue(new Callback<List<MusicAlbumInfo>>() {
            @Override
            public void onResponse(Call<List<MusicAlbumInfo>> call, Response<List<MusicAlbumInfo>> response) {
                List<MusicAlbumInfo> list = response.body();
                if (list == null || list.size() == 0){
                    Toast.makeText(BaseApplication.getAppContext(),"首页数据不见了",Toast.LENGTH_SHORT).show();
                    return;
                }
             albumView.getAlbumSuccess(list);
            }

            @Override
            public void onFailure(Call<List<MusicAlbumInfo>> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t);
            }
        });
    }

    public interface CreatArtistView{
        void creatArtistSuccess(String s);
    }

    public void creatArtist(String head,String brief,String name,String age,String six,String country,String commend,CreatArtistView view){
        Call<BaseJson> call = ApiClient.apiList.addArtist(name,age,six,brief,head,country,commend);
        call.enqueue(new Callback<BaseJson>() {
            @Override
            public void onResponse(Call<BaseJson> call, Response<BaseJson> response) {
                if (response.body() != null) {
                    view.creatArtistSuccess(response.body().getFileId());
                }else {
                    Toast.makeText(BaseApplication.getAppContext(),"Response无返回",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseJson> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t);
            }
        });
    }

    public interface GetArtistView{
        void getArtistSuccess(List<Artist> artists,List<Artist> collects);
    }

    public void getArtistList(GetArtistView view){
        Call<AllCollectArt> call = ApiClient.apiList.getArtistList();
        call.enqueue(new Callback<AllCollectArt>() {
            @Override
            public void onResponse(Call<AllCollectArt> call, Response<AllCollectArt> response) {
                AllCollectArt listJsons = response.body();
                List<BaseListJson> artList = listJsons.getArtistAll();
                List<BaseListJson> collectList = listJsons.getCollectList();

                List<Artist> artists = new ArrayList<>();
                List<Artist> collects = new ArrayList<>();
                List<Artist> coleList = new ArrayList<>();

                for (int i = 0; i < artList.size(); i++) {
                    Artist artist = mGson.fromJson(artList.get(i).getFields().toString(),Artist.class);
                    artists.add(artist);
                }
                for (int i = 0; i < collectList.size(); i++) {
                    Artist artist = mGson.fromJson(collectList.get(i).getFields().toString(),Artist.class);
                    collects.add(artist);
                }
                Log.i(TAG, "onResponse: artists="+artists.size());
                Log.i(TAG, "onResponse: collects="+collects.size());

                for (int i = 0; i < artists.size(); i++) {
                    String artId = artists.get(i).getUpId();
                    for (int j = 0; j < collects.size(); j++) {
                        String collectId = collects.get(j).getUpId();
                        if (collectId.equals(artId) && !TextUtils.isEmpty(artId)){
                            coleList.add(artists.get(i));
                            artists.get(i).setCollect(true);
                        }
                    }
                }
                view.getArtistSuccess(artists,coleList);
            }

            @Override
            public void onFailure(Call<AllCollectArt> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t);
            }
        });
    }

    public interface CreatCountryView{
        void CreatCountrySuccess(BaseJson ba);
    }

    public interface GetCountryView{
        void GetCountrySuccess(List<Country> countries);
    }

    public void creatCountry(String name , String banner ,CreatCountryView creatCountryView){
        Call<BaseJson> baseJsonCall = ApiClient.apiList.addCountry(name, banner) ;
        baseJsonCall.enqueue(new Callback<BaseJson>() {
            @Override
            public void onResponse(Call<BaseJson> call, Response<BaseJson> response) {
                creatCountryView.CreatCountrySuccess(response.body());
            }

            @Override
            public void onFailure(Call<BaseJson> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t);
            }
        });
    }

    public void getCountry(GetCountryView view){
        Call<List<BaseListJson>> jsonCall = ApiClient.apiList.getCountryList();
        jsonCall.enqueue(new Callback<List<BaseListJson>>() {
            @Override
            public void onResponse(Call<List<BaseListJson>> call, Response<List<BaseListJson>> response) {
                List<BaseListJson> jsons = response.body();
                List<Country> countries = new ArrayList<>();
                if (jsons != null){
                    for (int i = 0; i < jsons.size(); i++) {
                        Country country = mGson.fromJson(jsons.get(i).getFields().toString(),Country.class);
                        countries.add(country);
                    }
                }
                view.GetCountrySuccess(countries);
            }

            @Override
            public void onFailure(Call<List<BaseListJson>> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t);
            }
        });
    }


    public interface CreatSoundTypeView{
        void creatSoundTypeSuccess(BaseJson baseJson);
    }

    public interface GetSoundTypeView{
        void getSoundTypoeSuccess(List<Sound> sounds);
    }

    public void creatSoundType(String name,String imgUrl,CreatSoundTypeView creatSoundTypeView){
        Call<BaseJson> baseJsonCall = ApiClient.apiList.creatSoundType(name, imgUrl) ;
        baseJsonCall.enqueue(new Callback<BaseJson>() {
            @Override
            public void onResponse(Call<BaseJson> call, Response<BaseJson> response) {
                creatSoundTypeView.creatSoundTypeSuccess(response.body());
            }

            @Override
            public void onFailure(Call<BaseJson> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t);
            }
        });
    }

    public void getSoundTypeList(GetSoundTypeView getSoundTypeView){
        Call<List<BaseListJson>> call = ApiClient.apiList.getSoundType();
        call.enqueue(new Callback<List<BaseListJson>>() {
            @Override
            public void onResponse(Call<List<BaseListJson>> call, Response<List<BaseListJson>> response) {
                List<BaseListJson> baseListJsons = response.body();
                List<Sound> sounds = new ArrayList<>();
                if (baseListJsons != null) {
                    for (int i = 0; i < baseListJsons.size(); i++) {
                        Sound sound = mGson.fromJson(baseListJsons.get(i).getFields().toString(),Sound.class);
                        sounds.add(sound);
                    }
                }
                getSoundTypeView.getSoundTypoeSuccess(sounds);
            }

            @Override
            public void onFailure(Call<List<BaseListJson>> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t);
            }
        });
    }

    /** 收藏 collect ---------------------------------- */

    public interface GetCollectView{
        void getCollectSuccess(List<CollectInfo> collectInfos);
    }

    public interface AddCollectView{
        void addCollectSuccess(BaseJson baseJson);
    }

    public interface DeleteCollectView{
        void deleteCollectSccess(BaseJson baseJson);
    }

    public void getCollect(GetCollectView getCollectView){
        String userId = ACache.get().getAsString(ACache.TAG_USER_ID);
        if (TextUtils.isEmpty(userId)){
            AppUtils.goLogin();
            return;
        }
        Call<List<BaseListJson>> call = ApiClient.apiList.getCollect();
        call.enqueue(new Callback<List<BaseListJson>>() {
            @Override
            public void onResponse(Call<List<BaseListJson>> call, Response<List<BaseListJson>> response) {
                List<BaseListJson> listJsons = response.body();
                List<CollectInfo> list = new ArrayList<>();
                for (int i = 0; i < listJsons.size(); i++) {
                    CollectInfo info = mGson.fromJson(listJsons.get(i).getFields().toString(),CollectInfo.class);
                    list.add(info);
                }
                getCollectView.getCollectSuccess(list);
            }

            @Override
            public void onFailure(Call<List<BaseListJson>> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t);
            }
        });
    }

    public void addCollect(String upId,AddCollectView addCollectView){
        String userId = ACache.get().getAsString(ACache.TAG_USER_ID);
        if (TextUtils.isEmpty(userId)){
            AppUtils.goLogin();
            return;
        }
        Call<BaseJson> call = ApiClient.apiList.addCollect(userId,upId);
        call.enqueue(new Callback<BaseJson>() {
            @Override
            public void onResponse(Call<BaseJson> call, Response<BaseJson> response) {
                addCollectView.addCollectSuccess(response.body());
            }

            @Override
            public void onFailure(Call<BaseJson> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t);
            }
        });
    }

    public void deleteCollect(String upId,DeleteCollectView deleteCollectView){
        String userId = ACache.get().getAsString(ACache.TAG_USER_ID);
        if (TextUtils.isEmpty(userId)){
            AppUtils.goLogin();
            return;
        }
        Call<BaseJson> call = ApiClient.apiList.deleteCollect(userId,upId);
        call.enqueue(new Callback<BaseJson>() {
            @Override
            public void onResponse(Call<BaseJson> call, Response<BaseJson> response) {
                deleteCollectView.deleteCollectSccess(response.body());
            }

            @Override
            public void onFailure(Call<BaseJson> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t);
            }
        });
    }

    public interface AddUserView{
       void addUserSuccess(BaseJson baseJson);
    }

    public void addUser(String uid,String name,String head,AddUserView addUserView){
        Call<BaseJson> call = ApiClient.apiList.addUser(uid,name,head);
        call.enqueue(new Callback<BaseJson>() {
            @Override
            public void onResponse(Call<BaseJson> call, Response<BaseJson> response) {
                addUserView.addUserSuccess(response.body());
            }

            @Override
            public void onFailure(Call<BaseJson> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t);
            }
        });
    }

    public interface GetBVersionView{
        void getVersionSuccess(VersionInfo versionInfo);
        void versionFails();
    }

    public interface AddVersionView{
        void addVsersionSuccess(BaseJson baseJson);
    }

    public void getVersionInfo(GetBVersionView getBVersionView){
        Call<List<BaseListJson>> call = ApiClient.apiList.getVersion();
        call.enqueue(new Callback<List<BaseListJson>>() {
            @Override
            public void onResponse(Call<List<BaseListJson>> call, Response<List<BaseListJson>> response) {
                List<BaseListJson> listJsons = response.body();
                VersionInfo info = null;
                if (listJsons != null && listJsons.size() > 0 ) {
                    info = mGson.fromJson(listJsons.get(listJsons.size()-1).getFields().toString(), VersionInfo.class);
                    Log.i(TAG, "onResponse: "+info.toString());
                }
                getBVersionView.getVersionSuccess(info);
            }

            @Override
            public void onFailure(Call<List<BaseListJson>> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t);
                getBVersionView.versionFails();
            }
        });
    }

    public void addVersion(String versionCode,String apkId,AddVersionView addVersionView){
        Call<BaseJson> call = ApiClient.apiList.addVersion(versionCode, apkId);
        call.enqueue(new Callback<BaseJson>() {
            @Override
            public void onResponse(Call<BaseJson> call, Response<BaseJson> response) {
                addVersionView.addVsersionSuccess(response.body());
            }

            @Override
            public void onFailure(Call<BaseJson> call, Throwable t) {

            }
        });
    }

}
