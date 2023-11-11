package ru.dartx.counting.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.dartx.counting.domain.entity.Operation
import ru.dartx.counting.domain.entity.TypeOfQuestion

class GameViewModelFactory(
    private val application: Application,
    private val operation: Operation,
    private val typeOfQuestion: TypeOfQuestion
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java))
            return GameViewModel(application, operation, typeOfQuestion) as T
        else throw RuntimeException("Unknown view model class $modelClass")
    }
}