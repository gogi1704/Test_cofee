package com.l_george.test_cofee.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.l_george.test_cofee.app.CoffeeApp
import com.l_george.test_cofee.databinding.FragmentAuthBinding
import com.l_george.test_cofee.ui.viewModels.authViewModel.AuthViewModel
import com.l_george.test_cofee.ui.viewModels.authViewModel.AuthViewModelFactory
import javax.inject.Inject

class AuthFragment : Fragment() {
    private lateinit var binding: FragmentAuthBinding
    @Inject
    lateinit var authViewModelFactory: AuthViewModelFactory
    private lateinit var authViewModel:AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as CoffeeApp).component.inject(this)
        authViewModel = ViewModelProvider(this , authViewModelFactory)[AuthViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(layoutInflater, container, false)


        return binding.root
    }


}