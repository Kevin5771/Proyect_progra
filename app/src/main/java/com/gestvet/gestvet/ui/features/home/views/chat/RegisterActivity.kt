package com.gestvet.gestvet.ui.features.home.views.chat

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gestvet.gestvet.R
import com.google.firebase.auth.FirebaseAuth


private  lateinit var R_Nombre : EditText
private lateinit var R_Email: EditText
private lateinit var R_Password: EditText
private lateinit var R_r_Password: EditText
private lateinit var R_Btn_Registrar: Button

private lateinit var auth: FirebaseAuth


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        InicializarVariables()

        R_Btn_Registrar.setOnClickListener{
            ValidarDatos()
        }

    }
    private fun InicializarVariables(){
        R_Nombre = findViewById(R.id.R_Nombre_User)
        R_Email = findViewById(R.id.R_Email)
        R_Password = findViewById(R.id.R_Password)
        R_r_Password= findViewById(R.id.R_r_Password)
        R_Btn_Registrar = findViewById(R.id.R_Btn_Registrar)
    }
    private fun ValidarDatos(){
        val nombre_user :String = R_Nombre.text.toString()
        val email : String = R_Email.text.toString()
        val pass: String = R_Password.text.toString()
        val r_password : String = R_r_Password.text.toString()

        if (nombre_user.isEmpty()){
            Toast.makeText(applicationContext, "Ingrese Nombre de Usuario", Toast.LENGTH_SHORT).show()
        }
        else if (email.isEmpty()){
            Toast.makeText(applicationContext, "Ingrese su Correp", Toast.LENGTH_SHORT).show()
        }
        else if (pass.isEmpty()) {
            Toast.makeText(applicationContext, "Ingrese Contraseña", Toast.LENGTH_SHORT).show()
        }
        else if (r_password.isEmpty()){
            Toast.makeText(applicationContext, "Repita su Contraseña", Toast.LENGTH_SHORT).show()
        }
        else if (!pass.equals(r_password)){
            Toast.makeText(applicationContext, "Las Contraseñas no Coinciden", Toast.LENGTH_SHORT).show()
        }
        else{
            //RegistrarUsuario(email,pass)

        }
    }

    //private fun RegistrarUsuario(email: String, pass: String) {
    //auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener{
    // auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{

    // if()
    //}
    // )
    //  }
    //}
}

