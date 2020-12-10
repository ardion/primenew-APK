package com.example.jjl.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil

import com.example.jjl.*
import com.example.jjl.company.companyAddResponse
import com.example.jjl.company.formprofilecompanyActivity
import com.example.jjl.databinding.ActivityLogin2Binding
import com.example.jjl.home.HomeFragment
import com.example.jjl.regis.registerLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class login : BaseActivity() {


    private lateinit var binding: ActivityLogin2Binding
    lateinit var sharedPref: PreferenceHelper
    private lateinit var coroutineScope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login2)
        sharedPref = PreferenceHelper(this)
        initlisteners()

    }


    override fun initlisteners() {
        binding.tvRegis2.setOnClickListener {
            val intentregis = Intent(this, registerLayout::class.java)
            startActivity(intentregis)
        }
        binding.btnLogin.setOnClickListener {
            callSignInApi()

        }
    }

    override fun onStart() {
        super.onStart()
        if (sharedPref.getBoolean(Constant.pref_is_login)) {
            moveIntent()
        }
    }

    private fun moveIntent() {

        startActivity(Intent(this, formprofilecompanyActivity::class.java))
        finish()
    }


    private fun callSignInApi() {
        binding.progressBar.visibility = View.VISIBLE
        val service = ApiClient.getApiClient(this)?.create(AuthApiService::class.java)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        coroutineScope.launch {
            Log.d("test", "login = ${Thread.currentThread().name}")

            val response = withContext(Dispatchers.IO) {
                Log.d("test", "call API = ${Thread.currentThread().name}")

                try {
                    service?.loginRequest(
                        binding.etEmail.text.toString(),
                        binding.etPw.text.toString()
                    )
                } catch (e: Throwable) {
                    Log.e("onError", "onError : " + e.message);
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "invalid username/password",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
            Log.d("test", response.toString())
            if (response is LoginResponse) {
                binding.progressBar.visibility = View.GONE

                if (response.success) {
                    sharedPref.put(Constant.PREF_TOKEN, response.data?.token.toString())
                    sharedPref.put(Constant.pref_is_login, true)
                    sharedPref.put(Constant.PREF_ID, response.data?.id.toString())
                    Log.e("ondata", response.data?.id.toString());
                    Toast.makeText(this@login, response.message, Toast.LENGTH_SHORT).show()
                    moveIntent()

                } else {
                    setErrorDialog("Error Login!", response.message)
                }

            }
        }
    }

    override fun onDestroy() {
        if (!sharedPref.getBoolean(Constant.pref_is_login)) coroutineScope.cancel()
        super.onDestroy()
    }

}

