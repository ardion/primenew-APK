package com.example.jjl.hiring

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.jjl.Constant
import com.example.jjl.PreferenceHelper
import com.example.jjl.R
import com.example.jjl.databinding.ActivityDetailworkerBinding
import com.example.jjl.databinding.ActivityHiringBinding
import com.example.jjl.login.ApiClient
import com.example.jjl.login.AuthApiService
import com.example.jjl.project.projectapiservice
import java.util.*


class hiringActivity : AppCompatActivity() {
    lateinit var binding: ActivityHiringBinding
    lateinit var viewModel: hiringViewModel
    lateinit var sharedPref: PreferenceHelper
    var selectedSpiner: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = PreferenceHelper(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hiring)
        viewModel = ViewModelProvider(this).get(hiringViewModel::class.java)
        val servicepost = ApiClient.getApiClient(this)?.create(hiringApiService::class.java)
        val servicelist = ApiClient.getApiClient(this)?.create(projectapiservice::class.java)

        if (servicepost != null) {
            viewModel.sethireapi(service = servicepost)
        }

        if (servicelist != null) {
            viewModel.setproject(service = servicelist)
        }

        subscribeLiveData()
        val aa = sharedPref.getString(Constant.PREF_IDCOMPANY)
        if (aa != null) {
            Log.d("hiringcoba", aa)
        }
        if (aa != null) {
            viewModel.listprojectapi(aa)
        }
        val bb = sharedPref.getString(Constant.PREF_IDWORKER)
        if (bb != null) {
            Log.d("hiringcoba1", bb)
        }
        binding.btnHiring.setOnClickListener {
            Log.d("hiringcoba1", selectedSpiner.toString())
            sharedPref.getString(Constant.PREF_IDWORKER)?.toInt()?.let { it1 ->
                viewModel.callhiringapi(
                    selectedSpiner,
                    it1,
                    binding.tvMessage.text.toString(),
                    binding.tvPrice.text.toString().toInt(),
                    binding.tvProjectjob.text.toString()
                )
            }
            Toast.makeText(
                applicationContext,
                "success in",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun subscribeLiveData() {
        viewModel.progressBarlivedata.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
        viewModel.spinerlivedata.observe(this, Observer {
            var spiner = binding.spinerhiring
            spiner.adapter = ArrayAdapter<String>(
                this, R.layout.support_simple_spinner_dropdown_item, it.map {
                    it.name_project
                }
            )
            spiner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedSpiner = it[position].id_project.toInt()
                    (parent!!.getChildAt(0) as TextView).setTextColor(Color.GRAY)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        })
    }
}