package com.lordtaylor.listdownloader.ui.list

import com.lordtaylor.listdownloader.ui.base.BaseContract

interface ListContract : BaseContract {
    interface View: BaseContract.View{
        fun updateView()
        fun searchUpdate()
    }
    interface Presenter: BaseContract.Presenter<View>{
        fun loadData()
    }

}