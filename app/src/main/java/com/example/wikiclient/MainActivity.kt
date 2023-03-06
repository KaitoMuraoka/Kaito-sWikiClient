package com.example.wikiclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.wikiclient.databinding.ActivityMainBinding
import com.example.wikiclient.ui.ArticleListAdapter
import com.example.wikiclient.ui.model.Article

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val articleListAdapter = ArticleListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initArticleList()
        updateArticleList()
    }

    // RecycleViewの初期設定
    private fun initArticleList() {
        binding.articleList.apply {
            adapter = articleListAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    // RecycleViewにデータを反映
    private fun updateArticleList() {
        // リストに表示するデータをリスト形式で作成
        val articles: List<Article> = (0..10).map { number ->
            Article(number, "記事$number", "https://s3-ap-northeast-1.amazonaws.com/qiita-image-store/0/155135/0c2db45b0bd4b1aa023f5a7da835b76c2d191bd4/x_large.png?1585895165")
        }
        // リストにデータを流し込む
        articleListAdapter.submitList(articles)
    }
}
