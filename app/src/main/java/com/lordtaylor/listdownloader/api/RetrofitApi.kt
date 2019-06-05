package com.lordtaylor.listdownloader.api

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lordtaylor.listdownloader.models.Item
import com.lordtaylor.listdownloader.utils.Constance
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitApi {

    //this is only for demo you should not put links like this !!!!!!!!
    @GET("https://raw.githubusercontent.com/LordTaylor/Jsony/master/items.json")
    fun getDataFromServer(): Deferred<Response<List<Item>>>

    companion object {
        fun create(): RetrofitApi {
            val gson = GsonBuilder()
                    .setLenient()
                    .create()
            val retrofit = retrofit2.Retrofit.Builder()
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(Constance.BASE_URL)
                    .build()
            return retrofit.create(RetrofitApi::class.java)
        }
    }
}