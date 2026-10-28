package com.example.wictorine

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class ResultActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Связываем код с вашим созданным XML-макетом результатов
        setContentView(R.layout.activity_result)

        // Принимаем количество правильных ответов и сколько всего было вопросов
        val score = intent.getIntExtra("SCORE", 0)
        val totalQuestions = intent.getIntExtra("TOTAL", 5)

        // Находим текстовое поле результата по ID из вашего макета (на скриншоте это textView6)
        val tvScore: TextView = findViewById(R.id.textView6)
        tvScore.text = "Ваш результат: $score из $totalQuestions"

        // Находим кнопку перезапуска по ID (на скриншоте это button6)
        val btnRestart: Button = findViewById(R.id.button6)
        btnRestart.setOnClickListener {
            // При нажатии закрываем этот экран и возвращаемся в главное меню
            finish()
        }
    }
}