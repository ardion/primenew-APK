package com.example.jjl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import com.example.jjl.BaseActivity
import com.example.jjl.MainActivity
import com.example.jjl.R
import com.example.jjl.databinding.ActivityLogin2Binding

class login : BaseActivity() {

    private lateinit var binding: ActivityLogin2Binding
    lateinit var sharedPref: PreferenceHelper

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
            if (binding.etEmail.text.isNotEmpty() && binding.etPw.text.isNotEmpty()) {
                saveSession(binding.etEmail.text.toString(),binding.etPw.text.toString())
            }
            if(sharedPref.getString(Constant.pref_is_Username)==sharedPref.getString(Constant.pref_is_regUsername)
                && sharedPref.getString(Constant.pref_is_Pasword)==sharedPref.getString(Constant.pref_is_regPasword)){
                showmessage("Save")
                moveIntent()
            }else{
                showmessage("Check Again Your Pasword and Username")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (sharedPref.getBoolean(Constant.pref_is_login)) {
            moveIntent()
        }
    }

    private fun moveIntent(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun saveSession(username:String,pasword:String){
        sharedPref.put(Constant.pref_is_Username, username)
        sharedPref.put(Constant.pref_is_Pasword, pasword)
        sharedPref.put(Constant.pref_is_login, true)
    }

    private fun showmessage(message:String){
        Toast.makeText(applicationContext,message, Toast.LENGTH_SHORT).show()
    }


}

