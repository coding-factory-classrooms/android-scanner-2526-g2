package com.example.scanner

data class Amiibo (
    val amiiboSeries: String,
    val character: String,
    val gameSeries: String,
    val image: String,
    val name: String,
    val release: String,
    val uid: String
)

data class AmiiboHistory (
    val gameSeries: String,
    val image: String,
    val name: String,
    val uid: String
)

