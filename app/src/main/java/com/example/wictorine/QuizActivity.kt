package com.example.wictorine

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import java.io.BufferedReader
import java.io.InputStreamReader

class QuizActivity : ComponentActivity() {

    // Список вопросов
    private val questionList = mutableListOf<Question>()

    // Элементы интерфейса
    private lateinit var tvQuestionCount: TextView
    private lateinit var tvQuestionText: TextView
    private lateinit var btnOptions: List<Button>

    // Переменные индекса и баллов (они уже у нас есть)
    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // 1. Находим визуальные элементы через findViewById
        tvQuestionText = findViewById(R.id.textView3)
        tvQuestionCount = findViewById(R.id.textView)

        btnOptions = listOf(
            findViewById(R.id.button2),
            findViewById(R.id.button3),
            findViewById(R.id.button4),
            findViewById(R.id.button5)
        )

        // 2. Загружаем вопросы в переменную questionList из assets
        loadQuestionsFromAssets()

        // 3. Вешаем листенеров для кнопок с ответами на функцию checkAnswer
        // Проходим циклом по всем 4 кнопкам: index — это номер кнопки (0, 1, 2, 3)
        btnOptions.forEachIndexed { index, button ->
            button.setOnClickListener {
                checkAnswer(index) // передаем индекс нажатой кнопки в проверку
            }
        }

        // 4. Показываем текущий вопрос, вызывая функцию showNextQuestion
        showNextQuestion()
    }

    // • Создаем функцию проверки ответа строго по заданию
    private fun checkAnswer(selectedIndex: Int) {
        // Берем текущий вопрос из списка перед тем, как переключить индекс
        val currentQuestion = questionList[currentQuestionIndex]

        // Если индекс нажатой кнопки совпал с правильным индексом из файла — добавляем балл
        if (selectedIndex == currentQuestion.correctIndex) {
            score++
        }

        // Переходим к следующему вопросу (увеличиваем индекс на 1)
        currentQuestionIndex++

        // Открываем следующий вопрос через вызов функции showNextQuestion
        showNextQuestion()
    }

    // Функция чтения файла из assets
    private fun loadQuestionsFromAssets() {
        try {
            val reader = BufferedReader(InputStreamReader(assets.open("questions.txt")))
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                if (line!!.isBlank()) continue

                val trimmedLine = line!!.trim()
                val parts = trimmedLine.split("|")

                if (parts.size >= 7) {
                    val questionText = parts[1].trim()

                    val options = listOf(
                        parts[2].trim(),
                        parts[3].trim(),
                        parts[4].trim(),
                        parts[5].trim()
                    )

                    val correctIndex = parts[6].trim().toInt()

                    questionList.add(
                        Question(
                            questionText = questionText,
                            options = options,
                            correctIndex = correctIndex
                        )
                    )
                }
            }
            reader.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Функция отображения данных на экране телефона
    private fun showQuestion(index: Int) {
        if (index < 0 || index >= questionList.size) return

        val currentQuestion = questionList[index]

        tvQuestionText.text = currentQuestion.questionText
        tvQuestionCount.text = "Вопрос ${index + 1} из ${questionList.size}"

        if (currentQuestion.options.size >= 4) {
            btnOptions[0].text = currentQuestion.options[0]
            btnOptions[1].text = currentQuestion.options[1]
            btnOptions[2].text = currentQuestion.options[2]
            btnOptions[3].text = currentQuestion.options[3]
        }
    }
    private fun showNextQuestion() {
        if (currentQuestionIndex < questionList.size) {
            // Если есть следующий вопрос — показываем его
            showQuestion(currentQuestionIndex)
        } else {
            // Вопросы закончились! Переходим на экран результатов
            val intent = Intent(this, ResultActivity::class.java)

            // Передаем заработанные баллы и общее число вопросов внутрь нового экрана
            intent.putExtra("SCORE", score)
            intent.putExtra("TOTAL", questionList.size)

            startActivity(intent)
            finish() // Закрываем экран квиза, чтобы пользователь не мог вернуться к нему кнопкой "Назад"
        }
    }
}