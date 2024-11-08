package com.example.enishop.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.enishop.bo.Article
import com.example.enishop.repository.ArticleRepository

class ArticleListViewModel(articleRepository: ArticleRepository) : ViewModel(){
    val articles: LiveData<List<Article>> = liveData {
        emit(articleRepository.getArticles())
    }

    val categories: LiveData<List<String>> = liveData {
        emit(articleRepository.getCategories())
    }

    companion object {
        fun provideFactory(articleRepository: ArticleRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ArticleListViewModel(articleRepository) as T
                }
            }
        }
    }