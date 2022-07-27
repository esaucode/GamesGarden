package com.esaudev.gamesgarden.ui.navigation_states.recyclerview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import androidx.viewpager2.widget.ViewPager2
import com.esaudev.gamesgarden.R
import com.esaudev.gamesgarden.databinding.FragmentBBinding
import com.esaudev.gamesgarden.model.Player
import com.esaudev.gamesgarden.model.Sample
import com.esaudev.gamesgarden.ui.navigation_states.BViewModel

class BFragment : Fragment() {

    private var _binding: FragmentBBinding? = null
    private val binding: FragmentBBinding
        get() = _binding!!

    private val viewModel: BViewModel by viewModels()
    private val args: BFragmentArgs by navArgs()
    private val playerListAdapter = PlayerListAdapter()

    private var sampleList: MutableList<Sample> = mutableListOf()

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

        initRecyclerView()
        initObservers()
        initListeners()

        getSampleData()

        binding.infiniteViewPager.apply {
            adapter = InfiniteRecyclerAdapter(sampleList)
        }

        binding.infiniteViewPager.currentItem = 1

        onInfinitePageChangeCallback(sampleList.size + 2)
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


    private fun initRecyclerView() {
        with(binding.rvPlayers) {
            adapter = playerListAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            overScrollMode = View.OVER_SCROLL_NEVER
            setHasFixedSize(false)
            LinearSnapHelper().attachToRecyclerView(this)
        }

        val playerList = listOf(
            "Son",
            "CR7",
            "Messi",
            "Ronaldo",
            "Vinicius",
            "Edson Alvarez",
            "Cavani",
            "Luis Suarez"
        ).map {
            Player(name = it)
        }

        playerListAdapter.submitList(playerList)

        binding.rvPlayers.scrollToPosition((Int.MAX_VALUE/2)-((Int.MAX_VALUE)/2)%playerListAdapter.currentList.size)
    }

    private fun initObservers() {
        viewModel.argument.observe(viewLifecycleOwner) { }
    }

    private fun initListeners(){
        playerListAdapter.setPlayerClickListener {
            Toast.makeText(requireContext(), it.name, Toast.LENGTH_SHORT).show()
        }
    }
}