package com.example.binarchplatinum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.binarchplatinum.databinding.ActivityMainBinding
import com.example.binarchplatinum.ui.dialog.CustomDialogAdd

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        setOnClickListener()

    }

    private fun setOnClickListener() {
        binding.cvAddButton.setOnClickListener {
            CustomDialogAdd().show(supportFragmentManager, "newDialog")
        }
    }
}