package com.example.tallerprueba

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_dashboard.progressBar
import kotlinx.android.synthetic.main.activity_sign_up.*


class Dashboard : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        initPage()
        //Lo desaparecemos
        progressBar.visibility = View.INVISIBLE

        //Para salir
        btLogOut.setOnClickListener {
            val preferences: SharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
            preferences.edit().remove("USUARIO").commit()
            //Mandamos usuario a Login
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun initPage() {
        //Mostramos el cargando
        progressBar.visibility = View.VISIBLE

        //Nos traemos las preferencias
        val sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        val savedUser = sharedPreferences.getString("USUARIO", null)
        tvUser.text = savedUser
        getInfo(savedUser)
    }

    private fun getInfo(savedUser: String?) {
        //Nos traemos la info
        database = FirebaseDatabase.getInstance().getReference("Users")
        if (savedUser != null) {
            database.child(savedUser).get().addOnSuccessListener {
                if (it.exists()){
                    val lenguaje = it.child("lenguaje").value
                    val nombre = it.child("nombre").value
                    tvNombre.text = nombre.toString()
                    tvLenguaje.text = lenguaje.toString()
                    Toast.makeText(this, "Bienvenido $nombre", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Error al traer datos", Toast.LENGTH_LONG).show()
            }
        }
    }
}