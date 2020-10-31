package com.example.jjl.project

import com.google.gson.annotations.SerializedName

data class ProjectAddResponse(val success: String?, val message: String?, val data: Project) {

    data class Project(
        @SerializedName("id_company") val id_company: String?,
        @SerializedName("name_project") val name_project: String?,
        @SerializedName("description_project") val description_project: String?,
        val image: String?

    )
}
