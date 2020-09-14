package com.example.jjl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.jjl.BaseActivity
import com.example.jjl.MainActivity
import com.example.jjl.R
import com.example.jjl.databinding.ActivityRegisterLayoutBinding

class registerLayout : BaseActivity() {
    private lateinit var binding:ActivityRegisterLayoutBinding
    lateinit var sharedPref: PreferenceHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_layout)
        sharedPref = PreferenceHelper(this)
        initlisteners()
        setSupportActionBar(binding.toolbarreg)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarreg.setNavigationOnClickListener{onBackPressed()}
    }


    override fun initlisteners() {
        binding.btnSignup.setOnClickListener {
            if (binding.etUsername.text.isNotEmpty() && binding.etPasword.text.isNotEmpty()) {
                saveSession(binding.etUsername.text.toString(),binding.etPasword.text.toString())
                showmessage("Save")
                moveIntent()
            }
        }
    }
    override fun onStart() {
        super.onStart()
        if (sharedPref.getBoolean(Constant.pref_is_signup)) {
            moveIntent()
        }
    }

    private fun moveIntent(){
        startActivity(Intent(this, login::class.java))
        finish()
    }

    private fun saveSession(username:String,pasword:String){
        sharedPref.put(Constant.pref_is_regUsername, username)
        sharedPref.put(Constant.pref_is_regPasword, pasword)
        sharedPref.put(Constant.pref_is_signup, true)
    }

    private fun showmessage(message:String){
        Toast.makeText(applicationContext,message, Toast.LENGTH_SHORT).show()
    }
}