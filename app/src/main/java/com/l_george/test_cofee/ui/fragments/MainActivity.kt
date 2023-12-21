package com.l_george.test_cofee.ui.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.l_george.test_cofee.R
import com.l_george.test_cofee.app.CoffeeApp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as CoffeeApp).component.inject(this)
        setContentView(R.layout.activity_main)
    }
}