package com.example.jjl.company

import com.example.jjl.profilecompany.companyResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface companyapiservice {

    @GET("company/{id}")
    suspend fun getCompanybyID( @Path("id") id: String?) : companyResponse

    @Multipart
    @POST("company")
    suspend fun postcompany(
        @Part("id_user") id_user: RequestBody,
        @Part("company_name") company_name: RequestBody,
        @Part("scope") scope: RequestBody,
        @Part("city") city: RequestBody,
        @Part("company_description") company_description: RequestBody,
        @Part("instagram") instagram: RequestBody,
        @Part("position") position: RequestBody,
        @Part("linkedID") linkedID: RequestBody,
        @Part image: MultipartBody.Part
    ): companyAddResponse

}

