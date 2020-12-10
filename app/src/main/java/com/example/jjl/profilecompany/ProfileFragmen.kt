package com.example.jjl.profilecompany

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jjl.Constant
import com.example.jjl.PreferenceHelper
import com.example.jjl.company.companyapiservice
import com.example.jjl.databinding.FragmenProfileBinding
import com.example.jjl.detailworker.detailworkerResponse
import com.example.jjl.detailworker.detailworkerapiservice
import com.example.jjl.home.HomeFragment
import com.example.jjl.login.ApiClient
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*

class ProfileFragmen : Fragment() {

    lateinit var binding: FragmenProfileBinding
    lateinit var sharedPref: PreferenceHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmenProfileBinding.inflate(inflater)
        sharedPref = context?.let { PreferenceHelper(it) }!!
        useCoroutineToCallAPI()
        return binding.root

    }

    private fun useCoroutineToCallAPI() {
        val service =
            context?.let { ApiClient.getApiClient(it)?.create(companyapiservice::class.java) }

        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        coroutineScope.launch {
            Log.d("android1", "start : ${Thread.currentThread().name}")

            val response = withContext(Dispatchers.IO) {
                Log.d("android1", "callApi : ${Thread.currentThread().name}")
                try {
                    service?.getCompanybyID(sharedPref.getString(Constant.PREF_ID))
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is companyResponse) {
                sharedPref.put(Constant.PREF_IDCOMPANY, response.data?.id_company.toString())
                sharedPref.getString(Constant.PREF_IDCOMPANY)?.let { Log.d("idcom2", it) }
                binding.etCompanyname.text = response.data?.company_name
                binding.etScope.text = response.data?.scope
                binding.etCity.text = response.data?.city
                binding.etCompanydescription.text = response.data?.company_description
                binding.etInstagram.text = response.data?.instagram
                binding.etPosition.text = response.data?.position
                binding.etLinkedID.text = response.data?.linkedID
                Picasso.get().load("http://35.172.182.122:8080/uploads/" + response.data?.image)
                    .into(binding.imageView)
            } else if (response is Throwable) {
                Log.e("android1", response.message ?: "Error")
            }
        }
    }
}