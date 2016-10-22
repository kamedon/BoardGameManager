package com.kamedon.boardgamemanager.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.kamedon.boardgamemanager.BuildConfig
import com.kamedon.boardgamemanager.infra.camera.CameraClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by kamei.hidetoshi on 2016/10/21.
 */
@Module
class InfraModule() {

    @Provides
    @Singleton
    fun provideBarcodeDetector(context: Context): BarcodeDetector = BarcodeDetector.Builder(context).setBarcodeFormats(Barcode.ALL_FORMATS).build()

    @Provides
    @Singleton
    fun provideCameraClient(): CameraClient = CameraClient

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseDatabase() = FirebaseDatabase.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseStorage() = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideGoogleSignInOptions() = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(BuildConfig.GOOGLE_API_KEY).requestEmail().build()
}
