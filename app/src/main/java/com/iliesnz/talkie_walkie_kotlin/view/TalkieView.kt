package com.iliesnz.talkie_walkie_kotlin.view

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.iliesnz.talkie_walkie_kotlin.R
import com.iliesnz.talkie_walkie_kotlin.TalkieWalkieApplication
import com.iliesnz.talkie_walkie_kotlin.viewmodel.stateFlow.TalkieUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TalkieView : AppCompatActivity() {

    lateinit var back: Button
    lateinit var talk: Button
    lateinit var numberPicker: NumberPicker

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_talkie)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val app = application as TalkieWalkieApplication
        val talkieViewModel = app.container.talkieViewModel

        talkieViewModel.listening()

        back = findViewById<Button>(R.id.button_back)
        talk = findViewById<Button>(R.id.button_talk)
        numberPicker = findViewById<NumberPicker>(R.id.number_picker)
        numberPicker.minValue = 1
        numberPicker.maxValue = 15

        val homeViewIntent = Intent(this, HomeView::class.java)

        back.setOnClickListener {
            talkieViewModel.disconnectToServer()
            startActivity(homeViewIntent)
        }

        talk.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    Toast.makeText(this, "Parler...", Toast.LENGTH_LONG).show()
                    true
                }

                MotionEvent.ACTION_UP -> {
                    Toast.makeText(this, "Stop...", Toast.LENGTH_LONG).show()
                    true
                }

                else -> false
            }
        }

        numberPicker.setOnScrollListener { view, scrollState ->
            if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {         // SCROLL_STATE_IDLE = On ne touche plus la roue
                val finalValue = numberPicker.value
                blockPicker()
                talkieViewModel.changeChannel(finalValue)
            }
        }

        lifecycleScope.launch {
            talkieViewModel.uiStateReadOnly.collect {
                state -> uiState(state)
            }
        }

    }

    fun uiState(state: TalkieUiState){
        when(state){
            is TalkieUiState.base -> {
                back.visibility = View.VISIBLE
                talk.visibility = View.VISIBLE
                numberPicker.visibility = View.VISIBLE
            }

            is TalkieUiState.comingOutSound -> {

            }

            is TalkieUiState.incomingSound -> {

            }

            is TalkieUiState.Error -> {

            }
        }
    }


    private fun blockPicker() {
        lifecycleScope.launch {
            numberPicker.isEnabled = false
            delay(3000)
            numberPicker.isEnabled = true
        }
    }

}