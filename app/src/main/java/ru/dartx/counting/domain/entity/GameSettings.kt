package ru.dartx.counting.domain.entity

data class GameSettings(
    val operation: Operation,
    val typeOfQuestion: TypeOfQuestion,
    val maxValueOfMember: Int,
    val scoreForRightAnswer: List<Int>,
    val gameTimeInSeconds: Int,
    val timerStepInSeconds: Int
)