package com.example.asone_android.net;

import com.example.asone_android.Base.BaseJson;
import com.example.asone_android.bean.BaseListJson;
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

    /** 创建一个艺术家 */
    @POST("/artist")
    @FormUrlEncoded
    Call<BaseJson> addArtist(@Field("name")String name,@Field("age")String age,@Field("six")String six,@Field("brief")String brief,
                             @Field("head")String head,@Field("country")String country,@Field("recommend")String recommend);

    /** 获取艺术家列表 */
    @GET("/artist")
    Call<List<BaseListJson>> getArtistList();

    /** 创建国家 */
    @POST("/country")
    @FormUrlEncoded
    Call<BaseJson> addCountry(@Field("name")String name,@Field("banner")String banner);

    /** 获取国家列表 */
    @GET("/country")
    Call<List<BaseListJson>> getCountryList();

    /** 获取声音种类 */
    @GET("/sound")
    Call<List<BaseListJson>> getSoundType();

    /** 新建声音 */
    @POST("/sound")
    @FormUrlEncoded
    Call<BaseJson> creatSoundType(@Field("name")String name,@Field("imgUrl")String imgUrl);
}
