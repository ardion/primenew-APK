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
