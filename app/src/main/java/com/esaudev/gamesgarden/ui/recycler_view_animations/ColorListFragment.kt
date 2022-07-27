package com.esaudev.gamesgarden.ui.recycler_view_animations

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.esaudev.gamesgarden.R
import com.esaudev.gamesgarden.databinding.FragmentColorListBinding
import com.esaudev.gamesgarden.di.DataModule.ColorList
import com.esaudev.gamesgarden.model.Sample
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
    private val viewModel: ColorListViewModel by viewModels()


    private var sampleList: MutableList<Sample> = mutableListOf()

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


        binding.rvColors.apply {
            this.adapter = featuresAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }


        val snapHelper = LinearSnapHelper().apply {
            attachToRecyclerView(binding.rvColors)
        }

        binding.rvColors.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    AbsListView.OnScrollListener.SCROLL_STATE_IDLE -> {
                        val test = (binding.rvColors.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                        val position = (test%featuresAdapter.currentList.size)
                        val currentItem = featuresAdapter.currentList[position]
                        //Toast.makeText(requireContext(), currentItem.toString(), Toast.LENGTH_SHORT).show()
                        Log.d("test_scroll", "IDLE $currentItem")
                    }

                    AbsListView.OnScrollListener.SCROLL_STATE_FLING -> {
                        val test = (binding.rvColors.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                        val position = (test%featuresAdapter.currentList.size)
                        val currentItem = featuresAdapter.currentList[position]
                        Log.d("test_scroll", "FLING $currentItem")
                    }
                    else -> {
                        val test = (binding.rvColors.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                        val position = (test%featuresAdapter.currentList.size)
                        val currentItem = featuresAdapter.currentList[position]
                        Log.d("test_scroll", "TOUCH/OTHER $currentItem")
                    }
                }
            }
        })


        getSampleData()

        with(binding) {
            infiniteViewPager.apply {
                adapter = InfiniteRecyclerAdapter(sampleList)
                currentItem = 1
            }
        }

        onInfinitePageChangeCallback(sampleList.size + 2)

        initObservable()
        //initListeners()
        initAnimation()
    }

    private fun getSampleData() {
        sampleList.add(Sample(1, "#91C555"))
        sampleList.add(Sample(2, "#F48E37"))
        sampleList.add(Sample(3, "#FF7B7B"))
    }

    private fun onInfinitePageChangeCallback(listSize: Int) {
        binding.infiniteViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    when (binding.infiniteViewPager.currentItem) {
                        listSize - 1 -> binding.infiniteViewPager.setCurrentItem(1, false)
                        0 -> binding.infiniteViewPager.setCurrentItem(listSize - 2, false)
                    }
                }
            }
        })
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
        binding.rvColors.scrollToPosition((Int.MAX_VALUE/2)-(Int.MAX_VALUE/2)%featuresAdapter.currentList.size)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}