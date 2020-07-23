package com.learetechno.newsapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learetechno.newsapp.R
import com.learetechno.newsapp.adapter.ArticleAdapter
import com.learetechno.newsapp.adapter.SourceAdapter
import com.learetechno.newsapp.factory.NewsViewModelFactory
import com.learetechno.newsapp.repository.NewsRepository
import com.learetechno.newsapp.util.Resource

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    lateinit var viewModel : MainViewModel
    lateinit var sourceAdapter : SourceAdapter
    lateinit var articlesAdapter : ArticleAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val searchNews : EditText = view.findViewById(R.id.searchNews)
        setupRecyclerViewSource(view)
        setupRecyclerViewHeadlines(view)
        val newsRepository = NewsRepository()
        val viewModelProviderFactory = NewsViewModelFactory(newsRepository)
        viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory).get(MainViewModel::class.java)
        viewModel.getSourceNews()

        viewModelSource(view)

        viewModelArticle(view)

        onClickItemArticles()

        onClickItemSource()

        searchNews.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }

        return view
    }

    private fun onClickItemSource(){
        sourceAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putString("id", it.id)
                putString("name", it.name)
            }
            findNavController().navigate(R.id.action_mainFragment_to_articleFragment, bundle)
        }
    }

    private fun onClickItemArticles(){
        articlesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
            putString("Url", it.url)
            }
            findNavController().navigate(
                R.id.action_mainFragment_to_webArticleFragment,
                bundle
            )
        }
    }

    private fun viewModelArticle(view : View){
        val layoutLoadingSource : RelativeLayout = view.findViewById(R.id.layoutLoadingSource)
        viewModel.articleNewsHeadlines.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    response.data.let { articleResponse ->
                        layoutLoadingSource.visibility = View.INVISIBLE
                        articlesAdapter.differ.submitList(articleResponse?.articles?.toList())
                    }
                }
                is Resource.Error -> {
                    response.message?.let {
                        Log.e("Error", "Error : $it")
                    }
                }
                is Resource.Loading -> {
                    layoutLoadingSource.visibility = View.VISIBLE
                }
            }

        })
    }

    private fun viewModelSource(view: View) {
        val layoutLoadingHeadline : RelativeLayout = view.findViewById(R.id.layoutLoadingHeadlines)
        viewModel.sourceNews.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    response.data.let { sourceResponse ->
                        layoutLoadingHeadline.visibility = View.INVISIBLE
                        sourceAdapter.differ.submitList(sourceResponse?.sources?.toList())
                    }
                }
                is Resource.Error -> {
                    response.message?.let {
                        Log.e("Error", "Error : $it")
                    }
                }
                is Resource.Loading ->{
                    layoutLoadingHeadline.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setupRecyclerViewHeadlines(view: View) {
        val rvArticle : RecyclerView = view.findViewById(R.id.rvHeadlines)
        articlesAdapter = ArticleAdapter()
        rvArticle.apply {
            adapter = articlesAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupRecyclerViewSource(view : View){
        val rvSource : RecyclerView = view.findViewById(R.id.rvSource)
        sourceAdapter = SourceAdapter()
        rvSource.apply {
            adapter = sourceAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

}
