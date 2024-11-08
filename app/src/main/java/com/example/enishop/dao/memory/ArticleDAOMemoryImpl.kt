package com.example.enishop.dao.memory

import com.example.enishop.bo.Article
import com.example.enishop.dao.ArticleDao
import java.util.Date

class ArticleDAOMemoryImpl : ArticleDao {
    override fun findById(id: Long): Article? {
        return articleInMemory.find { it.id == id }
    }

    override fun insert(article: Article): Long {
        articleInMemory.add(article)
        return article.id
    }

    override fun getAllCategorie(): List<String> {
        return categoryInMemory
    }

    private var categoryInMemory = mutableListOf<String>()

    init{
        categoryInMemory.add("men's clothing")
        categoryInMemory.add("women's clothing")

    }

    override fun getAllArticles(): List<Article> {
        return articleInMemory
    }

    private var articleInMemory = mutableListOf<Article>()

    init {
        articleInMemory.add(Article(
            id = 1,
            name = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
            description = "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
            price = 109.95,
            imageUrl = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
            category = "men's clothing",
            date = Date()
        ))

        articleInMemory.add(Article(
            id = 2,
            name = "Mens Casual Premium Slim Fit T-Shirts",
            description = "Slim-fitting style, contrast raglan long sleeve, three-button henley placket, light weight & soft fabric for breathable and comfortable wearing. And Solid stitched shirts with round neck made for durability and a great fit for casual fashion wear and diehard baseball fans. The Henley style round neckline includes a three-button placket.",
            price = 22.3,
            imageUrl = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
            category = "men's clothing",
            date = Date(),
        ))

        articleInMemory.add(Article(
            id = 3,
            name = "Mens Cotton Jacket",
            description = "great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.",
            price = 55.99,
            imageUrl = "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg",
            category = "women's clothing",
            date = Date(),
        ))
    }
}