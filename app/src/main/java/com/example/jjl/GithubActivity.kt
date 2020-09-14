package com.example.jjl

import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.jjl.databinding.ActivityGithubBinding

class GithubActivity : AppCompatActivity(),WebViewListener {

    private lateinit var binding: ActivityGithubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_github)
        binding.webView.loadUrl("https://github.com/ardion")


        binding.webView.webChromeClient=MojokChromeClient(this)
        binding.webView.webViewClient=MojokWebClient(this)
    }


    class MojokChromeClient(private val listener: WebViewListener): WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            listener.onProgressChange(newProgress)
            super.onProgressChanged(view, newProgress)
        }
    }



    class MojokWebClient(private val listener: WebViewListener): WebViewClient(){
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            listener.onPageStarted()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            listener.onPageFinish()
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return super.shouldOverrideUrlLoading(view, url)
            listener.onShouldOverrideUrl(url.orEmpty())
        }

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
                listener.onShouldOverrideUrl(request?.url?.toString().orEmpty())
            }
            return super.shouldOverrideUrlLoading(view, request)
        }


    }







    override fun onPageStarted() {
        binding.progressBar.visibility= View.VISIBLE
        Toast.makeText(this, "Page Started", Toast.LENGTH_SHORT).show()
    }

    override fun onPageFinish() {
        binding.progressBar.visibility= View.GONE
    }

    override fun onShouldOverrideUrl(redirectUrl: String) {
        Toast.makeText(this, "Redirect Url:$redirectUrl", Toast.LENGTH_SHORT).show()
    }

    override fun onProgressChange(progress: Int) {
        binding.progressBar.progress=progress
    }

}

