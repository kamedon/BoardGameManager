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
import com.kamedon.boardgamemanager.infra.api.RakutenApi
import com.kamedon.boardgamemanager.infra.camera.CameraClient
import com.kamedon.boardgamemanager.infra.repository.*
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by kamei.hidetoshi on 2016/10/21.
 */
@Module
class InfraModule() {
    /*
     * API
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        var builder = OkHttpClient.Builder()
        builder.connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return builder.addInterceptor(loggingInterceptor).build()
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRakutenApi(okHttpClient: OkHttpClient): RakutenApi = Retrofit.Builder()
            .baseUrl(BuildConfig.RAKUTEN_API_ENDPOINT)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RakutenApi::class.java)

    /*
     * Rrepository
     */
    @Provides
    @Singleton
    fun provideLoginRepository(context: Context): ILoginRepository = LoginRepository(LoginPrefs.get(context))

    @Provides
    @Singleton
    fun provideRakutenRepository(api: RakutenApi): IRakutenRepository = RakutenRepository(api)

    @Provides
    @Singleton
    fun provideBoardGameRepository(database: FirebaseDatabase): IBoardGameRepository = BoardGameRepository(database)

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
