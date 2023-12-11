package ru.dartx.counting.data

import android.app.Application
import androidx.preference.PreferenceManager
import ru.dartx.counting.domain.entity.GameSettings
import ru.dartx.counting.domain.entity.Operation
import ru.dartx.counting.domain.entity.Question
import ru.dartx.counting.domain.entity.TypeOfQuestion
import ru.dartx.counting.domain.repository.GameRepository
import ru.dartx.counting.presentation.SettingsActivity
import kotlin.random.Random

class GameRepositoryImpl : GameRepository {
    override fun getGameSettings(
        operation: Operation,
        typeOfQuestion: TypeOfQuestion,
        application: Application
    ): GameSettings {
        val defPreference = PreferenceManager.getDefaultSharedPreferences(application)
        val mdInterval =
            defPreference.getString(SettingsActivity.MD_INTERVAL, "5")?.toInt() ?: 5
        val asInterval =
            defPreference.getString(SettingsActivity.AS_INTERVAL, "5")?.toInt() ?: 5
        val mdIntervalRnd =
            defPreference.getString(SettingsActivity.MD_INTERVAL_RND, "7")?.toInt() ?: 7
        val asIntervalRnd =
            defPreference.getString(SettingsActivity.AS_INTERVAL_RND, "7")?.toInt() ?: 7
        val mdScoreStepsDP =
            defPreference.getStringSet(SettingsActivity.MD_SCORE_STEPS, setOf("10", "5"))
        val asScoreStepsDP =
            defPreference.getStringSet(SettingsActivity.AS_SCORE_STEPS, setOf("10", "5"))
        val mdScoreSteps = mdScoreStepsDP?.map { it.toInt() } ?: listOf(10, 5)
        val asScoreSteps = asScoreStepsDP?.map { it.toInt() } ?: listOf(10, 5)
        val mdIncorrectAnswerPenalty =
            defPreference.getString(SettingsActivity.MD_INCORRECT_ANSWER_PENALTY, "5")?.toInt() ?: 5
        val mdMissedAnswerPenalty =
            defPreference.getString(SettingsActivity.MD_MISSED_ANSWER_PENALTY, "10")?.toInt() ?: 10
        val asIncorrectAnswerPenalty =
            defPreference.getString(SettingsActivity.AS_INCORRECT_ANSWER_PENALTY, "5")?.toInt() ?: 5
        val asMissedAnswerPenalty =
            defPreference.getString(SettingsActivity.AS_MISSED_ANSWER_PENALTY, "10")?.toInt() ?: 10

        return when (typeOfQuestion) {
            TypeOfQuestion.RESULT -> {
                when (operation) {
                    Operation.ADDITION -> GameSettings(
                        operation,
                        typeOfQuestion,
                        99,
                        asScoreSteps.sortedDescending(),
                        90,
                        asInterval,
                        asIncorrectAnswerPenalty,
                        asMissedAnswerPenalty
                    )

                    Operation.SUBTRACTION -> GameSettings(
                        operation,
                        typeOfQuestion,
                        99,
                        asScoreSteps.sortedDescending(),
                        90,
                        asInterval,
                        asIncorrectAnswerPenalty,
                        asMissedAnswerPenalty
                    )

                    Operation.MULTIPLICATION -> GameSettings(
                        operation,
                        typeOfQuestion,
                        9,
                        mdScoreSteps.sortedDescending(),
                        90,
                        mdInterval,
                        mdIncorrectAnswerPenalty,
                        mdMissedAnswerPenalty
                    )

                    Operation.DIVISION -> GameSettings(
                        operation,
                        typeOfQuestion,
                        9,
                        mdScoreSteps.sortedDescending(),
                        90,
                        mdInterval,
                        mdIncorrectAnswerPenalty,
                        mdMissedAnswerPenalty
                    )
                }
            }

            TypeOfQuestion.RANDOM_MEMBER -> {
                when (operation) {
                    Operation.ADDITION -> GameSettings(
                        operation,
                        typeOfQuestion,
                        99,
                        asScoreSteps.sortedDescending(),
                        90,
                        asIntervalRnd,
                        asIncorrectAnswerPenalty,
                        asMissedAnswerPenalty
                    )

                    Operation.SUBTRACTION -> GameSettings(
                        operation,
                        typeOfQuestion,
                        99,
                        asScoreSteps.sortedDescending(),
                        90,
                        asIntervalRnd,
                        mdIncorrectAnswerPenalty,
                        mdMissedAnswerPenalty
                    )

                    Operation.MULTIPLICATION -> GameSettings(
                        operation,
                        typeOfQuestion,
                        9,
                        mdScoreSteps.sortedDescending(),
                        90,
                        mdIntervalRnd,
                        mdIncorrectAnswerPenalty,
                        mdMissedAnswerPenalty
                    )

                    Operation.DIVISION -> GameSettings(
                        operation,
                        typeOfQuestion,
                        9,
                        mdScoreSteps.sortedDescending(),
                        90,
                        mdIntervalRnd,
                        mdIncorrectAnswerPenalty,
                        mdMissedAnswerPenalty
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