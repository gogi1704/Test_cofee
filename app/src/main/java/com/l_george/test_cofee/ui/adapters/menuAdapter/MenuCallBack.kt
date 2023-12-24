package com.l_george.test_cofee.ui.adapters.menuAdapter

import androidx.recyclerview.widget.DiffUtil
import com.l_george.test_cofee.data.models.MenuModel

class MenuCallBack : DiffUtil.ItemCallback<MenuModel>() {
    override fun areItemsTheSame(oldItem: MenuModel, newItem: MenuModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MenuModel, newItem: MenuModel): Boolean =
        oldItem == newItem
}