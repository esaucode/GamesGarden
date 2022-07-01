package com.esaudev.gamesgarden.di

import android.graphics.Color
import com.esaudev.gamesgarden.model.Player
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
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

    @Singleton
    @Provides
    fun providePlayer() = Player(name = "CR7")

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