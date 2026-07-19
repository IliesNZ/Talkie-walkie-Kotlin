package com.iliesnz.talkie_walkie_kotlin.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.iliesnz.talkie_walkie_kotlin.R

class talkie : AppCompatActivity() {
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

        val back = findViewById<Button>(R.id.button_back)
        val talk = findViewById<Button>(R.id.button_talk)

        val homeIntent = Intent(this, home::class.java)

        back.setOnClickListener {
            startActivity(homeIntent)
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

    }
}