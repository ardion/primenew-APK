package com.example.jjl.hiring

import com.google.gson.annotations.SerializedName

data class hiringResponse(val success: Boolean, val message: String?, val data: DataResult?) {

    data class DataResult(
        @SerializedName("id_project") val id_project:Int?,
        @SerializedName("id_worker") val id_worker:Int?,
        @SerializedName("message") val message:String?,
        @SerializedName("price") val price:Int?,
        @SerializedName("project_job") val project_job:String?

    )
}

//
//id_project:5
//id_worker:30
//message:aaaa
//price:2334
//project_job:aaaaaa