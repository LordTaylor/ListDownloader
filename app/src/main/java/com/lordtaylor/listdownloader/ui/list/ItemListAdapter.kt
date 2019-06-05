package com.lordtaylor.listdownloader.ui.list

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lordtaylor.listdownloader.R
import com.lordtaylor.listdownloader.models.Item
import kotlin.collections.ArrayList
import com.squareup.picasso.Picasso


class ItemListAdapter(val context: Context) : RecyclerView.Adapter<ItemListAdapter.SDViewHolder>() {

    var itemList: List<Item> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SDViewHolder {
        return SDViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: SDViewHolder, position: Int) {
        holder.onBind(itemList.get(position))
    }

    fun setItemList(list: ArrayList<Item>) {
        itemList = list
        notifyDataSetChanged()
    }

    class SDViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var itemImg: ImageView = view.findViewById(R.id.item_avatar)
        var itemTitle: TextView = view.findViewById(R.id.item_title)
        var itemDescription: TextView = view.findViewById(R.id.item_description)

        fun onBind(item: Item) {
            itemTitle.text = item.name
            itemDescription.text = item.description
            if (item.icon.isNotBlank()) {
                Picasso.get().load(item.icon).into(itemImg)
            } else {
                Picasso.get().load(R.drawable.ic_android_mock).into(itemImg)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                itemImg.clipToOutline = true
            }
        }
    }

}