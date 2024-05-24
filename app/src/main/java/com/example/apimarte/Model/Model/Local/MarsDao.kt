package com.example.apimarte.Model.Model.Local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.apimarte.Model.Model.Remote.MarsRealState


@Dao
interface MarsDao {

    // INSERT ONE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTerrain(mars: MarsRealState)

 // INSERT LIST
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTerrains(mars:List<MarsRealState>)


    @Update
    suspend fun updateTerrain(mars: MarsRealState)

    // DELETE ONE
    @Delete
    suspend fun deleteTerrain(mars: MarsRealState)

    @Query("DELETE FROM mars_table")
    suspend fun deleteAll()


    // TRAER TODOS LOS TERRENOS
    @Query("SELECT * FROM mars_table ORDER BY id DESC")
    fun getAllTerrains():LiveData<List<MarsRealState>>


    @Query("SELECT * FROM mars_table WHERE type=:type Limit 1")
    fun getMarsType(type:String):LiveData<MarsRealState>


    // SELECT TERRAIN ID
    @Query("SELECT * FROM mars_table WHERE id=:id")
    fun getMarsId(id:Int):LiveData<MarsRealState>



}