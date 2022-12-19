package com.example.aquacounter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.aquacounter.Model.Conexiones
import com.example.aquacounter.databinding.ActivityAddBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityAddBinding

    //Firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth
    //Database
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)




        //base de datos
        database = FirebaseDatabase.getInstance().getReference("Conexiones")



        binding.btnAdd.setOnClickListener {


            val nombre: String = binding.etName.text.toString().trim()
            val ubi: String = binding.etUbi.text.toString().trim()



            //id
            val id = database.child("Conexiones").push().key

            //validar datos
            if (nombre.isEmpty()){
                binding.etName.error = "Por favor ingrese el nombre de la Conexión"
            } else{
                val conexion = Conexiones(id,nombre, ubi)
                if (id != null) {
                    database.child(id).setValue(conexion).addOnSuccessListener {
                        //limpiar
                        binding.etName.setText("")
                        binding.etUbi.setText("")



                        Toast.makeText(this, "Conexion agregada", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{
                        Toast.makeText(this, "Error al agregar", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.btnView.setOnClickListener {
            startActivity(Intent(this, ViewConnectActivity::class.java))

        }
    }
}