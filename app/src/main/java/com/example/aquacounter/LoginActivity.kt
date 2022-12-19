package com.example.aquacounter

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.aquacounter.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    //cargar variable view Binding

    private lateinit var binding: ActivityLoginBinding

    // variable action Bar





    //cargar variable progress dialog

    private lateinit var progressDialog: ProgressDialog

    // cargar variable autenticación Firebase

    private lateinit var firebaseAuth: FirebaseAuth

    // cargar variables globales

    private var email:String = ""
    private var password:String = ""

   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)




        // iniciar progress dialog

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Por favor espere ....")
        progressDialog.setMessage("Iniciando Sesión")
        progressDialog.setCanceledOnTouchOutside(false)

        // iniciar autenticacion firebase

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        // programar btn iniciar sesion

        binding.btnLogin.setOnClickListener{

            // validar los datos
            validarDatos()

        }

        //programar registrate

        binding.tvRegister.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))

        }


    }

    private fun validarDatos() {
        // obtener los datos aqui
        email = binding.etEmail.text.toString().trim()
        password = binding.etPassword.text.toString().trim()

        // aqui se validan los datos
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmail.error = "Email Incorrecto"
        }

        else if(TextUtils.isEmpty(password)) {
            binding.etPassword.error = "por favor ingrese su contraseña"
        } else if(password.length < 6)
            binding.etPassword.error = "La contraseña debe tener al menos 6 digitos"

        else {
            firebaseLogin(email,password)
        }

    }

    private fun firebaseLogin(email: String, password: String) {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()

                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Bienvenido $email", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,ProfileActivity::class.java))
                finish()
            }
            .addOnFailureListener{ e ->

                progressDialog.dismiss()
                Toast.makeText(this,"Error al iniciar sesión: $email", Toast.LENGTH_SHORT).show()

            }
    }

    private fun checkUser() {
        // chequear usuario actual

        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            // si el usuario esta logueado ir al perfil
            startActivity(Intent(this, ProfileActivity:: class.java))
            finish()
        }

    }
}