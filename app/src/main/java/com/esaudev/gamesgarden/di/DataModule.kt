package com.esaudev.gamesgarden.di

import android.graphics.Color
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @ColorList
    @Singleton
    @Provides
    fun provideColorList(): List<Int>{
        return listOf(
            Color.BLUE,
            Color.RED,
            Color.YELLOW,
            Color.GREEN,
            Color.CYAN,
            Color.MAGENTA,
            Color.DKGRAY
        )
    }

    @FeaturesList
    @Singleton
    @Provides
    fun provideFeaturesList(): List<String>{
        return listOf(
            "Navigation states"
        )
    }


    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ColorList
    annotation class FeaturesList

}