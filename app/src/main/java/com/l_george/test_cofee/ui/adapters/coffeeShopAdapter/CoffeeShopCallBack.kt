package com.l_george.test_cofee.ui.adapters.coffeeShopAdapter

import androidx.recyclerview.widget.DiffUtil
import com.l_george.test_cofee.data.models.CoffeeShopModel

class CoffeeShopCallBack : DiffUtil.ItemCallback<CoffeeShopModel>() {
    override fun areItemsTheSame(oldItem: CoffeeShopModel, newItem: CoffeeShopModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CoffeeShopModel, newItem: CoffeeShopModel): Boolean =
        oldItem == newItem
}