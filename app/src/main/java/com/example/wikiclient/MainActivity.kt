package com.example.wikiclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wikiclient.databinding.ActivityMainBinding
import com.example.wikiclient.ui.ArticleListAdapter
import com.example.wikiclient.ui.MainViewModel
import com.example.wikiclient.ui.model.Article
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val articleListAdapter = ArticleListAdapter()
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initArticleList()
        observeArticles()

        // Web APIからのデータを取得開始("Android"というキーワードで20件取得)
        viewModel.fetchArticles("Android", 20)
    }

    // RecycleViewの初期設定
    private fun initArticleList() {
        binding.articleList.apply {
            adapter = articleListAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeArticles() {
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.articles.collect{ articles ->
                    // WEB APIからのデータ取得に成功すると、ここに流れてくる
                    updateArticleList(articles)
                }
            }
        }
    }

    // データをUIに反映
    private fun updateArticleList(articles: List<Article>) {
        // リストにデータを流し込む
        articleListAdapter.submitList(articles)
    }

}
