package com.esaudev.gamesgarden.ui.navigation_states

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.esaudev.gamesgarden.R
import com.esaudev.gamesgarden.databinding.FragmentBBinding
import com.esaudev.gamesgarden.databinding.FragmentInitialBinding

class BFragment : Fragment() {

    private var _binding: FragmentBBinding? = null
    private val binding: FragmentBBinding
        get() = _binding!!

    private val viewModel: BViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentBBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initListeners()
    }

    private fun initObservers() {
        viewModel.argument.observe(viewLifecycleOwner) {
            binding.tvArgument.text = it
            binding.tvArgument.visibility = View.VISIBLE
            Log.d("TEST_ESAU", "Observer triggered")
        }
    }

    private fun initListeners(){
        binding.tvArgument.setOnClickListener {
            findNavController().navigate(R.id.action_BFragment_to_finalFragment)
        }
    }
}