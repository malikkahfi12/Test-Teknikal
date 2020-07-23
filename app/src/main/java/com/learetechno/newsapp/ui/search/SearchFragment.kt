package com.learetechno.newsapp.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.learetechno.newsapp.R
import com.learetechno.newsapp.adapter.ArticleAdapter
import com.learetechno.newsapp.factory.SearchViewModelFactory
import com.learetechno.newsapp.repository.NewsRepository
import com.learetechno.newsapp.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    lateinit var viewModel : SearchViewModel
    lateinit var articleAdapter: ArticleAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val imgClose : ImageView = view.findViewById(R.id.imgClose)
        val edtSearch : EditText = view.findViewById(R.id.edtSearchNews)
        setupRecyclerViewHeadlines(view)
        val newsRepository = NewsRepository()
        val viewModelProviderFactory = SearchViewModelFactory(newsRepository)

        viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory).get(SearchViewModel::class.java)
        var job : Job? = null
        edtSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(1000L)
                if (editable.toString().isNotEmpty())
                    viewModel.getSearchResult(editable.toString())
            }
        }

        articleAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putString("Url", it.url)
            }
            findNavController().navigate(
                R.id.action_searchFragment_to_webArticleFragment,
                bundle
            )
        }

        viewModelSearch(view)
        imgClose.setOnClickListener {
            findNavController().navigateUp()
        }
        return  view
    }

    private fun viewModelSearch(view : View) {
        val rvArticle : RecyclerView = view.findViewById(R.id.rvSearch)
        val layoutLoadingSearch : RelativeLayout = view.findViewById(R.id.layoutLoadinSearch)
        viewModel.articleSearch.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    response.data.let {
                        rvArticle.visibility = View.VISIBLE
                        layoutLoadingSearch.visibility = View.INVISIBLE
                        articleAdapter.differ.submitList(it?.articles)
                    }
                }
                is Resource.Error -> {
                    response.message?.let {
                        Log.e("Error", "Error :  $it")
                    }
                }
                is Resource.Loading -> {
                    rvArticle.visibility = View.INVISIBLE
                    layoutLoadingSearch.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setupRecyclerViewHeadlines(view: View) {
        val rvArticle : RecyclerView = view.findViewById(R.id.rvSearch)
        articleAdapter = ArticleAdapter()
        articleAdapter.notifyDataSetChanged()
        rvArticle.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            hasFixedSize()
        }

    }

}
