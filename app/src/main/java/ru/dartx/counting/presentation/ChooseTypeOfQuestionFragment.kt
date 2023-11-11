package ru.dartx.counting.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.dartx.counting.R
import ru.dartx.counting.databinding.FragmentChooseTypeOfQuestionBinding
import ru.dartx.counting.domain.entity.Operation
import ru.dartx.counting.domain.entity.TypeOfQuestion
import ru.dartx.counting.utils.TextFormatter

private const val OPERATION = "operation"

class ChooseTypeOfQuestionFragment : Fragment() {
    private var _binding: FragmentChooseTypeOfQuestionBinding? = null
    private val binding: FragmentChooseTypeOfQuestionBinding
        get() = _binding ?: throw RuntimeException("Binding is null")
    private var operation: Operation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        operation = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(OPERATION, Operation::class.java)
        } else {
            @Suppress("DEPRECATION")
            requireArguments().getParcelable(OPERATION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseTypeOfQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTexts()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        operation?.let {
            with(binding) {
                btResult.setOnClickListener {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.main_container,
                            GameFragment.newInstance(operation!!, TypeOfQuestion.RESULT)
                        )
                        .addToBackStack(null)
                        .commit()
                }
                btRandom.setOnClickListener {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.main_container,
                            GameFragment.newInstance(operation!!, TypeOfQuestion.RANDOM_MEMBER)
                        )
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }

    private fun setTexts() {
        with(binding) {
            btResult.text =
                String.format(getString(R.string.button_result), TextFormatter.getSign(operation))
            btRandom.text =
                String.format(getString(R.string.button_random), TextFormatter.getSign(operation))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(operation: Operation) =
            ChooseTypeOfQuestionFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(OPERATION, operation)
                }
            }
    }
}