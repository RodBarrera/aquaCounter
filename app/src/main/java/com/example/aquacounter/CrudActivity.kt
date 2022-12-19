package com.example.aquacounter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.example.aquacounter.databinding.ActivityCrudBinding
import com.google.firebase.auth.FirebaseAuth

class CrudActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCrudBinding

    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrudBinding.inflate(layoutInflater)
        setContentView(binding.root)



        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }
        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }

        binding.btnView.setOnClickListener {
            startActivity(Intent(this, ViewConnectActivity::class.java))

        }


    }

    private fun checkUser() {

        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {

        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }
}
