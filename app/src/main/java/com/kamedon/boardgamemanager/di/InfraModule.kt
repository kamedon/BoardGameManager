package com.kamedon.boardgamemanager.di

import android.content.Context
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.kamedon.boardgamemanager.BuildConfig
import com.kamedon.boardgamemanager.infra.camera.CameraClient
import com.kamedon.boardgamemanager.infra.repository.ILoginRepository
import com.kamedon.boardgamemanager.infra.repository.LoginPrefs
import com.kamedon.boardgamemanager.infra.repository.LoginRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by kamei.hidetoshi on 2016/10/21.
 */
@Module
class InfraModule() {
    /*
     * Rrepository
     */
    @Provides
    @Singleton
    fun provideLoginRepository(context: Context): ILoginRepository = LoginRepository(LoginPrefs.get(context))

    /*
     * Camera
     */

    @Provides
    @Singleton
    fun provideBarcodeDetector(context: Context): BarcodeDetector = BarcodeDetector.Builder(context).setBarcodeFormats(Barcode.ALL_FORMATS).build()

    @Provides
    @Singleton
    fun provideCameraClient(): CameraClient = CameraClient

    /*
     * Firebase
     */
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
    fun provideGoogleSignInOptions(): GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_API_KEY)
            .requestEmail()
            .build()

    @Provides
    fun provideGoogleSignInClient(context: Context, options: GoogleSignInOptions) = GoogleApiClient.Builder(context)
            .addApi(Auth.GOOGLE_SIGN_IN_API, options)
            .build()
}
