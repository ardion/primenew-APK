package com.example.jjl.project

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jjl.Constant
import com.example.jjl.PreferenceHelper
import com.example.jjl.databinding.FragmentProjectBinding
import com.example.jjl.login.ApiClient
import com.example.jjl.portofolio.portoAdabter
import kotlinx.coroutines.*


class ProjectFragment : Fragment() {

    lateinit var binding: FragmentProjectBinding
    lateinit var sharedPref: PreferenceHelper
    private lateinit var coroutineScope: CoroutineScope
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentProjectBinding.inflate(inflater)
        sharedPref= context?.let { PreferenceHelper(it) }!!
        binding.recyclerView.adapter = projectAdabter()
        binding.recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.btnAddProject.setOnClickListener {
            val intent = Intent(activity, AddProjectActivity::class.java)
            startActivityForResult(intent, AddProjectActivity.ADD_WORD_REQUEST_CODE)
        }
        useCoroutineToCallAPI()
        return binding.root
    }



    private fun useCoroutineToCallAPI() {


        val service =
            context?.let { ApiClient.getApiClient(it)?.create(projectapiservice::class.java) }

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        coroutineScope.launch {

            binding.progressBar.visibility = View.VISIBLE
            Log.d("android1", "start : ${Thread.currentThread().name}")

            val response = withContext(Dispatchers.IO) {
                Log.d("android1", "callApi : ${Thread.currentThread().name}")
                try {
                    sharedPref.getString(Constant.PREF_IDCOMPANY)?.let { service?.getAllProject(it) }
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is projectresponse) {
                Toast.makeText(context,"usecorotine",Toast.LENGTH_SHORT).show()
//                Log.d("android1", response.data.toString())
                val list = response.data?.map {
                    projectModel(
                        it.id_project.orEmpty(),
                        it.id_company.orEmpty(),
                        it.name_project.orEmpty(),
                        it.description_project.orEmpty()
                    )
                } ?: listOf()
                Log.d("hhhh", list.toString())

                (binding.recyclerView.adapter as projectAdabter).addList(list)
            } else if (response is Throwable) {
                Log.e("android1", response.message ?: "Error")
            }
            binding.progressBar.visibility = View.GONE
            Log.d("android1", "finish : ${Thread.currentThread().name}")
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == AddProjectActivity.ADD_WORD_REQUEST_CODE ) {
            binding.recyclerView.adapter = projectAdabter()
            binding.recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            useCoroutineToCallAPI()
            Log.d("coba",AddProjectActivity.ADD_WORD_REQUEST_CODE.toString())
        }

    }



//    private fun setUpRecyclerView() {
//        Recycleproject = projectAdabter(arrayListOf(), object : projectAdabter.OnClickViewListener {
//            override fun OnClick(id: String) {
//                Toast.makeText(activity, id, Toast.LENGTH_SHORT).show()
//                val intent = Intent(activity as AppCompatActivity, detailworkerActivity::class.java)
//                intent.putExtra(HomeFragment.ID_WORKER, id)
//                startActivity(intent)
//            }
//        })
//        binding.recyclerView.adapter = RecycleWorker
//        binding.recyclerView.layoutManager =
//            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

}


//class WordListActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityWordListBinding
//    private lateinit var coroutineScope: CoroutineScope
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_word_list)
//        coroutineScope = CoroutineScope(Job() + Dispatchers.IO)
//
//        binding.rvWords.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//        binding.rvWords.adapter = WordListAdapter()
//
//        binding.btnAddWord.setOnClickListener {
//            val intent = Intent(this, AddWordActivity::class.java)
//            startActivityForResult(intent, AddWordActivity.ADD_WORD_REQUEST_CODE)
//        }
//
//        populateList()
//    }
//
//    private fun populateList() {
//        coroutineScope.launch {
//            val wordDao = WordRoomDatabase.getWordDatabase(this@WordListActivity).wordDao()
//            val list = wordDao.getAllWord()
//            withContext(Dispatchers.Main) {
//                (binding.rvWords.adapter as WordListAdapter).addItems(list)
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK && requestCode == AddWordActivity.ADD_WORD_REQUEST_CODE ) {
//            populateList()
//        }
//    }
//
//    override fun onDestroy() {
//        coroutineScope.cancel()
//        super.onDestroy()
//    }
//}