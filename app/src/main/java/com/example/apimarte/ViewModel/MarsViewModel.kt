package com.example.apimarte.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.apimarte.Model.Model.Local.MarsDataBase
import com.example.apimarte.Model.Model.MarsRepository
import com.example.apimarte.Model.Model.Remote.MarsRealState
import kotlinx.coroutines.launch

class MarsViewModel(application: Application): AndroidViewModel(application) {


    ////PARTE 1////////////////////
    private var repository: MarsRepository

    // varibale que representa la repuesta de la api

    val liveDatafromInternet: LiveData<List<MarsRealState>>

    // Para mostrar lo que estamos recibiendo
    val allMars: LiveData<List<MarsRealState>>


    init {

        val MarsDao = MarsDataBase.getDataBase(application).getMarsDao()
        //FORMA ANTIGUA
        repository = MarsRepository(MarsDao)

        // FORMA ANTIGUA
        //liveDatafromInternet = repository.fechtDataMars()
        viewModelScope.launch {
            repository.fetchDataFromInternetCoroutines()
        }

        allMars = repository.listAllMars
        liveDatafromInternet = repository.dataFromInternet

        liveDatafromInternet.observeForever{data ->
            Log.d("MarsViewModel", "DATA RECEIVED IN VIEWMODEL:$data")
        }

    }


    // FUNCION PARA SELECCIONAR
    private var selectedMarsTerrains : MutableLiveData<MarsRealState> = MutableLiveData()

    // CREAMOS FUNCOIN PARA SELECCIONAR

    fun selected(mars:MarsRealState)
    {
        selectedMarsTerrains.value =mars
    }

    fun selectedItem():LiveData<MarsRealState> = selectedMarsTerrains


    // INSERT
    fun insertMars( mars: MarsRealState)= viewModelScope.launch {
        repository.inserTerrrain(mars)
    }

    // UPDATE
    fun updateMars(mars: MarsRealState) = viewModelScope.launch {
        repository.updateTerrain(mars)
    }

    // GET FOR ID

    fun getMarsById(id:Int):LiveData<MarsRealState>{
        return repository.getTerrainsByid(id)
    }









}



