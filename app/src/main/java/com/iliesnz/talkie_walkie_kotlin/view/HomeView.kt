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
import com.iliesnz.talkie_walkie_kotlin.TalkieWalkieApplication

class HomeView : AppCompatActivity() {

    private lateinit var confirmation: Button
    private lateinit var ipAddress: EditText
    private lateinit var chargement: ProgressBar
    private lateinit var talkieViewIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val app = application as TalkieWalkieApplication
        val homeViewmodel = app.container.homeViewModel

        confirmation = findViewById<Button>(R.id.button)
        ipAddress = findViewById<EditText>(R.id.IP)
        chargement = findViewById<ProgressBar>(R.id.chargement)

        talkieViewIntent = Intent(this, TalkieView::class.java)

        confirmation.setOnClickListener {
            if (ipAddress.text.toString().isEmpty()) {
                Toast.makeText(this, "Il manque l'ip du serveur !", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Tentative de connexion...", Toast.LENGTH_LONG).show()
                uiState("loading")
                homeViewmodel.connectToServer(ipAddress.text.toString())
                uiState("success")
            }
        }
    }
    fun uiState(state: String) {
        when (state) {
            "base" -> {
                ipAddress.visibility = View.VISIBLE
                confirmation.visibility = View.VISIBLE
                chargement.visibility = View.GONE
            }

            "loading" -> {
                ipAddress.visibility = View.GONE
                confirmation.visibility = View.GONE
                chargement.visibility = View.VISIBLE
            }

            "error" -> {
                ipAddress.visibility = View.VISIBLE
                confirmation.visibility = View.VISIBLE
                chargement.visibility = View.GONE
            }

            "success" -> {
                startActivity(talkieViewIntent)
            }
        }
    }

}