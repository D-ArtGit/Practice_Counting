package ru.dartx.counting.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import ru.dartx.counting.data.GameRepositoryImpl
import ru.dartx.counting.domain.entity.GameResult
import ru.dartx.counting.domain.entity.GameSettings
import ru.dartx.counting.domain.entity.Operation
import ru.dartx.counting.domain.entity.Question
import ru.dartx.counting.domain.entity.TypeOfQuestion
import ru.dartx.counting.domain.usecases.GenerateQuestionUseCase
import ru.dartx.counting.domain.usecases.GetGameSettingsUseCase

class GameViewModel(
    private val application: Application,
    private val operation: Operation,
    private val typeOfQuestion: TypeOfQuestion
) : ViewModel() {
    private val repository = GameRepositoryImpl()
    private lateinit var gameSettings: GameSettings
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private var mainTimer: CountDownTimer? = null
    private var scoreTimer: CountDownTimer? = null

    private val _formattedTimer = MutableLiveData<String>()
    val formattedTimer: LiveData<String>
        get() = _formattedTimer

    private val _currentAnswerScore = MutableLiveData<Int>(0)
    val currentAnswerScore: LiveData<Int>
        get() = _currentAnswerScore

    private val _currentGameScore = MutableLiveData<Int>(0)
    val currentGameScore: LiveData<Int>
        get() = _currentGameScore

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val _isErrorAnswer = MutableLiveData<Boolean>()
    val isErrorAnswer: LiveData<Boolean>
        get() = _isErrorAnswer

    private var countOfAnswers = 0
    private var countOfRightAnswers = 0
    private val defPreference = PreferenceManager.getDefaultSharedPreferences(application)
    private var score = defPreference.getInt("all_game_score", 0)

    init {
        startGame()
    }

    private fun startGame() {
        getGameSetting()
        startMainTimer()
        generateQuestion()
        _currentGameScore.value = 0
    }

    private fun getGameSetting() {
        gameSettings = getGameSettingsUseCase(operation, typeOfQuestion)
    }

    private fun startMainTimer() {
        mainTimer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECOND,
            MILLIS_IN_SECOND
        ) {
            override fun onTick(p0: Long) {
                _formattedTimer.value = formatTime(p0)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        mainTimer?.start()
    }

    private fun startScoreTimer() {
        scoreTimer?.cancel()
        var step = 0
        val scoreTimerInMillis =
            gameSettings.timerStepInSeconds * gameSettings.scoreForRightAnswer.size * MILLIS_IN_SECOND
        scoreTimer = object : CountDownTimer(
            scoreTimerInMillis,
            gameSettings.timerStepInSeconds * MILLIS_IN_SECOND
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _currentAnswerScore.value =
                    gameSettings.scoreForRightAnswer[step++]
            }

            override fun onFinish() {
                _currentAnswerScore.value = 0
            }

        }
        scoreTimer?.start()
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSettings)
        _isErrorAnswer.value = false
        startScoreTimer()
    }

    fun enterAnswer(number: Int) {
        countOfAnswers++
        if (checkAnswerIsError(number)) {
            _isErrorAnswer.value = true
            _currentGameScore.value =
                currentGameScore.value?.minus(gameSettings.scoreForRightAnswer[gameSettings.scoreForRightAnswer.size - 1])
        } else {
            countOfRightAnswers++
            _isErrorAnswer.value = false
            _currentGameScore.value = currentAnswerScore.value?.let {
                _currentGameScore.value?.plus(
                    it
                )
            }
            generateQuestion()
        }
    }

    fun resetError() {
        _isErrorAnswer.value = false
    }

    private fun checkAnswerIsError(number: Int): Boolean {
        if (question.value != null) {
            return number != question.value!!.member[question.value!!.placeOfOption]
        } else throw RuntimeException("Unknown question")
    }

    private fun finishGame() {
        score += currentGameScore.value!!
        defPreference.edit().putInt("all_game_score", score).apply()
        _gameResult.value = GameResult(
            countOfRightAnswers,
            countOfAnswers,
            currentGameScore.value!!,
            score
        )
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val allSeconds = millisUntilFinished / MILLIS_IN_SECOND
        val minutes = allSeconds / SECONDS_IN_MINUTE
        val seconds = allSeconds - (minutes * SECONDS_IN_MINUTE)
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onCleared() {
        super.onCleared()
        mainTimer?.cancel()
        scoreTimer?.cancel()
    }

    companion object {
        private const val MILLIS_IN_SECOND = 1000L
        private const val SECONDS_IN_MINUTE = 60
    }
}