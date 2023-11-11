package ru.dartx.counting.presentation

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import ru.dartx.counting.R
import ru.dartx.counting.databinding.FragmentGameBinding
import ru.dartx.counting.domain.entity.Operation
import ru.dartx.counting.domain.entity.TypeOfQuestion

private const val TYPE_OF_QUESTION = "type_of_question"
private const val OPERATION = "operation"

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("Binding is null")
    private lateinit var typeOfQuestion: TypeOfQuestion
    private lateinit var operation: Operation
    private val viewModelFactory by lazy {
        GameViewModelFactory(
            requireActivity().application,
            operation,
            typeOfQuestion
        )
    }
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(TYPE_OF_QUESTION, TypeOfQuestion::class.java)
                ?.let { typeOfQuestion = it }
        } else {
            @Suppress("DEPRECATION")
            requireArguments().getParcelable<TypeOfQuestion>(TYPE_OF_QUESTION)?.let {
                typeOfQuestion = it
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(OPERATION, Operation::class.java)?.let {
                operation = it
            }
        } else {
            @Suppress("DEPRECATION")
            requireArguments().getParcelable<Operation>(OPERATION)?.let { operation = it }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setListeners()
    }

    private fun observeViewModel() {
        with(binding) {
            tvSign.text = when (operation) {
                Operation.ADDITION -> "+"
                Operation.SUBTRACTION -> "-"
                Operation.MULTIPLICATION -> "*"
                Operation.DIVISION -> ":"
            }
            viewModel.question.observe(viewLifecycleOwner) {
                etAnswer.setText("")
                when (it.placeOfOption) {
                    0 -> {
                        tvFirstMember.text = "?"
                        tvSecondMember.text = it.member[1].toString()
                        tvResult.text = it.member[2].toString()
                    }

                    1 -> {
                        tvFirstMember.text = it.member[0].toString()
                        tvSecondMember.text = "?"
                        tvResult.text = it.member[2].toString()
                    }

                    2 -> {
                        tvFirstMember.text = it.member[0].toString()
                        tvSecondMember.text = it.member[1].toString()
                        tvResult.text = "?"
                    }

                    else -> throw RuntimeException("Invalid place of option")
                }
            }
            viewModel.formattedTimer.observe(viewLifecycleOwner) { tvTimer.text = it }
            viewModel.currentAnswerScore.observe(viewLifecycleOwner) {
                tvCurrentAnswerScore.text = it.toString()
            }
            viewModel.currentGameScore.observe(viewLifecycleOwner) {
                tvCurrentGameScore.text = it.toString()
            }
            viewModel.isErrorAnswer.observe(viewLifecycleOwner) {
                if (it) etAnswer.error = getString(R.string.error_answer)
                else etAnswer.error = null
            }
            viewModel.gameResult.observe(viewLifecycleOwner) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.main_container, GameFinishedFragment.newInstance(it))
                    .commit()
            }
        }
    }

    private fun setListeners() {
        with(binding) {
            btAnswer.setOnClickListener {
                if (!etAnswer.text.isNullOrBlank())
                    viewModel.enterAnswer(etAnswer.text.toString().toInt())
            }
            etAnswer.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.resetError()
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
            etAnswer.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE && !etAnswer.text.isNullOrBlank()) {
                    viewModel.enterAnswer(etAnswer.text.toString().toInt())
                    true
                } else {
                    false
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().supportFragmentManager.popBackStack(
                        ChooseGameFragment.BACKSTACK_NAME,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                }

            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(operation: Operation, typeOfQuestion: TypeOfQuestion) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(OPERATION, operation)
                    putParcelable(TYPE_OF_QUESTION, typeOfQuestion)
                }
            }
    }
}