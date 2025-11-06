package com.example.scanner

import java.sql.Timestamp
import java.time.LocalDateTime

data class Amiibo (
    val amiiboSeries: String,
    val character: String,
    val gameSeries: String,
    val image: String,
    val name: String,
    val release: String,
    val uid: String,
    var scannedTimestamp: LocalDateTime = LocalDateTime.now(),
)


val sampleAmiibos = listOf(
    Amiibo("Super Smash Bros","Marth","Fire Emblem","https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_01000000-034e0902.png", "Marth","2014-09-17", "044FB774", ),
    Amiibo("Super Smash Bros","Marth","Fire Emblem","https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_01000000-034e0902.png", "Marth","2014-09-17", "044FB774"),
    Amiibo("Super Smash Bros","Marth","Fire Emblem","https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_01000000-034e0902.png", "Marth","2014-09-17", "044FB774"),
    Amiibo("Super Smash Bros","Marth","Fire Emblem","https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_01000000-034e0902.png", "Marth","2014-09-17", "044FB774"),
)