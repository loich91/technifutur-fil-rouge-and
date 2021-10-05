package com.technipixl.filrouge

import android.content.Intent
import android.inputmethodservice.Keyboard
import android.opengl.ETC1.getHeight


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.technipixl.filrouge.databinding.AuthentificationMainBinding

class AuthentificationActivity : AppCompatActivity() {
    private lateinit var binding: AuthentificationMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AuthentificationMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonConnect.setOnClickListener {
            pageHomeAccess()
        }


    }
    fun pageHomeAccess(){
        val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent).also { finish() }
    }

}