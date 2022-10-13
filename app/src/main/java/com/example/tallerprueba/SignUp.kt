package com.example.tallerprueba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //IMportante iniciar Instancia Firebase
        firebaseAuth = FirebaseAuth.getInstance()

        //Indicar qué pasará cuando le demos click a crear usuario
        btCrear.setOnClickListener {
            //Mostramos el cargando
            progressBar.visibility = View.VISIBLE
            //Traer valones de los edit text
            val correo = etCorreo.text.toString()
            val  password = etContrasena.text.toString()
            val confirmPassword = etConfirma.text.toString()
            val nombre = lbNombre.text.toString()
            val lenguaje = lbLenguaje.text.toString()

            //Condicionar para confirmar que todos los valores fueron llenados
            if ( correo.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && nombre.isNotEmpty() && lenguaje.isNotEmpty()){
                //Verificar que ambos passwords sean iguales
                if( password == confirmPassword){
                    //Llamar a firebase
                    firebaseAuth.createUserWithEmailAndPassword(correo, password).addOnCompleteListener {
                        if (it.isSuccessful){
                            Log.d("TAG", "Correo guardado")
                            //Implementamos funcion para mandar datos a DB
                            sendToDb(correo, nombre, lenguaje)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else {
                    Toast.makeText(this, "Las contraseñas deben ser iguales", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Faltan datos", Toast.LENGTH_SHORT).show()
            }
            progressBar.visibility = View.INVISIBLE
        }

    }

    private fun sendToDb(correo: String, nombre: String, lenguaje: String) {
        //Guardar lo que hay antes del punto
        val correoToSend = correo.substringBefore(".")
        database = FirebaseDatabase.getInstance().getReference("Users")
        val User = User(correo,nombre,lenguaje)
        database.child(correoToSend).setValue(User).addOnSuccessListener {
            Toast.makeText(this, "Se guardaron datos y creo correo", Toast.LENGTH_SHORT).show()
            //Mandamos usuario a Login
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(this, "Error guardando datos en bd", Toast.LENGTH_SHORT).show()
        }
    }
}