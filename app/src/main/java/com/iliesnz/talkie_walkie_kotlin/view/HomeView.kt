package com.iliesnz.talkie_walkie_kotlin.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.iliesnz.talkie_walkie_kotlin.R
import com.iliesnz.talkie_walkie_kotlin.TalkieWalkieApplication
import com.iliesnz.talkie_walkie_kotlin.viewmodel.HomeViewmodel
import com.iliesnz.talkie_walkie_kotlin.viewmodel.stateFlow.HomeUiState
import kotlinx.coroutines.launch

class HomeView : AppCompatActivity() {

    private lateinit var confirmation: Button
    private lateinit var ipAddress: EditText
    private lateinit var chargement: ProgressBar
    private lateinit var talkieViewIntent: Intent
    private lateinit var homeViewmodel: HomeViewmodel

    private val requestMicrophonePermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                confirmation.isEnabled = true
            } else {
                Toast.makeText(this, "La permission du microphone est requise pour utiliser l'application.", Toast.LENGTH_LONG).show()
            }
        }

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
        homeViewmodel = app.container.homeViewModel

        confirmation = findViewById<Button>(R.id.button)
        ipAddress = findViewById<EditText>(R.id.IP)
        chargement = findViewById<ProgressBar>(R.id.chargement)

        confirmation.isEnabled = false

        talkieViewIntent = Intent(this, TalkieView::class.java)

        confirmation.setOnClickListener {
                homeViewmodel.connectToTCP(ipAddress.text.toString())
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                homeViewmodel.uiStateReadOnly.collect {
                    state -> uiState(state)
                }
            }
        }

        checkAndRequestMicrophonePermission()

    }

    private fun checkAndRequestMicrophonePermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED -> {     // Permission déjà accepté
                confirmation.isEnabled = true
            }
            shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO) -> {     // Si l'utilisateur refuse
                Toast.makeText(this, "La permission du microphone est requise pour utiliser l'application.", Toast.LENGTH_LONG).show()
                requestMicrophonePermission.launch(Manifest.permission.RECORD_AUDIO)
            }
            else -> {       // Première demande de permission
                requestMicrophonePermission.launch(Manifest.permission.RECORD_AUDIO)
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

                val errorLog = state.message ?: "Erreur inconnue"
                Toast.makeText(this, errorLog, Toast.LENGTH_LONG).show()
            }

            is HomeUiState.Success -> {
                homeViewmodel.resetState()
                startActivity(talkieViewIntent)
            }
        }
    }

}