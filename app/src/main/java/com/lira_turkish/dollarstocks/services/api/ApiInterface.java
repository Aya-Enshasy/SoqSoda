package com.lira_turkish.dollarstocks.services.api;


import com.lira_turkish.dollarstocks.closeApp.model.CloseApp;
import com.lira_turkish.dollarstocks.crop.model.CropHistory;
import com.lira_turkish.dollarstocks.expectation.Expectation;
import com.lira_turkish.dollarstocks.feature.currency.fragments.crops.model.Crops1;
import com.lira_turkish.dollarstocks.feature.main.model.GetAds;
import com.lira_turkish.dollarstocks.models.ApiResult;
import com.lira_turkish.dollarstocks.models.CurrencyData;
import com.lira_turkish.dollarstocks.models.History;
import com.lira_turkish.dollarstocks.models.MessageNotification;
import com.lira_turkish.dollarstocks.models.Screen;
import com.lira_turkish.dollarstocks.world.model.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    @POST("get-all")
    Call<ApiResult<ArrayList<CurrencyData>>> getCurrencyData(@Query("type") String type, @Header("Accept-Language")String Accept);

    @POST("update-sp")
    Call<String> updateCurrency(@QueryMap HashMap<String, String> map);

    @POST("get-history")
    Call<ApiResult<ArrayList<History>>> getHistory(@Query("type") String city, @Query("date") String date, @Header("Accept-Language")String Accept);


    @POST("get-def")
    Call<ApiResult<ArrayList<Screen>>> getScreen();

    @POST("https://fcm.googleapis.com/fcm/send")
    Call<Objects> sendFirebaseNotification(@Body MessageNotification notification);


////////////////////////////////////////////////////////////////////////////////////
    @GET("advertisements")
    Call<GetAds> getAdvertisements();

    @GET("crops")
    Call<Crops1> getCropsFromApi(@Header("Accept-Language")String Accept);

    @GET("posts")
    Call<Expectation> getExpectation(@Header("Accept-Language")String Accept);

    @POST("posts")
    Call<String> addExpectation(@Query("content") String content, @Header("Accept-Language")String Accept);

    @POST("crops")
    Call<String> addCropsData(
            @Query("name") String name,
            @Query("price") String price,
            @Query("status") int status,
            @Query("currency") String currency,
            @Header("Accept")String Accept,
            @Header("Accept-Language")String AcceptLanguage
           );

    @POST("crops/update")
    Call<String> updateCrops(
            @Query("name") String name,
            @Query("price") String price,
            @Query("status") int status,
            @Query("currency") String currency,
            @Query("id") int id);

    @GET("get-world")
    Call<World> getWorldCurrencies(@Header("Accept-Language")String AcceptLanguage);

    @POST("posts/delete")
    Call<String> deleteItem(@Query("id") int id);

    @POST("crops/delete")
    Call<String> deleteCrops(@Query("id") int id);

    @POST("posts/update")
    Call<String> updateExpectation( @Query("content") String content, @Query("id") int id);

    @POST("advertisements")
    Call<String> addAdvertisements(@Query("name") String name,@Query("url") String url,@Query("status") int status);

    @POST("crops/history")
        Call<CropHistory> cropHistory(@Query("id") int id);

    @GET("notes")
        Call<CloseApp> closeAPP();

    @POST("notes")
        Call<String> addCloseAPP(@Query("title") String name,@Query("url") String url,@Query("status") int status);

    @POST("notes/delete")
        Call<String> deleteCloseAPP(@Query("id") int id);

    @POST("advertisements/delete")
        Call<String> deleteAdvertisements(@Query("id") int id);

    @POST("notes/update")
        Call<String> updateCloseAPP(@Query("id") int id,@Query("title") String name,@Query("url") String url,@Query("status") int status);

    @POST("advertisements/update")
        Call<String> updateAdvertisements(@Query("id") int id,@Query("name") String name,@Query("url") String url,@Query("status") int status);

}