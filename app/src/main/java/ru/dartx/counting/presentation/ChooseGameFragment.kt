package ru.dartx.counting.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.dartx.counting.R
import ru.dartx.counting.databinding.FragmentChooseGameBinding
import ru.dartx.counting.domain.entity.Operation

class ChooseGameFragment : Fragment() {
    private var _binding: FragmentChooseGameBinding? = null
    private val binding: FragmentChooseGameBinding
        get() = _binding ?: throw RuntimeException("Binding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        with(binding) {
            btAddition.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.main_container,
                        ChooseTypeOfQuestionFragment.newInstance(Operation.ADDITION)
                    )
                    .addToBackStack(BACKSTACK_NAME)
                    .commit()
            }
            btDivision.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.main_container,
                        ChooseTypeOfQuestionFragment.newInstance(Operation.DIVISION)
                    )
                    .addToBackStack(BACKSTACK_NAME)
                    .commit()
            }
            btMultiplication.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.main_container,
                        ChooseTypeOfQuestionFragment.newInstance(Operation.MULTIPLICATION)
                    )
                    .addToBackStack(BACKSTACK_NAME)
                    .commit()
            }
            btSubstraction.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.main_container,
                        ChooseTypeOfQuestionFragment.newInstance(Operation.SUBTRACTION)
                    )
                    .addToBackStack(BACKSTACK_NAME)
                    .commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val BACKSTACK_NAME = "first_screen"
    }
}