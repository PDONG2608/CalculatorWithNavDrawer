package com.example.navigationdrawer

data class ThemeColor(
    val colorResId: Int,
    val themeKey: String,
    val imagePreview : Int,
    var isSelected: Boolean = false
)