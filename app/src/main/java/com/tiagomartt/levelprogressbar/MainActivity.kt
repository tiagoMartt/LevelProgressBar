package com.tiagomartt.levelprogressbar

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tiagomartt.levelprogressbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var r = 0

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {

            r += 5
            binding.levelProgressBar.progress = r
        }


    }
}