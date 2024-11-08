package com.example.enishop.dao

import com.example.enishop.bo.Article

interface ArticleDao {
    fun findById(id: Long): Article?

    fun insert(article: Article): Long

    fun getAllArticles(): List<Article>

    fun getAllCategorie(): List<String>
}