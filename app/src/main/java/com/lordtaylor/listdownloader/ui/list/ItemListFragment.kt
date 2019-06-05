package com.lordtaylor.listdownloader.ui.list


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lordtaylor.listdownloader.data_repository.AppViewModel
import com.lordtaylor.listdownloader.models.Item
import kotlinx.android.synthetic.main.fragment_list.*
import android.view.View.INVISIBLE as INVISIBLE1
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.lordtaylor.listdownloader.R
import com.lordtaylor.listdownloader.databinding.FragmentListBinding


class ItemListFragment : Fragment(), ListContract.View, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var listAdapter: ItemListAdapter
    lateinit var presenter: ListPresenter
    lateinit var recycler: RecyclerView
    lateinit var viewModel:AppViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var binding = DataBindingUtil.inflate<FragmentListBinding>(inflater,R.layout.fragment_list,container,false)
        viewModel=ViewModelProviders.of(this).get(AppViewModel::class.java)
        binding.viewmodel  = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ListPresenter()

        presenter.attach(this, viewModel)
        initView()
    }

    private fun initView() {
        recycler = conteiner_item
        listAdapter = ItemListAdapter(context!!)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = listAdapter
        recycler.layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.fall_down_animation)
        swipe_to_refresh.setOnRefreshListener(this)
        presenter.loadData()
        viewModel.searchText.observe(this, Observer {
            presenter.searchDB(it)
            updateView()
        })
    }

    override fun onRefresh() {
        presenter.loadData()
    }

    override fun updateView() {
        presenter.getViewModel().getItemList().observe(this, object : Observer<List<Item>> {
            override fun onChanged(t: List<Item>?) {
                setUpdateInfo(t!!)
            }
        })
    }

    override fun searchUpdate() {
        presenter.getViewModel().getSearchResult().observe(this,
            Observer<List<Item>> { t -> setUpdateInfo(t!!) })
    }

    private fun setUpdateInfo(search: List<Item>) {
        listAdapter.setItemList(search as ArrayList<Item>)
        swipe_to_refresh.isRefreshing = false
        recycler.scheduleLayoutAnimation()
    }

}
