package com.l_george.test_cofee.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.l_george.test_cofee.R
import com.l_george.test_cofee.app.CoffeeApp
import com.l_george.test_cofee.databinding.FragmentCoffeeListBinding
import com.l_george.test_cofee.ui.adapters.coffeeShopAdapter.CoffeeShopAdapter
import com.l_george.test_cofee.ui.adapters.coffeeShopAdapter.CoffeeShopClickListener
import com.l_george.test_cofee.ui.viewModels.authViewModel.AuthViewModel
import com.l_george.test_cofee.ui.viewModels.authViewModel.AuthViewModelFactory
import com.l_george.test_cofee.ui.viewModels.locationsViewModel.LocationViewModel
import com.l_george.test_cofee.ui.viewModels.locationsViewModel.LocationViewModelFactory
import com.l_george.test_cofee.utils.AuthError
import com.l_george.test_cofee.utils.BUNDLE_LOCATION_ID
import com.l_george.test_cofee.utils.makeToast
import javax.inject.Inject


class CoffeeListFragment : Fragment() {


    private lateinit var binding: FragmentCoffeeListBinding

    @Inject
    lateinit var authViewModelFactory: AuthViewModelFactory
    private lateinit var authViewModel: AuthViewModel

    @Inject
    lateinit var locationViewModelFactory: LocationViewModelFactory
    private lateinit var locationViewModel: LocationViewModel

    private lateinit var adapter: CoffeeShopAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireContext().applicationContext as CoffeeApp).component.inject(this)
        authViewModel = ViewModelProvider(this, authViewModelFactory)[AuthViewModel::class.java]

        locationViewModel =
            ViewModelProvider(this, locationViewModelFactory)[LocationViewModel::class.java]

        adapter = CoffeeShopAdapter(object : CoffeeShopClickListener {
            override fun openCoffeeShop(coffeeShopId: Int) {
                findNavController().navigate(
                    R.id.action_coffeeListFragment_to_menuFragment,
                    Bundle().apply {
                        putInt(BUNDLE_LOCATION_ID, coffeeShopId)
                    })
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoffeeListBinding.inflate(layoutInflater, container, false)
        (requireActivity() as MainActivity).topBarSettings(true, getString(R.string.coffee_title))

        requireActivity().findViewById<ImageButton>(R.id.button_back).apply {
            setOnClickListener {
                authViewModel.logOut()
                findNavController().navigateUp()
            }
            visibility = View.VISIBLE
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            coffeeRecycler.adapter = adapter

            locationViewModel.listCoffeeShopLiveData.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }

            locationViewModel.errorLiveData.observe(viewLifecycleOwner) {
                if (it != null) {
                    if (it is AuthError) {
                        findNavController().navigate(R.id.authFragment)
                    }
                    requireContext().makeToast(it.message)
                }
            }

            buttonToMap.setOnClickListener {
                findNavController().navigate(R.id.action_coffeeListFragment_to_mapFragment)
            }


        }

        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                authViewModel.logOut()
                findNavController().navigateUp()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callBack)
    }


}

