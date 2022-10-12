package com.example.tallerprueba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var  firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //IMportante iniciar Instancia Firebase
        firebaseAuth = FirebaseAuth.getInstance()

        btLogin.setOnClickListener {
            val email = etCorreoInicio.text.toString()
            val password = etContrasenaInicio.text.toString()
            if ( email.isNotEmpty() && password.isNotEmpty()){
                    //Llamar a firebase
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful){
                            Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                            //Mandamos usuario a Login
                            val intent = Intent (this, Dashboard::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
             else {
                Toast.makeText(this, "Faltan datos", Toast.LENGTH_SHORT).show()
            }
        }


        //Botón Registro
        btRegister.setOnClickListener {
            val intent = Intent (this, SignUp::class.java)
            startActivity(intent)
        }
    }
}