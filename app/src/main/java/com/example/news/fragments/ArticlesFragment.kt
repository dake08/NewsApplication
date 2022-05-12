package com.example.news.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.news.R
import com.example.news.databinding.FragmentArticlesBinding
import com.example.news.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_articles.*

class ArticlesFragment : Fragment() {

    private val viewModel: NewsViewModel by viewModels()
    val args: ArticlesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_articles, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = args.article

        webView.apply {
            webViewClient = object : WebViewClient(){
                override fun onPageCommitVisible(view: WebView?, url: String?) {
                    super.onPageCommitVisible(view, url)
                    progressBar.visibility = View.GONE
                }
            }
            loadUrl(article.url.toString())
        }
    }
}