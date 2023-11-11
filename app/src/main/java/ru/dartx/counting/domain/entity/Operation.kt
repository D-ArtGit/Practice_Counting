package ru.dartx.counting.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Operation: Parcelable {
    ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION
}