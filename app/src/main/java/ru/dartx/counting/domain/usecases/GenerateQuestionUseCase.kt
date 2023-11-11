package ru.dartx.counting.domain.usecases

import ru.dartx.counting.domain.entity.GameSettings
import ru.dartx.counting.domain.entity.Question
import ru.dartx.counting.domain.repository.GameRepository

class GenerateQuestionUseCase(private val repository: GameRepository) {
    operator fun invoke(gameSettings: GameSettings): Question {
        return repository.generateQuestion(gameSettings)
    }
}