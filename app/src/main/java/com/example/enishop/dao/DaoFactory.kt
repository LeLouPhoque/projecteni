package com.example.enishop.dao

import com.example.enishop.dao.memory.ArticleDAOMemoryImpl
import com.example.enishop.dao.network.ArticleDAONetworkImpl

object DAOFactory {
    fun getArticleDao(type: DAOType): ArticleDao {
        return when (type) {
            DAOType.MEMORY -> ArticleDAOMemoryImpl()
            DAOType.NETWORK -> ArticleDAONetworkImpl()
        }
    }
}