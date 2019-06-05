package com.lordtaylor.listdownloader.data_repository

import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.*
import com.lordtaylor.listdownloader.models.Item

private const val TAG = "AppViewModel"

class AppViewModel(application: Application) : AndroidViewModel(application) ,Observable{


    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    private var repository: DataRepository
    private var itemList: LiveData<List<Item>>

    @Bindable
    var searchText = MutableLiveData<String>()

    init {
        repository = DataRepository(application.applicationContext)
        repository.initDB()
        repository.initConnection()
        itemList = repository.getItems()
        repository.loadData()
    }

    fun getItemList(): LiveData<List<Item>> {
        Log.d(TAG, "getItemList")
        return itemList
    }

    fun getSearchResult(): LiveData<List<Item>> {
        Log.d(TAG, "getSearchResult")
        return itemList
    }

    fun searchDB(search: String) {
        itemList = repository.getSearchItems(search)
    }

    fun loadData() {
        repository.loadData()
    }
}
