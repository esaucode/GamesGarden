package com.esaudev.gamesgarden.ui.recycler_view_animations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.esaudev.gamesgarden.databinding.FragmentColorListBinding
import com.esaudev.gamesgarden.di.DataModule.ColorList
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

        colorsAdapter.submitList(colorList)

        binding.rvColors.apply {
            this.adapter = colorsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}