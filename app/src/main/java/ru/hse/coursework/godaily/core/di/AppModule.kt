package ru.hse.coursework.godaily.core.di

import android.content.Context
import android.content.SharedPreferences
import coil3.ImageLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.core.domain.apiprocessing.SafeApiCaller
import ru.hse.coursework.godaily.core.domain.location.LocationService
import ru.hse.coursework.godaily.core.domain.service.CropProfilePhotoService
import ru.hse.coursework.godaily.core.domain.service.CropRoutePreviewService
import ru.hse.coursework.godaily.core.domain.service.UuidService
import ru.hse.coursework.godaily.core.security.VerificationManager
import ru.hse.coursework.godaily.core.tracking.TrackingService
import ru.hse.coursework.godaily.ui.errorsprocessing.ErrorHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocationService(@ApplicationContext context: Context): LocationService {
        return LocationService(context)
    }

    @Provides
    @Singleton
    fun provideUUIDService(): UuidService {
        return UuidService()
    }

    @Provides
    @Singleton
    fun provideSafeApiCaller(): SafeApiCaller {
        return SafeApiCaller()
    }

    @Provides
    @Singleton
    fun provideTrackingService(): TrackingService {
        return TrackingService()
    }

    @Provides
    @Singleton
    fun provideErrorHandler(
        @ApplicationContext context: Context,
        verificationManager: VerificationManager
    ): ErrorHandler {
        return ErrorHandler(context, verificationManager)
    }

    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader {
        return ImageLoader.Builder(context).build()
    }

    @Provides
    @Singleton
    fun provideCropProfilePhotoService(): CropProfilePhotoService {
        return CropProfilePhotoService()
    }

    @Provides
    @Singleton
    fun provideCropRoutePreviewService(): CropRoutePreviewService {
        return CropRoutePreviewService()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    fun provideSearchRadius(@ApplicationContext context: Context): Long {
        return context.resources.getInteger(R.integer.radius_in_metres).toLong()
    }
}
