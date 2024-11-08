package com.example.enishop.repository

import com.example.enishop.bo.Article
import com.example.enishop.dao.ArticleDao
import com.example.enishop.dao.DAOFactory
import com.example.enishop.dao.DAOType
import com.example.enishop.dao.memory.ArticleDAOMemoryImpl

object ArticleRepository {
    private var articleDao: ArticleDao = DAOFactory.getArticleDao(DAOType.MEMORY)

    fun setDaoType(type: DAOType) {
        articleDao = DAOFactory.getArticleDao(type)
    }

    fun getArticle(id: Long): Article? {
        return articleDao.findById(id)
    }

    fun addArticle(article: Article): Long {
        return articleDao.insert(article)
    }

    fun getArticles(): List<Article>{
        return articleDao.getAllArticles()
    }

    fun getCategories(): List<String>{
        return articleDao.getAllCategorie()

    }
}