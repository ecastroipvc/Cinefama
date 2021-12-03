package com.pm.cinefama.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pm.cinefama.data.entities.Filme

@Dao
interface FilmeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFilme(filme: Filme)

    @Update
    fun updateFilme(filme: Filme)

    @Query("SELECT * FROM filme ORDER BY id DESC")
    fun readAllFilmes(): LiveData<List<Filme>>

    @Delete
    fun deleteFilme(filme: Filme)
}