package com.pm.cinefama.api.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Filme(
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