package ru.dartx.counting.utils

import ru.dartx.counting.domain.entity.Operation

object TextFormatter {
    fun getSign(operation: Operation?): String {
        return when (operation) {
            Operation.ADDITION -> "+"
            Operation.SUBTRACTION -> "-"
            Operation.MULTIPLICATION -> "*"
            Operation.DIVISION -> ":"
            else -> ""
        }
    }
}