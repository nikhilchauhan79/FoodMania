package com.example.foodrecipes

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_recipe_detail.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var sourceUrlText: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        detail_web_view.getSettings().setAppCacheMaxSize( 10 * 1024 * 1024 ); // 10MB
//        detail_web_view.getSettings().setAppCachePath(context?.getApplicationContext()?.getCacheDir()?.getAbsolutePath() );
//        detail_web_view.getSettings().setAllowFileAccess( true );
//        detail_web_view.getSettings().setAppCacheEnabled( true );
//        detail_web_view.getSettings().setJavaScriptEnabled( true );
//        detail_web_view.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT );
//        detail_web_view.setWebViewClient(WebViewClient())
//
//        detail_web_view.settings.loadWithOverviewMode = true
//        detail_web_view.settings.useWideViewPort = true
//        detail_web_view.settings.domStorageEnabled = true
//        detail_web_view.webViewClient = object : WebViewClient() {
//            override
//            fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
//                handler?.proceed()
//            }
//        }
        sourceUrlText = arguments?.getString("sourceUrl")
        Log.d("url", "onCreateView: "+sourceUrlText)

        val webSettings = detail_web_view.settings
        webSettings.javaScriptEnabled = true
        sourceUrlText?.let { detail_web_view.loadUrl(it) }


//        detail_web_view.loadUrl("https://www.google.co.in/")    }

        val settings = detail_web_view.settings

        // Enable java script in web view
        settings.javaScriptEnabled = true

        // Enable and setup web view cache
        settings.setAppCacheEnabled(true)
        settings.cacheMode = WebSettings.LOAD_DEFAULT


        // Enable zooming in web view
        settings.setSupportZoom(true)
        settings.builtInZoomControls = true
        settings.displayZoomControls = true


        // Zoom web view text
        settings.textZoom = 125


        // Enable disable images in web view
        settings.blockNetworkImage = false
        // Whether the WebView should load image resources
        settings.loadsImagesAutomatically = true


        // More web view settings
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            settings.safeBrowsingEnabled = true  // api 26
        }
        //settings.pluginState = WebSettings.PluginState.ON
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.mediaPlaybackRequiresUserGesture = false


        // More optional settings, you can enable it by yourself
        settings.domStorageEnabled = true
        settings.setSupportMultipleWindows(true)
        settings.loadWithOverviewMode = true
        settings.allowContentAccess = true
        settings.setGeolocationEnabled(true)
        settings.allowUniversalAccessFromFileURLs = true
        settings.allowFileAccess = true

        // WebView settings
        detail_web_view.fitsSystemWindows = true


        /*
            if SDK version is greater of 19 then activate hardware acceleration
            otherwise activate software acceleration
        */
        detail_web_view.setLayerType(View.LAYER_TYPE_HARDWARE, null)

        sourceUrlText?.let { detail_web_view.loadUrl(it) }

        // Set web view client
       detail_web_view.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                // Page loading started
                // Do something

                // Enable disable back forward button

            }

            override fun onPageFinished(view: WebView, url: String) {
                // Page loading finished
                // Display the loaded page title in a toast message

            }
        }


        // Set web view chrome client
        detail_web_view.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
            }
        }



    }


}