package com.learetechno.newsapp.ui.article

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager

import com.learetechno.newsapp.R
import com.learetechno.newsapp.adapter.ArticleAdapter
import com.learetechno.newsapp.factory.ArticleViewModelFactory
import com.learetechno.newsapp.repository.NewsRepository
import com.learetechno.newsapp.util.Resource
import kotlinx.android.synthetic.main.fragment_article.*

/**
 * A simple [Fragment] subclass.
 */
class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var viewModel : ArticleViewModel
    lateinit var articleAdapter: ArticleAdapter
    val args : ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsRepository = NewsRepository()
        val viewModelProviderFactory = ArticleViewModelFactory(newsRepository)
        sournameName.text = args.name
        viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory).get(ArticleViewModel::class.java)
        setupRecyclerView()
        viewModel.getSearchResult(args.id)

        viewModel.article.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is  Resource.Success -> {
                    response.data.let {
                        rvArticles.visibility = View.VISIBLE
                        layoutLoadingArticle.visibility = View.INVISIBLE
                        articleAdapter.differ.submitList(it?.articles)
                    }
                }
                is Resource.Error -> {
                   response.message.let {
                       Log.d("Error", "Error $it")
                   }
                }
                is Resource.Loading ->{
                    rvArticles.visibility = View.INVISIBLE
                    layoutLoadingArticle.visibility = View.VISIBLE
                }
            }
        })

        articleAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putString("Url", it.url)
            }

            findNavController().navigate(R.id.action_articleFragment_to_webArticleFragment, bundle)
        }

    }

    private fun setupRecyclerView(){
        articleAdapter = ArticleAdapter()
        rvArticles.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            hasFixedSize()
        }
    }

}
