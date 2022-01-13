package com.esaudev.gamesgarden.ui.navigation_states

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esaudev.gamesgarden.R
import com.esaudev.gamesgarden.databinding.FragmentFinalBinding
import com.esaudev.gamesgarden.databinding.FragmentInitialBinding

class FinalFragment : Fragment() {

    private var _binding: FragmentFinalBinding? = null
    private val binding: FragmentFinalBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentFinalBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

}