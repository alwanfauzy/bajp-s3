package com.alwan.bajpsubmission3.di

import android.content.Context
import com.alwan.bajpsubmission3.data.CatalogueRepository
import com.alwan.bajpsubmission3.data.source.local.LocalDataSource
import com.alwan.bajpsubmission3.data.source.local.room.CatalogueDatabase
import com.alwan.bajpsubmission3.data.source.remote.RemoteDataSource
import com.alwan.bajpsubmission3.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): CatalogueRepository {
        val database = CatalogueDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.catalogueDao())
        val appExecutors = AppExecutors()

        return CatalogueRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}