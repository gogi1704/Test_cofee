package com.l_george.test_cofee.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.l_george.test_cofee.R
import com.l_george.test_cofee.app.CoffeeApp
import com.l_george.test_cofee.data.models.MenuModel
import com.l_george.test_cofee.databinding.FragmentPayBinding
import com.l_george.test_cofee.ui.adapters.bagAdapter.BagAdapter
import com.l_george.test_cofee.ui.adapters.menuAdapter.MenuClickListener
import com.l_george.test_cofee.ui.viewModels.menuViewModel.MenuViewModel
import com.l_george.test_cofee.ui.viewModels.menuViewModel.MenuViewModelFactory
import javax.inject.Inject

class PayFragment : Fragment() {
    private lateinit var binding: FragmentPayBinding

    @Inject
    lateinit var menuViewModelFactory: MenuViewModelFactory
    private lateinit var menuViewModel: MenuViewModel
    private lateinit var adapter: BagAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as CoffeeApp).component.inject(this)
        (requireActivity() as MainActivity).topBarSettings(true, getString(R.string.pay_title))
        menuViewModel = ViewModelProvider(this, menuViewModelFactory)[MenuViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPayBinding.inflate(layoutInflater, container, false)
        adapter = BagAdapter(object : MenuClickListener {
            override fun addToBag(menuModel: MenuModel) {
                menuViewModel.addToBag(menuModel)
            }

            override fun removeFromBag(menuModel: MenuModel) {
                menuViewModel.removeFromBag(menuModel)
            }

        })
        with(binding) {
            recyclerBag.adapter = adapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuViewModel.bagListLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }


}