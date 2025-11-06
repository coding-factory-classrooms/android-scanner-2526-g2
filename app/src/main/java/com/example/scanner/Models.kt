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

val sampleAmiibos = listOf(
    Amiibo("Super Smash Bros","Marth","Fire Emblem","https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_01000000-034e0902.png", "Marth","2014-09-17", "044FB774"),
    Amiibo("Super Smash Bros","Marth","Fire Emblem","https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_01000000-034e0902.png", "Marth","2014-09-17", "044FB774"),
    Amiibo("Super Smash Bros","Marth","Fire Emblem","https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_01000000-034e0902.png", "Marth","2014-09-17", "044FB774"),
    Amiibo("Super Smash Bros","Marth","Fire Emblem","https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_01000000-034e0902.png", "Marth","2014-09-17", "044FB774"),
)