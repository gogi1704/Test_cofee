package com.l_george.test_cofee.ui.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.l_george.test_cofee.app.CoffeeApp
import com.l_george.test_cofee.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as CoffeeApp).component.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    fun topBarSettings(isVisible: Boolean , title:String) {
        binding.buttonBack.visibility = if (isVisible) View.VISIBLE else View.GONE
        binding.textTitle.text = title
    }

}