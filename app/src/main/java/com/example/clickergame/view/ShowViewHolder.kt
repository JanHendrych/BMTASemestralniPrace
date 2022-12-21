package com.example.clickergame.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clickergame.R

class ShopViewHolder (view: View, var recyclerViewInterface: RecyclerViewInterface): RecyclerView.ViewHolder(view), View.OnClickListener {
     var imageView:ImageView = view.findViewById(R.id.imgViewShop)
     //var imageViewCoins:ImageView = view.findViewById(R.id.imgViewCoinsShop)
     var tittle:TextView = view.findViewById(R.id.tvTitleShopItem)
     var desc:TextView = view.findViewById(R.id.tvDescription)
     var price:TextView = view.findViewById(R.id.tvCostTestShop)

     init {
         view.setOnClickListener(this)
     }

     override fun onClick(p0: View?) {
          if (recyclerViewInterface != null){
               var pos:Int = adapterPosition
               if (pos != RecyclerView.NO_POSITION){
                    recyclerViewInterface.onItemClick(pos)
               }
          }
     }
}