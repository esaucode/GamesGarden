package com.esaudev.gamesgarden.ui.recycler_view_animations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.gamesgarden.ui.recycler_view_animations.filter.AFilter
import com.esaudev.gamesgarden.ui.recycler_view_animations.filter.FilterManager
import com.esaudev.gamesgarden.ui.recycler_view_animations.filter.Filterable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ColorListViewModel @Inject constructor(

): ViewModel() {

    private val _list = MutableLiveData<List<Feature>>()
    val list: LiveData<List<Feature>>
        get() = _list

    private val featuresList = listOf(
        Feature("Feature A", 1),
        Feature("Feature A", 2),
        Feature("Feature A", 3),
        Feature("Feature B", 4),
        Feature("Feature B", 5),
        Feature("Feature B", 6),
        Feature("Feature C", 7),
        Feature("Feature C", 8),
        Feature("Feature C", 9),
        Feature("Feature D", 10)
    )

    init {
        initColorListFragment()
    }

    private fun initColorListFragment(){
        viewModelScope.launch {
            _list.value = featuresList
        }
    }

    fun triggerTriggers(
        vararg filters: Filterable
    ) {
        viewModelScope.launch {
            _list.value = FilterManager().applyFilters(featuresList, *filters)
        }
    }

}