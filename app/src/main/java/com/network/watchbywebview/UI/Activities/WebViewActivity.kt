package com.network.watchbywebview.UI.Activities

import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.network.watchbywebview.R
import com.network.watchbywebview.ViewModel.WebViewViewModel
import java.io.ByteArrayInputStream

class WebViewActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val webViewVM : WebViewViewModel by viewModels()
        val extras = intent.extras

        val progressView = findViewById<ProgressBar>(R.id.progressView)
        // WebView Control
        webView = findViewById<WebView>(R.id.webview)
        webView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        webView.isScrollbarFadingEnabled = true
        webView.isLongClickable = true
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        //webView.webViewClient = MyWebViewClient()
        registerForContextMenu(webView)

        // Set WebSettings for a WebView
        val webSettings = webView.settings
        webSettings.setSupportZoom(true)
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false
        webSettings.loadWithOverviewMode = true
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        webSettings.domStorageEnabled = false
        //webSettings.setAppCacheEnabled(true)
        //webSettings.setAppCachePath(application.cacheDir.absolutePath)

//        val url = webViewVM.getUrlSource()
        var url = ""
         if (extras != null) {
             url = extras.getString("source")!!
         }
        println("---------> WebView Url: $url")

        webView.loadUrl(url)

//        val cm = application.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
//        val ani = cm.activeNetworkInfo
//        if (ani != null && ani.isConnected)
//            webSettings.cacheMode = WebSettings.LOAD_DEFAULT
//        else
//            webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

        webSettings.allowFileAccess = false
        webSettings.javaScriptEnabled =
            true                            // Enable this only if you need JavaScript support!
        webSettings.javaScriptCanOpenWindowsAutomatically =
            false       // Enable this only if you want pop-ups!
        webSettings.mediaPlaybackRequiresUserGesture = true

        webView.webViewClient = object : WebViewClient() {

            override fun onPageCommitVisible(view: WebView?, url: String?) {
                progressView.visibility = View.GONE
                super.onPageCommitVisible(view, url)
            }

            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {

                val empty = ByteArrayInputStream("".toByteArray())
                val host = ":::::" + request!!.url.host

                return if (webViewVM.checkHost(host)) {
                    WebResourceResponse("text/plain", "utf-8", empty)
                }else{
                    super.shouldInterceptRequest(view, request)
                }

            }
        }
    }

    private fun destroyWebView() {

        // Make sure you remove the WebView from its parent view before doing anything.
        //mWebContainer.removeAllViews()
        webView.clearHistory()

        // NOTE: clears RAM cache, if you pass true, it will also clear the disk cache.
        // Probably not a great idea to pass true if you have other WebViews still alive.
        webView.clearCache(true)

        // Loading a blank page is optional, but will ensure that the WebView isn't doing anything when you destroy it.
        webView.loadUrl("about:blank")
        webView.onPause()
        webView.removeAllViews()
        //webView.destroyDrawingCache()

        // NOTE: This pauses JavaScript execution for ALL WebViews,
        // do not use if you have other WebViews still alive.
        // If you create another WebView after calling this,
        // make sure to call webView.resumeTimers().
        webView.pauseTimers()

        // NOTE: This can occasionally cause a segfault below API 17 (4.2)
        webView.destroy()

        // Null out the reference so that you don't end up re-using it.
        //webView = null
    }

    override fun onDestroy() {
        destroyWebView()
        super.onDestroy()
    }
}