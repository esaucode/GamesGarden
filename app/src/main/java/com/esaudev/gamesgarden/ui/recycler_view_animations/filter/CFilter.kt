package com.esaudev.gamesgarden.ui.recycler_view_animations.filter

import com.esaudev.gamesgarden.ui.recycler_view_animations.Feature

class CFilter(): Filterable, Filter<Feature>() {
    override fun applyFilter(list: List<Feature>): List<Feature> {
        return list.filter { it.name.contains("C") }
    }
}


