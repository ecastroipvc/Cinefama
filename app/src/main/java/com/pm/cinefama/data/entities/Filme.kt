package com.pm.cinefama.data.entities

import android.os.Parcelable
import androidx.room.PrimaryKey
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "Filme")

class Filme (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
) : Parcelable