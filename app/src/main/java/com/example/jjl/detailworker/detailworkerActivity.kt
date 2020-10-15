package com.example.jjl.detailworker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.jjl.R
import com.example.jjl.databinding.ActivityDetailworkerBinding
import com.example.jjl.experience.experienceActivity
import com.example.jjl.hiring.hiringActivity
import com.example.jjl.home.*
import com.example.jjl.login.ApiClient
import com.example.jjl.portofolio.portofolioActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*

lateinit var binding: ActivityDetailworkerBinding

class detailworkerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detailworker)
        useCoroutineToCallAPI()
    binding.btnPortofolio.setOnClickListener {
        val intent = Intent(this, portofolioActivity::class.java)
        startActivity(intent)
    }

        binding.btnExperience.setOnClickListener {
            val intent = Intent(this, experienceActivity::class.java)
            startActivity(intent)
        }

        binding.btnHiring.setOnClickListener {
            val intent = Intent(this, hiringActivity::class.java)
            startActivity(intent)
        }

    }


    private fun useCoroutineToCallAPI() {
//    val retrofit = Retrofit.Builder()
//        .baseUrl("http://dummy.restapiexample.com/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
        val service= ApiClient.getApiClient(this)?.create(detailworkerapiservice::class.java)

        val coroutineScope = CoroutineScope(Job() +Dispatchers.Main)

        coroutineScope.launch {
//            binding.progressBar.visibility = View.VISIBLE
            Log.d("android1", "start : ${Thread.currentThread().name}")

            val response = withContext(Dispatchers.IO) {
                Log.d("android1", "callApi : ${Thread.currentThread().name}")
                try {
                    service?.getAllWorker(intent.getStringExtra(HomeFragment.ID_WORKER))
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is detailworkerResponse) {
                Log.d("android1", response.data.toString())
                binding.tvJobdesk.text=response.data?.jobdesk
                binding.tvJobstatus.text=response.data?.job_status
                binding.tvDeskper.text=response.data?.description_personal
                binding.tvInstagram.text=response.data?.instagram
                binding.tvGithub.text=response.data?.github
                binding.tvGitlab.text=response.data?.gitlab

                Picasso.get().load("http://35.172.182.122:8080/uploads/"+response.data?.image).into(binding.imageView)
//                val list = response.data?.map {
//                    workerdetailModel(
//                        it.id_worker.orEmpty(),
//                        it.name.orEmpty(),
//                        it.image.orEmpty(),
//                        it.domicile.orEmpty(),
//                        it.skill.orEmpty()
//                    )
//                } ?: listOf()



            } else if (response is Throwable) {
                Log.e("android1", response.message ?: "Error")
            }
        }


    }

}



