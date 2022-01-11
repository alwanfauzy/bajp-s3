package com.alwan.bajpsubmission3.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_entities")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo(name = "title")
    var title: String?,

    @ColumnInfo(name = "overview")
    var overview: String?,

    @ColumnInfo(name = "genres")
    var genres: String?,

    @ColumnInfo(name = "voteAverage")
    var voteAverage: Double?,

    @ColumnInfo(name = "posterPath")
    var posterPath: String?,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean?,
)
