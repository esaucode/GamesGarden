package com.esaudev.gamesgarden.ui.recycler_view_animations.filter

import com.esaudev.gamesgarden.ui.recycler_view_animations.Feature

class FilterManager {

    fun applyFilters(list: List<Feature>, vararg filters: Filterable): List<Feature> {

        val filteredList = mutableListOf<Feature>()
        for (filter in filters){
            filteredList.addAll(filter.applyFilter(list))
        }

        return filteredList
    }

}

