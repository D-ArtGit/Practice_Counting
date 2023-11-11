package ru.dartx.counting.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class TypeOfQuestion: Parcelable {
    RESULT, RANDOM_MEMBER
}