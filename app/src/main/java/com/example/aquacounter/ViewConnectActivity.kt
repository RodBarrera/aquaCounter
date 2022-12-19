package com.example.aquacounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aquacounter.Adapter.ConexionAdaptador
import com.example.aquacounter.Model.Conexiones
import com.google.firebase.database.*


class ViewConnectActivity : AppCompatActivity() {

    private lateinit var conexionRecyclerView: RecyclerView
    private lateinit var conexionAdaptador: ConexionAdaptador

    private lateinit var listConexiones: ArrayList<Conexiones>

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_connect)

        conexionRecyclerView = findViewById(R.id.rvConexion)
        conexionRecyclerView.layoutManager = LinearLayoutManager(this)
        conexionRecyclerView.setHasFixedSize(true)

        listConexiones = arrayListOf<Conexiones>()
        getDispensadores()



    }

    private fun getDispensadores() {
        println("Obteniendo Conexiones ")

        database = FirebaseDatabase.getInstance().getReference("Conexiones")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listConexiones.clear()
                if (snapshot.exists()){
                    for (productosSnapshot in snapshot.children){
                        val conexiones = productosSnapshot.getValue(Conexiones::class.java)
                        listConexiones.add(conexiones!!)
                    }
                    val adapterConexiones = ConexionAdaptador( listConexiones)
                    conexionRecyclerView.adapter = adapterConexiones
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}