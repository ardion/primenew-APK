package com.example.jjl.project

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlin.coroutines.CoroutineContext

class AddProjectViewModel : ViewModel(), CoroutineScope {

    private lateinit var service: projectapiservice

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main


    fun setLoginService(service: projectapiservice) {
        this.service = service
    }

    fun postProjectApi(id_company: RequestBody, name_project: RequestBody, description_project: RequestBody, image: MultipartBody.Part) {
        launch {

            val response = withContext(Dispatchers.IO) {
                try {
                    service?.postProject(id_company,name_project, description_project , image)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is ProjectAddResponse) {
                // Action Success
            }
        }
    }
}