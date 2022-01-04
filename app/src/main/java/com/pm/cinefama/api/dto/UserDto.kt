package com.pm.cinefama.api.dto

import com.pm.cinefama.api.models.User

data class UserDto(
    val status : String,
    val message : String,
    val user : List<User>,
    val token : String
)