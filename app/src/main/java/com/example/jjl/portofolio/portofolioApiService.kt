package com.example.jjl.portofolio

import com.example.jjl.home.homeresponse
import com.example.jjl.home.workerModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface portofolioApiService {

    @GET("portofolio/{id}")
    suspend fun getAllPorto(@Path("id") id: String?) : portoresponse
}


