package com.example.enishop.bo

import java.util.Date

data class Article(
    var id: Long,
    var name: String,
    var description: String,
    var price: Double,
    var imageUrl: String,
    var category: String,
    var date: Date
)