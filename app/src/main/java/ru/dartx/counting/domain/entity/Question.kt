package ru.dartx.counting.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question(
    val placeOfOption: Int,
    val member: List<Int>
): Parcelable
