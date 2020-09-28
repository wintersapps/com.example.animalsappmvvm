package com.examples.animalsappmvvm.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AnimalApi {

    @GET("getKey")
    Single<ApiKeyModel> getApiKey();

    @FormUrlEncoded
    @POST("getAnimals")
    Single<List<AnimalModel>> getAnimals(@Field("key") String key);
}
