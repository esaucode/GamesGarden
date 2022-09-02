package com.esaudev.gamesgarden.ui.navigation_states

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.esaudev.gamesgarden.R
import com.esaudev.gamesgarden.databinding.FragmentHomeBinding
import com.esaudev.gamesgarden.databinding.FragmentInitialBinding

class InitialFragment : Fragment() {

    private var _binding: FragmentInitialBinding? = null
    private val binding: FragmentInitialBinding
        get() = _binding!!

    private val args: InitialFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentInitialBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mbGoNext.setOnClickListener {
            val action = InitialFragmentDirections.actionInitialFragmentToBFragment(
                fragmentB = "Hola desde el fragmento B"
            )
            findNavController().navigate(action)
        }

        binding.etInput.setText(args.productId)
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(requireContext(), "on Resume triggered", Toast.LENGTH_SHORT).show()
    }


}