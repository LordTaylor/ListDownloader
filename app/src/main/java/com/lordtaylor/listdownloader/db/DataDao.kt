package com.lordtaylor.listdownloader.db


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.lordtaylor.listdownloader.models.Item


@Dao
interface DataDao {

    @Query("SELECT * FROM Item WHERE name LIKE :search OR description LIKE :search")
    fun getAll(search:String="%"): LiveData<List<Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Item)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(item: List<Item>?)

    @Query("DELETE from Item")
    fun deleteAll()
}