package com.example.ui_article

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.viewModelScope
import com.example.core.viewModel.BaseViewModel
import com.example.domain_article.usecase.GetArticleListLocalUseCase
import com.example.domain_article.usecase.GetArticleListRemoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    getArticleListLocalUseCase: GetArticleListLocalUseCase,
    private val getArticleListRemoteUseCase: GetArticleListRemoteUseCase
) : BaseViewModel(), DefaultLifecycleObserver {

    private var currentPage = 1

    val articleList = getArticleListLocalUseCase()

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            if (currentPage > 1)
                networkMoreLoading(ArticleRequestTag.GetArticleList.name)
            else networkLoading(ArticleRequestTag.GetArticleList.name)
            observeNetworkState(
                getArticleListRemoteUseCase(page = currentPage),
                ArticleRequestTag.GetArticleList.name
            )
        }
    }

    fun refresh() {
        currentPage = 1
        getData()
    }

    fun getNextPage() {
        currentPage++
        getData()
    }
}
