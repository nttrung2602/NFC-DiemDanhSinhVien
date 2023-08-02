package com.trungdz.nfcproject.presentation.di


import com.trungdz.nfcproject.data.repository.IRepositoryImp
import com.trungdz.nfcproject.data.repository.datasource.AppDiemDanhRemoteDatasource
import com.trungdz.nfcproject.domain.repository.IRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun providesIRepository(iAppFoodRemoteDatasource: AppDiemDanhRemoteDatasource): IRepository {
        return IRepositoryImp(iAppFoodRemoteDatasource)
    }
}