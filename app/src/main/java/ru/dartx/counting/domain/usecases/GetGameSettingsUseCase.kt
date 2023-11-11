package ru.dartx.counting.domain.usecases

import ru.dartx.counting.domain.entity.GameSettings
import ru.dartx.counting.domain.entity.TypeOfQuestion
import ru.dartx.counting.domain.entity.Operation
import ru.dartx.counting.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {
    operator fun invoke(operation: Operation, typeOfQuestion: TypeOfQuestion): GameSettings {
        return repository.getGameSettings(operation, typeOfQuestion)
    }
}