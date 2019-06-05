package com.lordtaylor.listdownloader.data_repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.lordtaylor.listdownloader.api.RetrofitApi
import com.lordtaylor.listdownloader.db.AppDatabase
import com.lordtaylor.listdownloader.models.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


private const val TAG = "DataRepository"

class DataRepository(val context: Context) {

    private lateinit var db: AppDatabase
    private lateinit var api: RetrofitApi


    fun initDB() {
        Log.d(TAG, "initDB")
        if (!::db.isInitialized) {
            db = AppDatabase.getInstance(context)!!
        }
    }

    fun initConnection() {
        Log.d(TAG, "initConnection")
        if (!::api.isInitialized) {
            api = RetrofitApi.create()
        }
    }

    fun loadData() {
        Log.d(TAG, "loadData")
        CoroutineScope(Dispatchers.IO).launch {
            var request = api.getDataFromServer()
            withContext(Dispatchers.Default) {
                try {
                    var response = request.await()
                    if (response.isSuccessful) {
                        withContext(Dispatchers.Default) {
                            db.getItemDao().insertAll(response.body())
                        }
                        Log.d(TAG, "loadData: " + response.message())
                    } else {
                        Log.e(TAG, "loadData: error :" + response.errorBody())
                    }
                } catch (e: Exception) {
                    e.localizedMessage
                    Log.e(TAG, "loadData: error try:" + e.localizedMessage)
                }
            }
        }
    }

    fun getItems(): LiveData<List<Item>> {
        Log.d(TAG, "getItems")
        return db.getItemDao().getAll()
    }
    fun getSearchItems(search:String): LiveData<List<Item>> {
        var searchDao = "%$search%"
        return db.getItemDao().getAll(searchDao)
    }
}

