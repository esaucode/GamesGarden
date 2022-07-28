package com.esaudev.gamesgarden.ui.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.esaudev.gamesgarden.databinding.FragmentHomeBinding
import com.esaudev.gamesgarden.ui.SEARCH_QUERY_KEY
import com.esaudev.gamesgarden.ui.goToSearch
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    private var resultSearchActivity: ActivityResultLauncher<Intent>? = null

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

            goToSearch()
        }

        binding.btnAppear.setOnClickListener {
            //binding.btnAppear.visibility = View.GONE

            val transition = Slide(Gravity.BOTTOM)
                .setDuration(300)
                .addTarget(binding.btnAppear)

            TransitionManager.beginDelayedTransition(binding.root, transition)
            binding.btnAppear.visibility = View.GONE

            val builder = CustomTabsIntent.Builder()
            val customTabIntent = builder.build()
            customTabIntent.launchUrl(requireContext(), Uri.parse("https://medium.com/swlh/using-custom-chrome-tabs-in-your-android-app-b31e4f8f5194"))
        }

        onResultSearchActivity()
    }

    private fun goToSearch() {
        resultSearchActivity?.launch(Intent().goToSearch(requireContext()))
    }

    private fun onResultSearchActivity() {
        resultSearchActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val query = result.data?.getStringExtra(SEARCH_QUERY_KEY) ?: String()
                    Toast.makeText(requireContext(), query, Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        resultSearchActivity = null
    }
}