package com.vaxinterview.bookman.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.vaxinterview.bookman.data.local.BookDao
import com.vaxinterview.bookman.data.local.BookDatabase
import com.vaxinterview.bookman.data.remote.BookAPIService
import com.vaxinterview.bookman.data.remote.utils.DateAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {


    //Retrofit
    @Provides
    @Singleton
    fun bookService(): BookAPIService = Retrofit.Builder()
        .baseUrl("https://gist.githubusercontent.com/android-test-vaxcare/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))                    //takes care of most basic mapping
        .build()
        .create(BookAPIService::class.java)

    //have to add custom moshi for date parsing and perhaps bigDecimal if I get to it
    val moshi = Moshi.Builder()
        .addLast(DateAdapter())
        .build()


    //Room
    @Provides
    @Singleton
    fun initializeDatabase(@ApplicationContext context: Context): BookDatabase =
        Room.databaseBuilder(context, BookDatabase::class.java, "books").build()


    @Provides
    fun provideBookDao(db: BookDatabase): BookDao {
        return db.bookDao()
    }

}