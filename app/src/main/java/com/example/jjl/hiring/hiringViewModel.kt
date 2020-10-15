package com.example.jjl.hiring

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jjl.project.projectapiservice
import com.example.jjl.project.projectresponse
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import retrofit2.http.Field
import kotlin.coroutines.CoroutineContext

class hiringViewModel : ViewModel(), CoroutineScope {
    private lateinit var servicepost: hiringApiService
    private lateinit var serviceget: projectapiservice
    val progressBarlivedata = MutableLiveData<Boolean>()
    val spinerlivedata = MutableLiveData<List<spinerhiringmodel>>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun sethireapi(service: hiringApiService) {
        this.servicepost = service
    }

    fun setproject(service: projectapiservice) {
        this.serviceget = service
    }

    fun callhiringapi(
        id_project: Int, id_worker: Int,
        message: String,
        price: Int,
        project_job: String
    ) {
        launch {
            progressBarlivedata.value = true
             withContext(Dispatchers.IO) {
                Log.d("test", "call API = ${Thread.currentThread().name}")

                try {
                    servicepost?.postHiring(
                        id_project,
                        id_worker,
                        message,
                        price,
                        project_job
                    )
                } catch (e: Throwable) {
                    Log.e("onError", "onError : " + e.message);
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
//                        Toast.makeText(
//                            applicationContext,
//                            "invalid username/password",
//                            Toast.LENGTH_SHORT
//                        ).show()

                    }
                }

            }
            progressBarlivedata.value = false
        }
    }

    fun listprojectapi(id: String) {
        launch {
            progressBarlivedata.value = true
            val response = withContext(Dispatchers.IO) {
//                Log.d("test", "call API = ${Thread.currentThread().name}")

                try {
                    serviceget?.getAllProject(id)
                } catch (e: Throwable) {
                    Log.e("onError", "onError : " + e.message);
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
//                        Toast.makeText(
//                            applicationContext,
//                            "invalid username/password",
//                            Toast.LENGTH_SHORT
//                        ).show()
                        progressBarlivedata.value = false
                    }

                }

            }
            Log.d("response",(response is projectresponse).toString())
            if (response is projectresponse){
                var list=response.data?.map{
                    spinerhiringmodel(it.id_project.orEmpty(),it.name_project.orEmpty())

                }
                progressBarlivedata.value = false

                spinerlivedata.value=list
            }

        }

    }

}





