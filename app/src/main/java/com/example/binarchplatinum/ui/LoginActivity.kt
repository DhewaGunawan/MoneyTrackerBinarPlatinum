package com.example.binarchplatinum.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.binarchplatinum.MainActivity
import com.example.binarchplatinum.databinding.ActivityLoginBinding



class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setOnclickListener()

    }

    private fun setOnclickListener(){
        binding.btnName.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            if(name.isEmpty()){

                Toast.makeText(this, "Please input your name !", Toast.LENGTH_SHORT).show()
            }else{
                navigateToMenu(name)
            }

            Toast.makeText(this, "Name", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToMenu(name: String) {
        MainActivity.startActivity(this,name)
    }
}