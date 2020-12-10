package com.example.jjl.login

import android.content.Context
import android.util.Log
import com.example.jjl.Constant
import com.example.jjl.PreferenceHelper
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(val mContext: Context) : Interceptor {
    private lateinit var sharedPref: PreferenceHelper
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        sharedPref = PreferenceHelper(mContext)
        val token = sharedPref.getString(Constant.PREF_TOKEN)
        Log.d("test", "token = $token")
        proceed(
            request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        )
    }
}
