package com.example.jjl.hiring

import com.example.jjl.detailworker.detailworkerResponse
import retrofit2.http.*

interface hiringApiService {

    @FormUrlEncoded
    @POST("projectman")
    suspend fun postHiring(
        @Field("id_project") id_project:Int?,
        @Field("id_worker") id_worker: Int?,
        @Field("message") message:String?,
        @Field("price") price: Int?,
        @Field("project_job") project_job: String?
    ): hiringResponse
}
