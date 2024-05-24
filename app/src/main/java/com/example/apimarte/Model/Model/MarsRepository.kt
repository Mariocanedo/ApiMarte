package com.example.apimarte.Model.Model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.apimarte.Model.Model.Local.MarsDao
import com.example.apimarte.Model.Model.Remote.MarsRealState
import com.example.apimarte.Model.Model.Remote.RetrofitClient
import retrofit2.Call
import retrofit2.Response


class MarsRepository( private  val marsDao: MarsDao){


    /***********************FUNCIONES DEL CRUD*********************/


    fun getTerrainsByid(id: Int) :LiveData<MarsRealState>{
        return getTerrainsByid(id)
    }

    // LIST TERRAINS

    val listAllMars : LiveData<List<MarsRealState>> = marsDao.getAllTerrains()



    // INSERT TERRAINS
    suspend fun inserTerrrain(mars:MarsRealState){
        marsDao.insertTerrain(mars)
    }


    // update Terrains

    suspend fun updateTerrain(mars:MarsRealState){
        marsDao.updateTerrain(mars)
    }


    // DELETE ALL
     suspend fun deleteAll(){
         marsDao.deleteAll()
     }

    // TRAER ELEMENTO POR ID

    fun getTerrains(id:Int):LiveData<MarsRealState>{
        return marsDao.getMarsId(id)
    }



    /************PARTE DE LA API RETROFIT************/

    // llamar al metodo conexión

    private val retrofitCliente = RetrofitClient.getRetrofit()

    // hacer referencia al pojo y la repuesta que vamos a recibir

    val dataFromInternet = MutableLiveData<List<MarsRealState>>()


    fun fechtDataMars(): LiveData<List<MarsRealState>> {

        //Log.d("REPO, VIAJE CONFIABLE")

        retrofitCliente.fetchMarsData().enqueue(object : retrofit2.Callback<List<MarsRealState>> {
            override fun onResponse(
                call: Call<List<MarsRealState>>,
                response: Response<List<MarsRealState>>
            ) {
                when (response.code()) {

                    in 200..299 -> dataFromInternet.value = response.body()
                    in 300..399 -> Log.d("Repo", "${response.code()} --${response.errorBody()}")
                    else -> Log.d("E", "${response.code()}--${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<MarsRealState>>, t: Throwable) {
                Log.d("ERROR", "${t.message}")
            }


        })

        return dataFromInternet
    }


    // SIN VIEJA CONFIABLE CON COROUTINES
    suspend fun fetchDataFromInternetCoroutines() {

        try {
            val response = retrofitCliente.fetchMarsDataCoroutines()

            when (response.code()) {

                in 200..299 -> response.body().let {
                    if (it != null) {
                        marsDao.insertTerrains(it)
                        Log.d("TERRENOS", "$it")
                    }
                }
                in 300..399 -> Log.d("Repo", "${response.code()} --${response.errorBody()}")
                else -> Log.d("E", "${response.code()}--${response.errorBody()}")
            }

        } catch (t: Throwable) {
            Log.d("ERROR", "${t.message}")

        }
    }

}






