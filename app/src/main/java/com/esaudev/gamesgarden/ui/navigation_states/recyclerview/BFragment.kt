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
import com.esaudev.gamesgarden.R
import com.esaudev.gamesgarden.databinding.FragmentBBinding
import com.esaudev.gamesgarden.model.Player
import com.esaudev.gamesgarden.ui.navigation_states.BViewModel

class BFragment : Fragment() {

    private var _binding: FragmentBBinding? = null
    private val binding: FragmentBBinding
        get() = _binding!!

    private val viewModel: BViewModel by viewModels()
    private val args: BFragmentArgs by navArgs()
    private val playerListAdapter = PlayerListAdapter()

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
    }

    private fun initRecyclerView() {
        with(binding.rvPlayers) {
            adapter = playerListAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            overScrollMode = View.OVER_SCROLL_NEVER
            setHasFixedSize(false)
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