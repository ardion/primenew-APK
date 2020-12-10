package com.example.jjl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import com.example.jjl.databinding.ActivitySplashBinding


class splashActivity : BaseActivity() {
    private lateinit var binding:ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        var handler=Handler()
        handler.postDelayed({
            val intentt = Intent(this, AppIntroScreen::class.java)
            startActivity(intentt)
            finish()
        }, 3000)

    }
}