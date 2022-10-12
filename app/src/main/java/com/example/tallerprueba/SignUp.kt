package com.example.tallerprueba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //IMportante iniciar Instancia Firebase
        firebaseAuth = FirebaseAuth.getInstance()

        //Indicar qué pasará cuando le demos click a crear usuario
        btCrear.setOnClickListener {
            //Traer valones de los edit text
            val correo = etCorreo.text.toString()
            val  password = etContrasena.text.toString()
            val confirmPassword = etConfirma.text.toString()

            //Condicionar para confirmar que todos los valores fueron llenados
            if ( correo.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()){
                //Verificar que ambos passwords sean iguales
                if( password == confirmPassword){
                    //Llamar a firebase
                    firebaseAuth.createUserWithEmailAndPassword(correo, password).addOnCompleteListener {
                        if (it.isSuccessful){
                            Toast.makeText(this, "Se dio de alta correctamente el usuario", Toast.LENGTH_SHORT).show()
                            //Mandamos usuario a Login
                            val intent = Intent (this, MainActivity::class.java)
                            startActivity(intent)
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
        }

    }
}