package com.esaudev.gamesgarden.ui.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.esaudev.gamesgarden.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentHomeBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnMostrar.setOnClickListener {

            val transition = Slide(Gravity.BOTTOM)
                .setDuration(300)
                .addTarget(binding.btnAppear)

            TransitionManager.beginDelayedTransition(binding.root, transition)
            binding.btnAppear.visibility = View.VISIBLE
        }

        binding.btnAppear.setOnClickListener {
            //binding.btnAppear.visibility = View.GONE

            val transition = Slide(Gravity.BOTTOM)
                .setDuration(300)
                .addTarget(binding.btnAppear)

            TransitionManager.beginDelayedTransition(binding.root, transition)
            binding.btnAppear.visibility = View.GONE
        }
    }




}