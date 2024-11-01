package com.gestvet.gestvet.ui.features.home.views.chat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gestvet.gestvet.R


private lateinit var Btn_Login: Button
private lateinit var Btn_Register: Button

class Inicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Log.d("Inicio", "Actividad Inicio abierta")
        //enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)

        Btn_Login = findViewById(R.id.Btn_Login)
        Btn_Register = findViewById(R.id.Btn_Registro)


        Btn_Login.setOnClickListener {
            try {
                val intent = Intent(this@Inicio, RegisterActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Error al abrir Login: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

        Btn_Register.setOnClickListener {
            try {
                val intent = Intent(this@Inicio, RegisterActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Error al abrir Registro: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }


    }
}