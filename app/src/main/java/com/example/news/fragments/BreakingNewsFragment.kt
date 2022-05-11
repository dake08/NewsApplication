package com.example.news.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.R
import com.example.news.adapters.ArticleAdapter
import com.example.news.repository.NewsRepository
import com.example.news.repository.database.ArticleDatabase
import com.example.news.utils.Resource
import com.example.news.utils.shareNews
import com.example.news.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlin.random.Random

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private val viewModel: NewsViewModel by viewModels()
    lateinit var newsAdapter: ArticleAdapter
    val TAG = "BreakingNewsFragment"

    private fun setupRecyclerView() {
        newsAdapter = ArticleAdapter()
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }
        newsAdapter.onSaveClickListener {
            if (it.id == null) {
                it.id = Random.nextInt(0, 1000);
            }
            viewModel.insertArticle(it)
            Snackbar.make(requireView(), "Saved", Snackbar.LENGTH_SHORT).show()
        }

        newsAdapter.onDeleteClickListener {
            viewModel.deleteArticle(it)
            Snackbar.make(requireView(), "Removed", Snackbar.LENGTH_SHORT).show()
        }

        newsAdapter.onShareClickListener {
            shareNews(context, it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setViewModelObserver()
    }

    private fun setViewModelObserver() {
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { newsResponce ->
            when (newsResponce) {
                is Resource.Success -> {
                    shimmerFrameLayout.stopShimmerAnimation()
                    shimmerFrameLayout.visibility = View.GONE
                    newsResponce.data?.let { news ->
                        rvBreakingNews.visibility = View.VISIBLE
                        newsAdapter.differ.submitList(news.articles)
                    }
                }
                is Resource.Error -> {
                    shimmerFrameLayout.visibility = View.GONE
                    newsResponce.message?.let { message ->
                        Log.e(TAG, "Error ::  $message")
                    }
                }
                is Resource.Loading -> {
                    shimmerFrameLayout.startShimmerAnimation()
                }
            }
        })
    }
}

