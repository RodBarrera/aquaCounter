package com.example.aquacounter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.aquacounter.databinding.ActivityProfileBinding

import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)




        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()


        }

        binding.btnapp.setOnClickListener{
            startActivity(Intent(this, CrudActivity::class.java))
        }

    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            // si el usuario esta logueado ir al perfil
            val email = firebaseUser.email
            binding.tvEmail.text = email
        }
        else{
            startActivity(Intent(this, LoginActivity:: class.java))
            finish()
        }
    }
}
