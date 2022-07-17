package com.esaudev.gamesgarden.ui.tiktok

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.esaudev.gamesgarden.R
import com.esaudev.gamesgarden.databinding.ActivityTiktokBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TiktokActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTiktokBinding

    lateinit var adapter:VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTiktokBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /**set fullscreen*/
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        /**set database*/
        val mDataBase = Firebase.database.getReference("videos")

        val options = FirebaseRecyclerOptions.Builder<VideoModel>()
            .setQuery(mDataBase,VideoModel::class.java)
            .build()
        /**set adapter*/
        adapter = VideoAdapter(options)
        binding.singleVideoPager.adapter = VideoAdapter(options)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}