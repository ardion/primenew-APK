package com.example.jjl

interface WebViewListener{
    fun onPageStarted()
    fun onPageFinish()
    fun onShouldOverrideUrl(redirectUrl:String)
    fun onProgressChange(progress:Int)
}