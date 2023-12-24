package com.l_george.test_cofee.ui.adapters.menuAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.l_george.test_cofee.R
import com.l_george.test_cofee.data.models.MenuModel
import com.l_george.test_cofee.databinding.ItemMenuLayoutBinding

class MenuAdapter : ListAdapter<MenuModel, MenuAdapter.MenuViewHolder>(MenuCallBack()) {

    class MenuViewHolder(private val binding: ItemMenuLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MenuModel) {
            with(binding) {
                Glide.with(itemImage)
                    .load(item.imageURL).placeholder(R.drawable.icon_point)
                    .timeout(1_000)
                    .into(itemImage)
                itemName.text = item.name
                itemPrice.text = item.price.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding =
            ItemMenuLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}