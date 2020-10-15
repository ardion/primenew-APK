package com.example.jjl.experience


import retrofit2.http.GET
import retrofit2.http.Path


interface experienceApiService {

    @GET("experience/{id}")
    suspend fun getAllExperience(@Path("id") id: String?) : experienceresponse


}


