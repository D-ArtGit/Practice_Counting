package ru.dartx.counting.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
    val rightAnswersCount: Int,
    val allAnswersCount: Int,
    val currentGameScore: Int,
    val allGameScore: Int
) : Parcelable
