package ru.dartx.counting.domain.repository

import android.app.Application
import ru.dartx.counting.domain.entity.GameSettings
import ru.dartx.counting.domain.entity.TypeOfQuestion
import ru.dartx.counting.domain.entity.Operation
import ru.dartx.counting.domain.entity.Question

interface GameRepository {
    fun getGameSettings(operation: Operation, typeOfQuestion: TypeOfQuestion, application: Application): GameSettings
    fun generateQuestion(gameSettings: GameSettings): Question
}