package com.example.jjl.detailworker

import com.example.jjl.home.homeresponse
import retrofit2.http.GET
import retrofit2.http.Path

interface detailworkerapiservice {
    @GET("worker/{id}")
    suspend fun getAllWorker( @Path("id") id: String?): detailworkerResponse
}

