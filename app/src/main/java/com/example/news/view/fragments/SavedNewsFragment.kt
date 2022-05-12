package com.example.news.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.utils.Resource
import com.example.news.view.adapters.ArticleAdapter
import com.example.news.view.adapters.SavedArticlesAdapter
import com.example.news.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_saved_news.*
import kotlin.random.Random

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    private val viewModel: NewsViewModel by viewModels()
    lateinit var newsAdapter: SavedArticlesAdapter
    val TAG = "SavedNewsFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_news, container, false)
    }
    private fun setupRecyclerView() {
        newsAdapter = SavedArticlesAdapter()
        rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment,
                bundle
            )
        }

        val onItemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        )
        {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)

                Snackbar.make(requireView(),"Deleted successfully", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.insertArticle(article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(onItemTouchHelperCallback).apply {
            attachToRecyclerView(rvSavedNews)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setViewModelObserver()
    }

    private fun setViewModelObserver() {
        viewModel.getSavedArticles().observe(viewLifecycleOwner,Observer{
            Log.i(TAG,""+it.size)
            newsAdapter.differ.submitList(it)
            rvSavedNews.visibility = View.VISIBLE
            shimmerFrameLayout2.stopShimmerAnimation()
            shimmerFrameLayout2.visibility = View.GONE
        })
    }
}