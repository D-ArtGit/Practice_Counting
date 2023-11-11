package ru.dartx.counting.data

import ru.dartx.counting.domain.entity.GameSettings
import ru.dartx.counting.domain.entity.Operation
import ru.dartx.counting.domain.entity.Question
import ru.dartx.counting.domain.entity.TypeOfQuestion
import ru.dartx.counting.domain.repository.GameRepository
import kotlin.random.Random

class GameRepositoryImpl : GameRepository {
    override fun getGameSettings(
        operation: Operation,
        typeOfQuestion: TypeOfQuestion
    ): GameSettings {
        return when (typeOfQuestion) {
            TypeOfQuestion.RESULT -> {
                when (operation) {
                    Operation.ADDITION -> GameSettings(
                        operation,
                        typeOfQuestion,
                        99,
                        listOf(15, 10, 5),
                        90,
                        10
                    )

                    Operation.SUBTRACTION -> GameSettings(
                        operation,
                        typeOfQuestion,
                        99,
                        listOf(15, 10, 5),
                        90,
                        10
                    )

                    Operation.MULTIPLICATION -> GameSettings(
                        operation,
                        typeOfQuestion,
                        9,
                        listOf(15, 10, 5),
                        90,
                        5
                    )

                    Operation.DIVISION -> GameSettings(
                        operation,
                        typeOfQuestion,
                        9,
                        listOf(15, 10, 5),
                        90,
                        5
                    )
                }
            }

            TypeOfQuestion.RANDOM_MEMBER -> {
                when (operation) {
                    Operation.ADDITION -> GameSettings(
                        operation,
                        typeOfQuestion,
                        99,
                        listOf(21, 14, 7),
                        90,
                        10
                    )

                    Operation.SUBTRACTION -> GameSettings(
                        operation,
                        typeOfQuestion,
                        99,
                        listOf(21, 14, 7),
                        90,
                        10
                    )

                    Operation.MULTIPLICATION -> GameSettings(
                        operation,
                        typeOfQuestion,
                        9,
                        listOf(21, 14, 7),
                        90,
                        10
                    )

                    Operation.DIVISION -> GameSettings(
                        operation,
                        typeOfQuestion,
                        9,
                        listOf(21, 14, 7),
                        90,
                        10
                    )
                }
            }
        }
    }

    override fun generateQuestion(gameSettings: GameSettings): Question {
        val firstRandomMember = Random.nextInt(2, gameSettings.maxValueOfMember + 1)
        val secondRandomMember = Random.nextInt(2, gameSettings.maxValueOfMember + 1)
        return when (gameSettings.typeOfQuestion) {
            TypeOfQuestion.RESULT -> {
                when (gameSettings.operation) {
                    Operation.ADDITION -> Question(
                        2,
                        listOf(
                            firstRandomMember,
                            secondRandomMember,
                            firstRandomMember + secondRandomMember
                        )
                    )

                    Operation.SUBTRACTION -> Question(
                        2,
                        listOf(
                            firstRandomMember + secondRandomMember,
                            firstRandomMember,
                            secondRandomMember
                        )
                    )

                    Operation.MULTIPLICATION -> Question(
                        2,
                        listOf(
                            firstRandomMember,
                            secondRandomMember,
                            firstRandomMember * secondRandomMember
                        )
                    )

                    Operation.DIVISION -> Question(
                        2,
                        listOf(
                            firstRandomMember * secondRandomMember,
                            firstRandomMember,
                            secondRandomMember
                        )
                    )
                }
            }

            TypeOfQuestion.RANDOM_MEMBER -> {
                when (gameSettings.operation) {
                    Operation.ADDITION -> Question(
                        Random.nextInt(0, 3),
                        listOf(
                            firstRandomMember,
                            secondRandomMember,
                            firstRandomMember + secondRandomMember
                        )
                    )

                    Operation.SUBTRACTION -> Question(
                        Random.nextInt(0, 3),
                        listOf(
                            firstRandomMember + secondRandomMember,
                            firstRandomMember,
                            secondRandomMember
                        )
                    )

                    Operation.MULTIPLICATION -> Question(
                        Random.nextInt(0, 3),
                        listOf(
                            firstRandomMember,
                            secondRandomMember,
                            firstRandomMember * secondRandomMember
                        )
                    )

                    Operation.DIVISION -> Question(
                        Random.nextInt(0, 3),
                        listOf(
                            firstRandomMember * secondRandomMember,
                            firstRandomMember,
                            secondRandomMember
                        )
                    )
                }
            }
        }
    }
}