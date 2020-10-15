package com.example.jjl.company


import com.google.gson.annotations.SerializedName
import retrofit2.http.Part

data class companyAddResponse(val success: Boolean, val message: String?, val data: company) {

    data class company(
        @SerializedName("id") val id: String?,
        val id_user: String?,
        @SerializedName("company_name") val company_name: String?,
        val scope: String?,
        val city: String?,
        val company_description: String?,
        val instagram: String?,
        val position: String?,
        val linkedID: String?,
        val image: String?
    )
}
//
//@Part("id_user") id_user: RequestBody,
//@Part("company_name") company_name: RequestBody,
//@Part("scope") scope: RequestBody,
//@Part("city") city: RequestBody,
//@Part("company_description") company_description: RequestBody,
//@Part("instagram") instagram: RequestBody,
//@Part("position") position: RequestBody,
//@Part("linkedID") linkedID: RequestBody,
//@Part image: MultipartBody.Part