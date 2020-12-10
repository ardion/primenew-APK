package com.example.jjl.company


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jjl.Constant
import com.example.jjl.PreferenceHelper


import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlin.coroutines.CoroutineContext

class AddCompanyViewModel : ViewModel(), CoroutineScope {

    private lateinit var service: companyapiservice
    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setLoginService(service: companyapiservice) {
        this.service = service
    }

    fun postCompanyApi(
        id_user: RequestBody,
        company_name: RequestBody,
        scope: RequestBody,
        city: RequestBody,
        company_description: RequestBody,
        instagram: RequestBody,
        position: RequestBody,
        linkedID: RequestBody,
        image: MultipartBody.Part
    ) {

        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service?.postcompany(
                        id_user, company_name, scope,
                        city, company_description, instagram, position,
                        linkedID, image
                    )
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is companyAddResponse) {
                Log.d("responid", response.data.id.toString())
            }

        }
    }
}

