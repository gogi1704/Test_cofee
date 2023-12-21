package com.l_george.test_cofee.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.l_george.test_cofee.databinding.FragmentCoffeeListBinding


class CoffeeListFragment : Fragment() {
    private lateinit var binding: FragmentCoffeeListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoffeeListBinding.inflate(layoutInflater, container, false)


        return binding.root
    }


}