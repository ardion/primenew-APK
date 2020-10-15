package com.example.jjl.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.provider.Settings.Secure.getString
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jjl.Constant
import com.example.jjl.PreferenceHelper
import com.example.jjl.databinding.FragmenHomeBinding
import com.example.jjl.detailworker.binding
import com.example.jjl.detailworker.detailworkerActivity
import com.example.jjl.home.HomeFragment.Companion.ID_WORKER
import com.example.jjl.login.ApiClient
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {

    lateinit var binding: FragmenHomeBinding
    lateinit var sharedPref: PreferenceHelper

    private lateinit var coroutineScope: CoroutineScope

    companion object {
        const val ID_WORKER = "anjay"
    }

    private lateinit var RecycleWorker: homeaAdabter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmenHomeBinding.inflate(inflater)
        sharedPref= context?.let { PreferenceHelper(it) }!!
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
//        useCoroutineToCallAPI("")


        binding.etSearch.addTextChangedListener(object : TextWatcher {
            private var searchFor = ""

            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val searchText = s.toString().trim()
                if (searchText == searchFor)
                    return

                searchFor = searchText
                val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
                coroutineScope.launch {
                    delay(300)  //debounce timeOut
                    if (searchText != searchFor)
                        return@launch

                    useCoroutineToCallAPI(searchFor)
                }
            }
        })

        useCoroutineToCallAPI("")

        setUpRecyclerView()

        return binding.root

    }


    private fun useCoroutineToCallAPI(search:String) {
//        binding.progressBar.visibility = View.VISIBLE
        val service =
            context?.let { ApiClient.getApiClient(it)?.create(homeapiservice::class.java) }

        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        coroutineScope.launch {

            binding.progressBar.visibility = View.VISIBLE
            Log.d("android1", "start : ${Thread.currentThread().name}")

            val response = withContext(Dispatchers.IO) {
                Log.d("android1", "callApi : ${Thread.currentThread().name}")
                try {
                    service?.getAllWorker(100,search)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is homeresponse) {
                Log.d("android1", response.data.toString())
                val list = response.data?.map {
                    workerModel(
                        it.id_worker.orEmpty(),
                        it.name.orEmpty(),
                        it.image.orEmpty(),
                        it.domicile.orEmpty(),
                        it.skill.orEmpty()
                    )
                } ?: listOf()

                (binding.recyclerView.adapter as homeaAdabter).addList(list)
            } else if (response is Throwable) {
                Log.e("android1", response.message ?: "Error")
            }
            binding.progressBar.visibility = View.GONE
            Log.d("android1", "finish : ${Thread.currentThread().name}")
        }


    }

    private fun setUpRecyclerView() {
        RecycleWorker = homeaAdabter(arrayListOf(), object : homeaAdabter.OnClickViewListener {
            override fun OnClick(id: String) {
                Toast.makeText(activity, id, Toast.LENGTH_SHORT).show()
                sharedPref.put(Constant.PREF_IDWORKER, id)
                val intent = Intent(activity as AppCompatActivity, detailworkerActivity::class.java)
                intent.putExtra(ID_WORKER, id)
                startActivity(intent)
            }
        })
        binding.recyclerView.adapter = RecycleWorker
        binding.recyclerView.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)


    }


}