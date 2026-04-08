package com.example.myapplication

data class CategoryItem(
    val categoryName: String,
    val categoryImage: Int,
    val subItems: List<String>,
    var isExpanded: Boolean = false
)