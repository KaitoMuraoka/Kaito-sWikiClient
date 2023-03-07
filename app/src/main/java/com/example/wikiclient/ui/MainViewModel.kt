package com.example.wikiclient.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wikiclient.data.WikiRepository
import com.example.wikiclient.data.WikiRepositoryImpl
import com.example.wikiclient.data.model.GetArticlesResponse
import com.example.wikiclient.ui.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: WikiRepository = WikiRepositoryImpl()
) : ViewModel() {
    // 取得したデータを別のクラスに通知するためのクラス
    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles

    // Repositoryからデータ取得のメソッドを呼び出す
    fun fetchArticles(keyword: String, limit: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                repository.getArticle(keyword, limit)
            }.onSuccess { response ->
                // データの取得に成功
                if (response != null) postArticles(response)
            }.onFailure {
                // データの取得に失敗
                Log.e("Fetch Failure", it.toString())
            }
        }
    }

    // 取得したデータをMainActivityに追加する
    private fun postArticles(response: GetArticlesResponse) {
        _articles.value = response.query.pages.map {
            Article(
                id = it.index,
                title = it.title,
                thumbnailUrl = it.thumbnail?.source ?: "",
            )
        }
    }
}
