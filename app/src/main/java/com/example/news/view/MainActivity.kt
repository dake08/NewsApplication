package com.example.news.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.ui.setupWithNavController
import com.example.news.databinding.ActivityMainBinding
import androidx.navigation.findNavController
import com.example.news.R


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bottomNavigationView.setupWithNavController(findNavController(R.id.newsFragment))
    }
}