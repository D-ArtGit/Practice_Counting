package ru.dartx.counting.presentation

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.core.widget.addTextChangedListener
import ru.dartx.counting.databinding.PasswordDialogBinding

object PasswordDialog {
    fun showDialog(context: Context, listener: Listener, message: String) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = PasswordDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        with(binding) {
            tvText.text = message
            btOk.setOnClickListener {
                etPassword.error = listener.onClick(etPassword.text.toString())
                if (etPassword.error == null) dialog?.dismiss()
            }
            btCancel.setOnClickListener {
                dialog?.dismiss()
            }
            etPassword.addTextChangedListener { text ->
                if (!text.isNullOrEmpty() && etPassword.error != null) etPassword.error = null
            }
        }
        dialog = builder.create()
        dialog.setCanceledOnTouchOutside(true)
        dialog.setOnCancelListener {
            it.dismiss()
        }
        dialog.show()
    }

    interface Listener {
        fun onClick(password: String): String?
    }
}