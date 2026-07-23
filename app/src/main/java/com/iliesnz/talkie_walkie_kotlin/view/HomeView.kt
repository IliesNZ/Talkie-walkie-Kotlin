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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.iliesnz.talkie_walkie_kotlin.R
import com.iliesnz.talkie_walkie_kotlin.TalkieWalkieApplication
import com.iliesnz.talkie_walkie_kotlin.viewmodel.stateFlow.HomeUiState
import kotlinx.coroutines.launch

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
                homeViewmodel.connectToServer(ipAddress.text.toString())
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                homeViewmodel.uiStateReadOnly.collect {
                    state -> uiState(state)
                }
            }
        }


    }

    private fun uiState(state: HomeUiState) {
        when (state) {
            is HomeUiState.Base -> {
                ipAddress.visibility = View.VISIBLE
                confirmation.visibility = View.VISIBLE
                chargement.visibility = View.GONE
            }

            is HomeUiState.Loading -> {
                ipAddress.visibility = View.GONE
                confirmation.visibility = View.GONE
                chargement.visibility = View.VISIBLE
            }

            is HomeUiState.Error -> {
                ipAddress.visibility = View.VISIBLE
                confirmation.visibility = View.VISIBLE
                chargement.visibility = View.GONE
                Toast.makeText(this, "Erreur de connexion au serveur", Toast.LENGTH_LONG).show()
            }

            is HomeUiState.Success -> {
                startActivity(talkieViewIntent)
                finish()
            }
        }
    }

}