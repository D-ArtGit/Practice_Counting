package ru.dartx.counting.domain.usecases

import android.app.Application
import ru.dartx.counting.domain.entity.GameSettings
import ru.dartx.counting.domain.entity.TypeOfQuestion
import ru.dartx.counting.domain.entity.Operation
import ru.dartx.counting.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {
    operator fun invoke(operation: Operation, typeOfQuestion: TypeOfQuestion, application: Application): GameSettings {
        return repository.getGameSettings(operation, typeOfQuestion, application)
    }
}