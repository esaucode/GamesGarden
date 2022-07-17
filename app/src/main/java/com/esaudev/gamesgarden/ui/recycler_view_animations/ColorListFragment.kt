package com.esaudev.gamesgarden.ui.recycler_view_animations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.esaudev.gamesgarden.R
import com.esaudev.gamesgarden.databinding.FragmentColorListBinding
import com.esaudev.gamesgarden.di.DataModule.ColorList
import com.esaudev.gamesgarden.ui.home.adapters.FeaturesAdapter
import com.esaudev.gamesgarden.ui.recycler_view_animations.filter.AFilter
import com.esaudev.gamesgarden.ui.recycler_view_animations.filter.BFilter
import com.esaudev.gamesgarden.ui.recycler_view_animations.filter.CFilter
import com.esaudev.gamesgarden.ui.recycler_view_animations.filter.Filterable
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
    private val viewModel: ColorListViewModel by viewModels()

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
        initObservable()
        //initListeners()
        initAnimation()
    }

    private fun initAnimation() {
        val animZoomOut =
            AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_out)
        //binding.testImage.startAnimation(animZoomOut)
    }

    /*private fun initListeners() {
        binding.cbA.setOnCheckedChangeListener { compoundButton, buttonChecked ->
            val filters = arrayListOf<Filterable>()
            if (buttonChecked) {
                filters.add(AFilter())
            }

            if (binding.cbB.isChecked) {
                filters.add(BFilter())
            }

            if (binding.cbC.isChecked) {
                filters.add(CFilter())
            }

            viewModel.triggerTriggers(*filters.toTypedArray())
        }

        binding.cbB.setOnCheckedChangeListener { compoundButton, buttonChecked ->
            val filters = arrayListOf<Filterable>()
            if (buttonChecked) {
                filters.add(BFilter())
            }

            if (binding.cbA.isChecked) {
                filters.add(AFilter())
            }

            if (binding.cbC.isChecked) {
                filters.add(CFilter())
            }

            viewModel.triggerTriggers(*filters.toTypedArray())
        }

        binding.cbC.setOnCheckedChangeListener { compoundButton, buttonChecked ->
            val filters = arrayListOf<Filterable>()
            if (buttonChecked) {
                filters.add(CFilter())
            }

            if (binding.cbB.isChecked) {
                filters.add(BFilter())
            }

            if (binding.cbA.isChecked) {
                filters.add(AFilter())
            }

            viewModel.triggerTriggers(*filters.toTypedArray())
        }

    }*/

    private fun initObservable(){
        viewModel.list.observe(viewLifecycleOwner) {
            init(it)
        }
    }

    private fun init(list: List<Feature>){

        featuresAdapter.submitList(list)

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