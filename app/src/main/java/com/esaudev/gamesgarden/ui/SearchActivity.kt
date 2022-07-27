package com.esaudev.gamesgarden.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esaudev.gamesgarden.R
import com.esaudev.gamesgarden.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.buttonSearch.setOnClickListener {
            goBackWithSearch()
        }
    }

    private fun goBackWithSearch() {
        intent.putExtra(SEARCH_QUERY_KEY, binding.searchQuery.text.toString())
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}