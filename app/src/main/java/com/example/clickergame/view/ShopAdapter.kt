package com.example.clickergame.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clickergame.R
import com.example.clickergame.model.Icons
import com.example.clickergame.model.ShopItemModel
import java.util.*

data class ShopAdapter(var context: Context, var listShop: LinkedList<ShopItemModel>, var recyclerViewInterface: RecyclerViewInterface): RecyclerView.Adapter<ShopViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
       var inflater:LayoutInflater = LayoutInflater.from(context)
        var view:View = inflater.inflate(R.layout.recycler_view_shop_row, parent, false)

      return ShopViewHolder(view, recyclerViewInterface)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        holder.tittle.setText(listShop.get(position).tittle)
        holder.desc.setText((listShop.get(position).description))
        holder.price.setText(listShop.get(position).price.toString())
      holder.imageView.setImageResource(R.drawable.sword)
    }

    override fun getItemCount(): Int {
        return listShop.size
    }




}