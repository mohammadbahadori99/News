package com.example.data_article.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data_article.model.ArticleDTO

@Database(
    entities = [
        ArticleDTO::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {

    abstract fun articleDao(): com.example.data_article.datasource.local.ArticleDao
}
