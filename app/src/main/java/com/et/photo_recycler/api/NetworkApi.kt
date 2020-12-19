package com.et.photo_recycler.api

import com.et.photo_recycler.model.PhotoModel
import io.reactivex.Single
import retrofit2.http.GET

interface NetworkApi {

    @GET("/a_test_1/test_app.json")
    fun getPhoto(): Single<List<PhotoModel>>
}