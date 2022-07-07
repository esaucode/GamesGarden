package com.esaudev.gamesgarden

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.inflate
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.esaudev.gamesgarden.databinding.ActivityMainBinding
import com.esaudev.gamesgarden.databinding.ActivityMainBinding.inflate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bnvMain.setupWithNavController(findNavController(R.id.navHostMain))
    }
}