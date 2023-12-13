package ru.dartx.counting.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.preference.PreferenceManager
import ru.dartx.counting.R
import ru.dartx.counting.databinding.FragmentGameFinishedBinding
import ru.dartx.counting.domain.entity.GameResult

private const val GAME_RESULT = "game_result"

class GameFinishedFragment : Fragment() {
    private lateinit var gameResult: GameResult
    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("Binding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(GAME_RESULT, GameResult::class.java)
                ?.let { gameResult = it }
        } else {
            @Suppress("DEPRECATION")
            requireArguments().getParcelable<GameResult>(GAME_RESULT)?.let {
                gameResult = it
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    retryGame()
                }

            })
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(
            ChooseGameFragment.BACKSTACK_NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    private fun initViews() {
        with(binding) {
            if (
                gameResult.currentGameScore > 0 &&
                gameResult.rightAnswersCount / gameResult.allAnswersCount.toDouble() > 0.8
            ) {
                ivEmoji.setImageResource(R.drawable.ic_smile)
            } else {
                ivEmoji.setImageResource(R.drawable.ic_sad)
            }
            tvRightAnswers.text =
                getString(R.string.right_answers, gameResult.rightAnswersCount)
            tvAllAnswers.text =
                getString(R.string.all_answers, gameResult.allAnswersCount)
            tvGameScore.text = getString(R.string.game_score, gameResult.currentGameScore)
            tvAllGameScore.text = getString(R.string.all_score, gameResult.allGameScore)
            val defPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
            tvPaid.text =
                getString(R.string.paid, defPreferences.getString(SettingsActivity.PAID_SCORE, "0"))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(gameResult: GameResult) =
            GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(GAME_RESULT, gameResult)
                }
            }
    }
}