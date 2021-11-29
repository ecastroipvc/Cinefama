package com.pm.cinefama.data.entities

import android.os.Parcelable
import androidx.room.PrimaryKey
import androidx.room.Entity
import androidx.room.ColumnInfo
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "Filme")

class Filme (
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(defaultValue = "")
    val name: String,
    val duration: Int,
    val directors: String,
    val actors: String,
    val genre: String,
    val release_date: String,
    val legal_age: Int,
    val theater: Int,
    val schedule: String
) : Parcelable