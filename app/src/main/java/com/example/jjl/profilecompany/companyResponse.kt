package com.example.jjl.profilecompany

import com.google.gson.annotations.SerializedName

data class companyResponse(val success: String?, val message: String?, val data: companyr) {

    data class companyr(
        val id_company: String?,
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