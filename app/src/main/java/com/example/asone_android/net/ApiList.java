package com.example.asone_android.net;

import com.example.asone_android.Base.BaseJson;
import com.example.asone_android.bean.MusicAlbum;
import com.example.asone_android.bean.MusicAlbumInfo;
import com.example.asone_android.bean.MusicFieldInfo;
import com.example.asone_android.bean.UpLoad;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiList {

    /** 上传文件 */
    @POST("/upLoad")
    @Multipart
    Call<UpLoad> postUpLoad( @PartMap() Map<String, RequestBody> body,@Part MultipartBody.Part image);

    /** 生成一个Music文件 */
    @POST("/music")
    @FormUrlEncoded
    Call<UpLoad> postMusic(@Field("audioId")String audioId,@Field("imgId")String imgId,@Field("title")String title,
                           @Field("musicLabel")String musicLabel,@Field("artist")String artist,@Field("country")String country);

    /** 获取全部Music列表 */
    @GET("/music")
    Call<List<MusicFieldInfo>> getMusicAlbum(@Query("musicLabel")String musicLabel);

    /** 新建MusicAlbum */
    @POST("/house/album")
    @FormUrlEncoded
    Call<BaseJson> addHouseAlbum(@Field("title")String title,@Field("imgUrl")String imgUrl,@Field("musicAlbumList") String musicAlbumList);

    /** 获取MusicAlbum首页列表*/
    @GET("/house/album")
    Call<List<MusicAlbumInfo>> getMusicAlbumHouse();
}
