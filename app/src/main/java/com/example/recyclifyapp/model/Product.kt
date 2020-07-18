package com.example.recyclifyapp.model

data class Product(
    val _id: String,
    val _keywords: List<String>,
    val brand_owner: String,
    val categories: String,
    val categories_imported: String,
    val categories_tags: List<String>,
    val code: String,
    val image_front_small_url: String,
    val image_front_url: String,
    val image_small_url: String,
    val image_thumb_url: String,
    val image_url: String,
    val max_imgid: String,
    val selected_images: SelectedImages
)