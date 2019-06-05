package com.lordtaylor.listdownloader.ui.list

import com.lordtaylor.listdownloader.data_repository.AppViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListPresenter : ListContract.Presenter {

    lateinit var view: ListContract.View
    private lateinit var viewModel: AppViewModel

    fun attach(view: ListContract.View, viewModel: AppViewModel) {
        this.viewModel = viewModel
        attach(view)
    }

    override fun attach(view: ListContract.View) {
        this.view = view

    }

    override fun loadData() {
        viewModel.loadData()
        view.updateView()
    }

    fun searchDB(search: String) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.searchDB(search)
            withContext(Dispatchers.Main) {
                view.searchUpdate()
            }
        }
    }

    fun getViewModel(): AppViewModel {
        return viewModel
    }

}