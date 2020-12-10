package com.example.jjl.home

import com.example.jjl.regis.RegisResponse
import retrofit2.Call
import retrofit2.http.*

val items = mutableListOf<workerModel>()
interface homeapiservice {
    @GET("worker")
    suspend fun getAllWorker(@Query("limit") limit: Int?, @Query("search") search: String?) : homeresponse


}
