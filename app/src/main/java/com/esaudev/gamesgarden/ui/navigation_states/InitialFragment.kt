package com.esaudev.gamesgarden.ui.navigation_states

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.esaudev.gamesgarden.R
import com.esaudev.gamesgarden.databinding.FragmentHomeBinding
import com.esaudev.gamesgarden.databinding.FragmentInitialBinding
import com.esaudev.gamesgarden.extensions.hide
import com.esaudev.gamesgarden.extensions.hideToBottom
import com.esaudev.gamesgarden.extensions.show
import com.esaudev.gamesgarden.extensions.showFromBottom

class InitialFragment : Fragment() {

    private var _binding: FragmentInitialBinding? = null
    private val binding: FragmentInitialBinding
        get() = _binding!!

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


        binding.bMostrar.setOnClickListener {
            binding.ivImagen.showFromBottom(binding.root)
        }

        binding.bOcultar.setOnClickListener {
            binding.ivImagen.hideToBottom(binding.root)
        }
    }


    override fun onResume() {
        super.onResume()
        Toast.makeText(requireContext(), "on Resume triggered", Toast.LENGTH_SHORT).show()
    }


}