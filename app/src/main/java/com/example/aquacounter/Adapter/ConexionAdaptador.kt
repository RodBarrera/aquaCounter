package com.example.aquacounter.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aquacounter.Model.Conexiones
import com.example.aquacounter.R
import com.google.firebase.database.FirebaseDatabase


class ConexionAdaptador(private val conexiones: ArrayList<Conexiones>) : RecyclerView.Adapter<ConexionAdaptador.ViewHolder>() {
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.conexion_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val conexion = conexiones[position]
        holder.id.text = conexion.id
        holder.nombre.text = conexion.nombre
        holder.ubi.text = conexion.ubi
        holder.pet.text = conexion.consumoActual
        holder.genero.text = conexion.consumoTotal
        holder.btnEliminar.setOnClickListener {

            eliminarConexion(holder.id.text.toString())
        }

        /* holder.btnEditar.setOnClickListener{

            editarDispensador(holder.id.text.toString())
        }*/
    }

    /*private fun editarDispensador(id: String) {

        val firebaseDatabase = FirebaseDatabase.getInstance().getReference("Dispensadores").child(id)
        val mtask = firebaseDatabase.

    } */

    private fun eliminarConexion(id: String) {
        val firebaseDatabase = FirebaseDatabase.getInstance().getReference("Conexiones").child(id)
        val mTask = firebaseDatabase.removeValue()
    }

    override fun getItemCount(): Int {
        return conexiones.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.findViewById(R.id.tvNombre)
        val nombre: TextView = itemView.findViewById(R.id.tvId)
        val ubi: TextView = itemView.findViewById(R.id.tvUbi)
        val pet: TextView = itemView.findViewById(R.id.tvPets)
        val genero: TextView = itemView.findViewById(R.id.tvGenero)
        val btnEliminar: Button = itemView.findViewById(R.id.btnEliminarItem)

    }
}