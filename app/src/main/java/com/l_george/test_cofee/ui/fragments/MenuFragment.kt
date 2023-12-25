package com.l_george.test_cofee.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.l_george.test_cofee.R
import com.l_george.test_cofee.app.CoffeeApp
import com.l_george.test_cofee.data.models.MenuModel
import com.l_george.test_cofee.databinding.FragmentMenuBinding
import com.l_george.test_cofee.ui.adapters.menuAdapter.MenuAdapter
import com.l_george.test_cofee.ui.adapters.menuAdapter.MenuClickListener
import com.l_george.test_cofee.ui.viewModels.menuViewModel.MenuViewModel
import com.l_george.test_cofee.ui.viewModels.menuViewModel.MenuViewModelFactory
import com.l_george.test_cofee.utils.BUNDLE_LOCATION_ID
import javax.inject.Inject

class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding

    @Inject
    lateinit var menuViewModelFactory: MenuViewModelFactory
    private lateinit var menuViewModel: MenuViewModel
    private lateinit var adapter: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as CoffeeApp).component.inject(this)
        menuViewModel = ViewModelProvider(this, menuViewModelFactory)[MenuViewModel::class.java]
        adapter = MenuAdapter(object : MenuClickListener {
            override fun addToBag(menuModel: MenuModel) {
                menuViewModel.addToBag(menuModel)
            }

            override fun removeFromBag(menuModel: MenuModel) {
                menuViewModel.removeFromBag(menuModel)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(layoutInflater, container, false)
        menuViewModel.getMenuById(arguments?.getInt(BUNDLE_LOCATION_ID)!!)
        (requireActivity() as MainActivity).topBarSettings(true, getString(R.string.menu))
        requireActivity().findViewById<ImageButton>(R.id.button_back).apply {
            setOnClickListener {
                findNavController().navigateUp()
            }
            visibility = View.VISIBLE
        }
        menuViewModel.resetBag()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerMenu.adapter = adapter
            menuViewModel.menuListLiveData.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }

            buttonToPay.setOnClickListener {
                findNavController().navigate(R.id.action_menuFragment_to_payFragment)
            }
        }
    }


}