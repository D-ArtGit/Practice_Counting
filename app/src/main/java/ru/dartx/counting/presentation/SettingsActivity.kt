package ru.dartx.counting.presentation

import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import ru.dartx.counting.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val etpAllGameScore = preferenceManager.findPreference<EditTextPreference>(
                ALL_GAME_SCORE
            )
            etpAllGameScore?.setOnBindEditTextListener {
                it.inputType = InputType.TYPE_CLASS_NUMBER
            }
            val etpPaidScore = preferenceManager.findPreference<EditTextPreference>(
                PAID_SCORE
            )
            etpPaidScore?.setOnBindEditTextListener { it.inputType = InputType.TYPE_CLASS_NUMBER }
            val etpMdIncorrectAnswerScore = preferenceManager.findPreference<EditTextPreference>(
                MD_INCORRECT_ANSWER_PENALTY
            )
            etpMdIncorrectAnswerScore?.setOnBindEditTextListener {
                it.inputType = InputType.TYPE_CLASS_NUMBER
            }
            val etpMdMissedAnswerPenalty = preferenceManager.findPreference<EditTextPreference>(
                MD_MISSED_ANSWER_PENALTY
            )
            etpMdMissedAnswerPenalty?.setOnBindEditTextListener {
                it.inputType = InputType.TYPE_CLASS_NUMBER
            }
            val etpAsIncorrectAnswerScore = preferenceManager.findPreference<EditTextPreference>(
                AS_INCORRECT_ANSWER_PENALTY
            )
            etpAsIncorrectAnswerScore?.setOnBindEditTextListener {
                it.inputType = InputType.TYPE_CLASS_NUMBER
            }
            val etpAsMissedAnswerPenalty = preferenceManager.findPreference<EditTextPreference>(
                AS_MISSED_ANSWER_PENALTY
            )
            etpAsMissedAnswerPenalty?.setOnBindEditTextListener {
                it.inputType = InputType.TYPE_CLASS_NUMBER
            }
        }
    }

    companion object {
        const val ALL_GAME_SCORE = "all_game_score"
        const val PAID_SCORE = "paid_score"
        const val MD_INCORRECT_ANSWER_PENALTY = "md_incorrect_answer_penalty"
        const val MD_MISSED_ANSWER_PENALTY = "md_missed_answer_penalty"
        const val AS_INCORRECT_ANSWER_PENALTY = "as_incorrect_answer_penalty"
        const val AS_MISSED_ANSWER_PENALTY = "as_missed_answer_penalty"
        const val MD_INTERVAL = "md_interval"
        const val AS_INTERVAL = "as_interval"
        const val MD_INTERVAL_RND = "md_interval_rnd"
        const val AS_INTERVAL_RND = "as_interval_rnd"
        const val MD_SCORE_STEPS = "md_score_steps"
        const val AS_SCORE_STEPS = "as_score_steps"
        const val PASSWORD = "password"
    }
}