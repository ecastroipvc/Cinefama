package com.pm.cinefama.api.requests

import com.pm.cinefama.api.dto.FilmeDto
import com.pm.cinefama.api.models.Filme
import retrofit2.Call
import retrofit2.http.*

interface FilmeApi {
    @GET("filmes/read")
    fun getReports(@Header("Authorization") token: String): Call<List<Filme>>

    @FormUrlEncoded
    @POST("filmes/create")
    fun createReport(
        @Header("Authorization") token: String,
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
    fun updateReport(
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
    fun deleteReport(@Header("Authorization") token: String, @Field("id") id: Int): Call<FilmeDto>
}