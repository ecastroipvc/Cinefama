package com.pm.cinefama.api.requests

import com.pm.cinefama.api.dto.FilmeDto
import com.pm.cinefama.api.models.Filme
import retrofit2.Call
import retrofit2.http.*

interface FilmeApi {
    @GET("filmes/read")
    fun getFilmes(@Header("Authorization") token: String): Call<List<Filme>>

    @FormUrlEncoded
    @POST("filmes/create")
    fun createFilme(
        @Header("Authorization") token: String,
        @Field("users_id") users_id: String?,
        @Field("name") name: String,
        @Field("duration") duration: Int,
        @Field("directors") directors: String,
        @Field("actors") actors: String,
        @Field("genre") genre: String,
        @Field("release_date") release_date: String,
        @Field("legal_age") legal_age: Int,
        @Field("theater") theater: Int,
        @Field("schedule") schedule: String
    ): Call<FilmeDto>

    @FormUrlEncoded
    @POST("filmes/update")
    fun updateFilme(
        @Header("Authorization") token: String,
        @Field("id") id: Int,
        @Field("name") name: String,
        @Field("duration") duration: Int,
        @Field("directors") directors: String,
        @Field("actors") actors: String,
        @Field("genre") genre: String,
        @Field("release_date") release_date: String,
        @Field("legal_age") legal_age: Int,
        @Field("theater") theater: Int,
        @Field("schedule") schedule: String
    ): Call<FilmeDto>

    @FormUrlEncoded
    @POST("filmes/delete")
    fun deleteFilme(@Header("Authorization") token: String, @Field("id") id: Int): Call<FilmeDto>
}