package com.gestvet.gestvet.ui.features.home.views.chat


import com.gestvet.gestvet.R
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity


class LoginChat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_login_chat)
        MostrarBienvenida()

    }

    fun MostrarBienvenida(){
        object: CountDownTimer(3000, 1000){
            override fun onTick(p0: Long) {
                //TODO("Not yet implemented")
            }

            override fun onFinish() {
                val intent = Intent(applicationContext, ChatF::class.java)
                startActivity(intent)
                finish()
            }

        }.start()
    }
}
