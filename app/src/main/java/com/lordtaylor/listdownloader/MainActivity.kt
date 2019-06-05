package com.lordtaylor.listdownloader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lordtaylor.listdownloader.data_repository.AppViewModel
import com.lordtaylor.listdownloader.ui.list.ItemListFragment

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.conteiner_fragment, ItemListFragment()).commit()
    }
}
