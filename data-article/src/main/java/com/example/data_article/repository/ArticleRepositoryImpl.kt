package com.example.data_article.repository


import com.example.core.ApiResult
import com.example.core.Exceptions
import com.example.core.NetworkHandler


import com.example.data_article.datasource.local.ArticleLocalDataSource
import com.example.data_article.datasource.remote.ArticleRemoteDataSource
import com.example.domain_article.repository.ArticleRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepositoryImpl @Inject constructor(
    private val articleLocalDataSource: ArticleLocalDataSource,
    private val articleRemoteDataSource: ArticleRemoteDataSource,
    private val networkHandler: NetworkHandler
) : ArticleRepository {
    override fun getArticleListLocal() = articleLocalDataSource.getArticleList()

    override fun getArticleLocal(articleId: Int) = articleLocalDataSource.getArticle(articleId)

    override suspend fun getArticleListRemote(page: Int): ApiResult<Unit> {
        return if (networkHandler.hasNetworkConnection()) {
            when (val result = articleRemoteDataSource.getArticleList(page = page)) {
                is ApiResult.Success -> {
                    ApiResult.Success(
                        articleLocalDataSource.insertArticleList(
                            page = page,
                            articleList = result.data.articles
                        )
                    )
                }
                is ApiResult.Error -> ApiResult.Error(result.exception)
                else -> {
                    ApiResult.Error(Exceptions.NetworkConnectionException())
                }
            }
        } else ApiResult.Error(Exceptions.NetworkConnectionException())
    }
}
