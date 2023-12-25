package com.l_george.test_cofee.ui.adapters.menuAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.l_george.test_cofee.R
import com.l_george.test_cofee.data.models.MenuModel
import com.l_george.test_cofee.databinding.ItemMenuLayoutBinding

interface MenuClickListener {
    fun addToBag(menuModel: MenuModel)
    fun removeFromBag(menuModel: MenuModel)
}

class MenuAdapter(private val listener: MenuClickListener) :
    ListAdapter<MenuModel, MenuAdapter.MenuViewHolder>(MenuCallBack()) {

    class MenuViewHolder(
        private val binding: ItemMenuLayoutBinding,
        private val listener: MenuClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MenuModel) {
            with(binding) {
                Glide.with(itemImage)
                    .load(item.imageURL).placeholder(R.drawable.ic_coffee_placeholder)
                    .timeout(1_000)
                    .into(itemImage)
                itemName.text = item.name
                itemPrice.text = item.price.toString()

                buttonMinus.setOnClickListener {
                    val countNow = textCount.text.toString().toInt()
                    if (countNow > 0) {
                        textCount.text=StringBuilder()
                            .append(countNow - 1)
                        listener.removeFromBag(item)
                    }
                }

                buttonPlus.setOnClickListener {
                    val countNow = textCount.text.toString().toInt()
                    textCount.text = StringBuilder()
                        .append(countNow + 1)
                    listener.addToBag(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding =
            ItemMenuLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}