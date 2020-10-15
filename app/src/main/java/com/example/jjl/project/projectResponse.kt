package com.example.jjl.project

import com.google.gson.annotations.SerializedName


data class projectresponse (val success: Boolean, val message: String?, val data: List<project>) {

    data class project(
        @SerializedName("id_project") val id_project: String?,
        @SerializedName("id_company") val id_company: String?,
        @SerializedName("name_project") val name_project: String?,
        @SerializedName("description_project") val description_project: String?
    )

}