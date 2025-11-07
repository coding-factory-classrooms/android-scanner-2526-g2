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
    var uid: String,
    var scannedTimestamp: String?,
)


val sampleAmiibos = listOf(
    Amiibo("Super Smash Bros","Marth","Fire Emblem","https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_01000000-034e0902.png", "Marth","2014-09-17", "044FB774","2025-11-06 13:43::23"),
    Amiibo("Super Smash Bros","Marth","Fire Emblem","https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_01000000-034e0902.png", "Marth","2014-09-17", "044FB774","2024-09-03 11:12::23"),
    Amiibo("Super Smash Bros","Marth","Fire Emblem","https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_01000000-034e0902.png", "Marth","2014-09-17", "044FB774","2023-02-19 08:12::09"),
    Amiibo("Super Smash Bros","Marth","Fire Emblem","https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_01000000-034e0902.png", "Marth","2014-09-17", "044FB774","2025-04-22 21:23::10"),
)