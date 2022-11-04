package com.example.binarchplatinum.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.resources.Compatibility.Api21Impl.inflate
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.binarchplatinum.MainActivity
import com.example.binarchplatinum.databinding.ActivityEnterNameBinding
import com.example.binarchplatinum.databinding.ActivityEnterNameBinding.inflate
import com.example.binarchplatinum.databinding.ActivityMainBinding.inflate


class EnterNameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEnterNameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        saveIntenceState: Bundle?
    ): View {

        binding = EnterNameActivity.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onFinishNavigateListener() {
        val name = binding.etName.text.toString().trim()
        if(name.isEmpty()){

            Toast.makeText(requireContext(), "Please input your name !", Toast.LENGTH_SHORT).show()*/
        }else{
            navigateToMenu(name)
        }
    }

    private fun navigateToMenu(name : String) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        Toast.makeText(requireContext(), "Name : $name", Toast.LENGTH_SHORT).show()
    }
}