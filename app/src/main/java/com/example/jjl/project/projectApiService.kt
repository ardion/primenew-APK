package com.example.jjl.project

import retrofit2.http.GET
import retrofit2.http.Path

interface projectapiservice {
    @GET("project/{id}")
    suspend fun getAllProject(
        @Path("id") id: String
    ) : projectresponse


}