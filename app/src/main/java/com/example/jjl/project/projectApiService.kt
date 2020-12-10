package com.example.jjl.project

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface projectapiservice {
    @GET("project/{id}")
    suspend fun getAllProject(
        @Path("id") id: String
    ): projectresponse


    @Multipart
    @POST("project")
    suspend fun postProject(
        @Part("id_company") id_company: RequestBody,
        @Part("name_project") name_project: RequestBody,
        @Part("description_project") description_project: RequestBody,
        @Part image: MultipartBody.Part
    ): ProjectAddResponse
}