package com.kamedon.boardgamemanager.di

import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.firebase.auth.FirebaseAuth
import com.kamedon.boardgamemanager.domain.usecase.*
import com.kamedon.boardgamemanager.infra.camera.CameraClient
import com.kamedon.boardgamemanager.infra.repository.IBoardGameRepository
import com.kamedon.boardgamemanager.infra.repository.ILoginRepository
import com.kamedon.boardgamemanager.infra.repository.IRakutenRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by kamei.hidetoshi on 2016/10/21.
 */
@Module
class PresentationModule() {

    @Provides
    @Singleton
    fun provideBarcodeUseCase(detector: BarcodeDetector): IBarcodeUseCase = BarcodeUseCase(detector)

    @Provides
    @Singleton
    fun provideOnePreviewUseCase(client: CameraClient): ICameraOnePreviewUserCase = CameraOnePreviewUserCase(client)

    @Provides
    @Singleton
    fun provideSignInUseCase(auth: FirebaseAuth, client: GoogleApiClient, repository: ILoginRepository): ISignInUseCase = SignInUseCase(auth, client, repository)

    // No @Singleton
    @Provides
    fun provideSecureUseCase(auth: FirebaseAuth, repository: ILoginRepository): ISecureUseCase = SecureUseCase(auth, repository)

    @Provides
    @Singleton
    fun provideBoardGameUseCase(repository: IBoardGameRepository): IBoardGameUseCase = BoardGameUseCase(repository)

    @Provides
    @Singleton
    fun provideSearchUseCase(repository: IRakutenRepository): ISearchUseCase = SearchUseCase(repository)


}
