package com.iliesnz.talkie_walkie_kotlin.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.iliesnz.talkie_walkie_kotlin.R
import com.iliesnz.talkie_walkie_kotlin.container.AppContainer

class HomeView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val homeViewmodel = AppContainer.homeViewModel

        val confirmation = findViewById<Button>(R.id.button)
        val ipAddress = findViewById<EditText>(R.id.IP)
        val chargement = findViewById<ProgressBar>(R.id.chargement)

        val talkieViewIntent = Intent(this, TalkieView::class.java)

        confirmation.setOnClickListener {
            if (ipAddress.text.toString().isEmpty()){
                Toast.makeText(this, "Il manque l'ip du serveur !", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this, "Tentative de connexion...", Toast.LENGTH_LONG).show()
                ipAddress.visibility = View.GONE
                confirmation.visibility = View.GONE
                chargement.visibility = View.VISIBLE

                homeViewmodel.connectToServer(ipAddress.text.toString())
            }
        }


    }
}