package com.l_george.test_cofee.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.l_george.test_cofee.app.CoffeeApp
import com.l_george.test_cofee.databinding.FragmentMenuBinding
import com.l_george.test_cofee.ui.adapters.menuAdapter.MenuAdapter
import com.l_george.test_cofee.ui.viewModels.menuViewModel.MenuViewModel
import com.l_george.test_cofee.ui.viewModels.menuViewModel.MenuViewModelFactory
import com.l_george.test_cofee.utils.BUNDLE_LOCATION_ID
import javax.inject.Inject

class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding

    @Inject
    lateinit var menuViewModelFactory: MenuViewModelFactory
    private lateinit var menuViewModel: MenuViewModel
    private lateinit var adapter:MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as CoffeeApp).component.inject(this)
        menuViewModel = ViewModelProvider(this, menuViewModelFactory)[MenuViewModel::class.java]
        adapter = MenuAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(layoutInflater, container, false)
        menuViewModel.getMenuById(arguments?.getInt(BUNDLE_LOCATION_ID)!!)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            recyclerMenu.adapter = adapter
            menuViewModel.menuListLiveData.observe(viewLifecycleOwner){
               adapter.submitList(it.reversed())
            }
        }
    }


}