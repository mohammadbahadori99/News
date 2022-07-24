package com.example.ui_article

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.domain_article.usecase.GetArticleLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    GetArticleLocalUseCase: GetArticleLocalUseCase
) : ViewModel() {
    val article = GetArticleLocalUseCase(savedStateHandle.get<String>(KEY_ARTICLE_ID)?.toInt() ?: 0)
}
