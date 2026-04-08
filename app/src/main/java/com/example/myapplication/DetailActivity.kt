package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var tvDetailContent: TextView
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        tvDetailContent = findViewById(R.id.tvDetailContent)
        btnBack = findViewById(R.id.btnBack)

        // Получаем данные из Intent
        val userName = intent.getStringExtra("USER_NAME")
        tvDetailContent.text = "Привет, ${userName ?: "Гость"}!"

        // Обработчик кнопки "Назад"
        btnBack.setOnClickListener {
            finish()
        }
    }
}