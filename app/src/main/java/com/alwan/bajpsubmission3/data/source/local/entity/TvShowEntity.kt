package com.alwan.bajpsubmission3.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_show_entities")
data class TvShowEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo(name = "name")
    var name: String?,

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
