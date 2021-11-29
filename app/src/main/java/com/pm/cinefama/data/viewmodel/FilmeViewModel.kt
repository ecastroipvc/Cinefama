package com.pm.cinefama.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.pm.cinefama.data.database.FilmeDatabase
import com.pm.cinefama.data.entities.Filme
import com.pm.cinefama.data.repository.FilmeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilmeViewModel(application: Application) : AndroidViewModel(application){
    val readAllFilmes: LiveData<List<Filme>>
    private val repository: FilmeRepository

    init {
        val filmeDao = FilmeDatabase.getDatabase(application).filmeDao()
        repository = FilmeRepository(filmeDao)
        readAllFilmes = repository.readAllFilmes
    }

    fun  addFilme(filme: Filme){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFilme(filme)
        }
    }

    fun updateFilme(filme: Filme) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFilme(filme)
        }
    }

    fun  deleteFilme(filme: Filme) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFilme(filme)
        }
    }
}