package com.example.aquacounter

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.aquacounter.databinding.ActivitySignUpBinding

import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private lateinit var progressDialod: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth

    //datos globales

    private var email:String = ""
    private var password:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)





        //Inicio de progress dialog

        progressDialod= ProgressDialog(this)
        progressDialod.setTitle("Por favor espere")
        progressDialod.setMessage("Creando cuenta")
        progressDialod.setCanceledOnTouchOutside(false)


        firebaseAuth= FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener{
            validarDatos()
        }

    }

    private fun validarDatos() {
        // obtener los datos aqui
        email = binding.etEmail.text.toString().trim()
        password = binding.etPassword.text.toString().trim()

        // aqui se validan los datos
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmail.error = "Email Incorrecto"
        }

        else if(TextUtils.isEmpty(password)) {
            binding.etPassword.error = "por favor ingrese su contraseña"
        }

        else if(password.length < 6) {
            binding.etPassword.error = "La contraseña debe tener al menos 6 digitos"
        }
        else {
            firebaseRegistro(email, password)
        }

    }

    private fun firebaseRegistro(email: String, password: String) {
        progressDialod.show()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialod.dismiss()

                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Registrado con Email: $email", Toast.LENGTH_SHORT).show()


                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->

                progressDialod.dismiss()
                Toast.makeText(this, "error de registro: $email", Toast.LENGTH_SHORT).show()

            }
    }

}