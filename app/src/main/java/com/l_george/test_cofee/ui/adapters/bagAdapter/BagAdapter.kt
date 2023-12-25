package com.l_george.test_cofee.ui.adapters.bagAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.l_george.test_cofee.data.models.MenuModel
import com.l_george.test_cofee.databinding.ItemBagLayoutBinding
import com.l_george.test_cofee.ui.adapters.menuAdapter.MenuClickListener

class BagAdapter(private val listener: MenuClickListener) :
    ListAdapter<Pair<MenuModel, Int>, BagAdapter.BagViewHolder>(BagCallBack()) {

    class BagViewHolder(
        private val binding: ItemBagLayoutBinding,
        private val listener: MenuClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Pair<MenuModel, Int>) {
            with(binding) {
                val price = item.first.price * item.second
                itemName.text = item.first.name
                itemPrice.text = price.toString()
                textCount.text = item.second.toString()
                buttonMinus.setOnClickListener {
                    listener.removeFromBag(item.first)
                }
                buttonPlus.setOnClickListener {
                    listener.addToBag(item.first)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BagViewHolder {
        val binding =
            ItemBagLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BagViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: BagViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}