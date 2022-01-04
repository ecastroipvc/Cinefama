package com.pm.cinefama.api.dto

import com.pm.cinefama.api.models.Filme

data class FilmeDto(
    val status : String,
    val message : String,
    val filme : Filme
)