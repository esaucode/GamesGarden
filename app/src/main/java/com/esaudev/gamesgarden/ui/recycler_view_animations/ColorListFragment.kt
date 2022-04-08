package com.esaudev.gamesgarden.ui.recycler_view_animations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.esaudev.gamesgarden.databinding.FragmentColorListBinding
import com.esaudev.gamesgarden.di.DataModule.ColorList
import com.esaudev.gamesgarden.ui.home.adapters.FeaturesAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ColorListFragment : Fragment() {

    @Inject
    @ColorList
    lateinit var colorList: List<Int>

    private var _binding: FragmentColorListBinding? = null
    private val binding get() = _binding!!

    private val colorsAdapter = ColorsAdapter()
    private val featuresAdapter = FeaturesAdapter()

    private val featuresList = listOf(
        Feature("Feature 1", 1),
        Feature("Feature 2", 2),
        Feature("Feature 3", 3),
        Feature("Feature 4", 4),
        Feature("Feature 5", 5),
        Feature("Feature 6", 6),
        Feature("Feature 7", 7),
        Feature("Feature 8", 8),
        Feature("Feature 9", 9),
        Feature("Feature 10", 10)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentColorListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init(){

        featuresAdapter.submitList(featuresList)

        binding.rvColors.apply {
            this.adapter = featuresAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}