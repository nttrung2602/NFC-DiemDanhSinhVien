package com.trungdz.nfcproject.presentation.di

import com.trungdz.nfcproject.data.api.AppDiemDanhApiService
import com.trungdz.nfcproject.data.repository.datasource.AppDiemDanhRemoteDatasource
import com.trungdz.nfcproject.data.repository.datasourceImp.AppDiemDanhRemoteDataSourceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Provides
    @Singleton
    fun providesIAppFoodRemoteDataSource(appFoodApiService: AppDiemDanhApiService): AppDiemDanhRemoteDatasource {
        return AppDiemDanhRemoteDataSourceImp(appFoodApiService)
    }
}