package com.pm.cinefama.data.repository

import androidx.lifecycle.LiveData
import com.pm.cinefama.data.dao.FilmeDao
import com.pm.cinefama.data.entities.Filme

class FilmeRepository(private  val filmeDao: FilmeDao) {
    val readAllFilmes : LiveData<List<Filme>> = filmeDao.readAllFilmes()

    suspend fun addFilme(filme: Filme){
        filmeDao.addFilme(filme)
    }

    suspend fun updateFilme(filme: Filme) {
        filmeDao.updateFilme(filme)
    }

    suspend fun deleteFilme(filme: Filme) {
        filmeDao.deleteFilme(filme)
    }
}