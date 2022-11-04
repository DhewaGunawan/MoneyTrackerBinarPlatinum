package com.example.binarchplatinum.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

    private fun navigateToMenu(name : String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        Toast.makeText(this, "Name : $name", Toast.LENGTH_SHORT).show()
    }

    private fun setOnclickListener(){
        binding.etName.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            if(name.isEmpty()){

                Toast.makeText(this, "Please input your name !", Toast.LENGTH_SHORT).show()
            }else{
                navigateToMenu(name)
            }

            Toast.makeText(this, "Name", Toast.LENGTH_SHORT).show()
        }
    }
}