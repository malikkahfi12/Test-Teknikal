package com.learetechno.newsapp.ui.webarticles

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.navigation.fragment.navArgs

import com.learetechno.newsapp.R

/**
 * A simple [Fragment] subclass.
 */
class WebArticleFragment : Fragment() {

    val args : WebArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_web_article, container, false)
        val webView : WebView = view.findViewById(R.id.webView)
        val progressBarHorizontal : ProgressBar = view.findViewById(R.id.progress_horizontal)
        webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true

            loadUrl(args.url)
            webViewClient = WebViewClient()
            webChromeClient = object : WebChromeClient(){
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    progressBarHorizontal.setProgress(progress)
                    if(newProgress == 100){
                        progressBarHorizontal.visibility = View.INVISIBLE
                    }
                }

            }
        }
        Log.d("url", args.url)
        return view
    }

}
