package com.pm.cinefama.api.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Filme(
    val id : Int,
    val name: String,
    val duration: Int,
    val directors: String,
    val actors: String,
    val genre: String,
    val release_date: String,
    val legal_age: Int,
    val theater: Int,
    val schedule: String,
    val users_id : Int,
    val created_at : String,
    val user_name : String
) : Parcelable