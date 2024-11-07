package com.example.enishop.dao.network

import com.example.enishop.bo.Article
import com.example.enishop.dao.ArticleDao

class ArticleDAONetworkImpl: ArticleDao {
    override fun findById(id: Long): Article? {
        return null
    }

    override fun insert(article: Article): Long {
        return article.id
    }

    override fun getAllArticles(): List<Article> {
        TODO("Not yet implemented")
    }
}