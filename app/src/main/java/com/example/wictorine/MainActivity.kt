package com.example.wictorine

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

// Заменили AppCompatActivity на ComponentActivity для совместимости с вашей темой проекта
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Открываем ваш XML-макет главного экрана
        setContentView(R.layout.activity_main)

        // Находим кнопку "Начать"
        val startButton: Button = findViewById(R.id.button)

        // Вешаем клик для перехода на экран вопросов
        startButton.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
        }
    }
}