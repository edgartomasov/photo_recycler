package com.et.photo_recycler.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val gson: Gson = GsonBuilder().setLenient().create()

    public fun <S> createClient(serviceClass: Class<S>?): S {
        val retrofit: Retrofit ?= provideRetrofit()
        return retrofit!!.create(serviceClass)
    }

    private fun provideRetrofit(): Retrofit? {
        return Retrofit.Builder()
            .baseUrl("https://lovetest.me")
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun provideOkHttpClient(): OkHttpClient? {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor: Interceptor ?= getLoggingInterceptor()
        if (loggingInterceptor != null) {
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    private fun getLoggingInterceptor(): Interceptor? {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }
}