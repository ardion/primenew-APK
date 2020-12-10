package com.example.jjl.regis

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.jjl.BaseActivity
import com.example.jjl.Constant
import com.example.jjl.PreferenceHelper
import com.example.jjl.R
import com.example.jjl.databinding.ActivityRegisterLayoutBinding
import com.example.jjl.login.ApiClient
import com.example.jjl.login.login
import kotlinx.coroutines.*

class registerLayout : BaseActivity() {
    private lateinit var binding: ActivityRegisterLayoutBinding
    lateinit var sharedPref: PreferenceHelper
    private lateinit var coroutineScope: CoroutineScope


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_register_layout
        )
        sharedPref = PreferenceHelper(this)
        initlisteners()
        setSupportActionBar(binding.toolbarreg)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarreg.setNavigationOnClickListener { onBackPressed() }
    }


    override fun initlisteners() {
        binding.btnSignup.setOnClickListener {
            callSignInApi()
        }
    }

    override fun onStart() {
        super.onStart()
        if (sharedPref.getBoolean(Constant.pref_is_signup)) {
            moveIntent()
        }
    }

    private fun moveIntent() {
        startActivity(Intent(this, login::class.java))
        finish()
    }

    private fun saveSession(username: String, pasword: String) {
        sharedPref.put(Constant.pref_is_regUsername, username)
        sharedPref.put(Constant.pref_is_regPasword, pasword)
        sharedPref.put(Constant.pref_is_signup, true)
    }

    private fun showmessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun callSignInApi() {
        binding.progressBar.visibility = View.VISIBLE
        val service = ApiClient.getApiClient(this)?.create(RegisApiService::class.java)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service?.registerRequest(
                        binding.etUsername.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.etPasword.text.toString(),
                        binding.etNumber.text.toString()
                    )
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (response is RegisResponse) {
                binding.progressBar.visibility = View.GONE

                if (response.success) {
                    Log.d("register", "response $response")
                    val intent = Intent(this@registerLayout, login::class.java)
                    intent.putExtra(Constant.EXTRA_EMAIL, "${binding.etEmail.editableText}")
                    intent.putExtra(
                        Constant.EXTRA_PASSWORD,
                        "${binding.etPasword.editableText}"
                    )
                    saveSession(
                        binding.etEmail.editableText.toString(),
                        binding.etPasword.editableText.toString()
                    )
                    sharedPref.put(
                        Constant.PREF_FULL_NAME,
                        binding.etUsername.editableText.toString()
                    )
                    startActivity(intent)
                } else {
                    setErrorDialog("Error Register!", response.message)
                }

            }
        }
    }

    override fun onDestroy() {
        if (!sharedPref.getBoolean(Constant.pref_is_login)) coroutineScope.cancel()
        super.onDestroy()
    }
}