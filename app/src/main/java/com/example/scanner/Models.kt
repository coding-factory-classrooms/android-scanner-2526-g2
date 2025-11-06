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

val sampleAmiibos = listOf(
    AmiiboHistory("Super Smash Bros", "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_01000000-034e0902.png", "Marth","092938"),
    AmiiboHistory("Super Smash Bros", "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_01000000-034e0902.png", "Marth","092938"),
    AmiiboHistory("Super Smash Bros", "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_01000000-034e0902.png", "Marth","092938"),
    AmiiboHistory("Super Smash Bros", "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_01000000-034e0902.png", "Marth","092938"),
)