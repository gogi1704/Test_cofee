package com.l_george.test_cofee.ui.adapters.coffeeShopAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.l_george.test_cofee.data.models.CoffeeShopModel
import com.l_george.test_cofee.databinding.ItemCoffeeLayoutBinding

interface CoffeeShopClickListener {
    fun openCoffeeShop(coffeeShopId: Int)
}

class CoffeeShopAdapter(private val listener: CoffeeShopClickListener) :
    ListAdapter<CoffeeShopModel, CoffeeShopAdapter.CoffeeShopViewHolder>(CoffeeShopCallBack()) {

    class CoffeeShopViewHolder(
        private val binding: ItemCoffeeLayoutBinding,
        private val listener: CoffeeShopClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CoffeeShopModel) {
            with(binding) {
                textName.text = item.name
                cardItem.setOnClickListener {
                    listener.openCoffeeShop(item.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeShopViewHolder {
        val binding =
            ItemCoffeeLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoffeeShopViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: CoffeeShopViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}