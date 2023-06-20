package com.example.task

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.task.databinding.ActivityWebViewBinding

class WebView : AppCompatActivity() {

    lateinit var webViewBinding :ActivityWebViewBinding

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webViewBinding = ActivityWebViewBinding.inflate(layoutInflater)
        var view = webViewBinding.root
        setContentView(view)
        supportActionBar?.hide()
        showLoadingDialog()
        webViewBinding.webViewActivity.webViewClient = WebViewClient()
        webViewBinding.webViewActivity.loadUrl("https://www.pacificglobalsolutions.com/about-us.html")
        dismissLoadingDialog()
    }

    override fun onBackPressed() {

        if(webViewBinding.webViewActivity.canGoBack()){
            webViewBinding.webViewActivity.goBack()
        }else{
            super.onBackPressed()
        }
    }

    private fun showLoadingDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    private fun dismissLoadingDialog() {
        progressDialog.dismiss()
    }

}