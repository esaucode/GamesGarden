package com.esaudev.gamesgarden.ui.recycler_view_animations.filter

import com.esaudev.gamesgarden.ui.recycler_view_animations.Feature

interface Filterable {
    fun applyFilter(list: List<Feature>): List<Feature>
}

