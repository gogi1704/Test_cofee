package com.l_george.test_cofee.ui.adapters.bagAdapter

import androidx.recyclerview.widget.DiffUtil
import com.l_george.test_cofee.data.models.MenuModel

class BagCallBack:DiffUtil.ItemCallback<Pair<MenuModel, Int>>() {
    override fun areItemsTheSame(
        oldItem: Pair<MenuModel, Int>,
        newItem: Pair<MenuModel, Int>
    ): Boolean = oldItem.first.id == newItem.first.id

    override fun areContentsTheSame(
        oldItem: Pair<MenuModel, Int>,
        newItem: Pair<MenuModel, Int>
    ): Boolean = oldItem == newItem
}