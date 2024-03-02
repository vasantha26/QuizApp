package com.example.quizapp

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlin.concurrent.Volatile


@Database(entities = [Result::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
        abstract fun questionDao(): QuestionDao

        companion object {
            private const val DATABASE_NAME = "Cat"
            @Volatile
            private var INSTANCE: AppDatabase? = null

            fun getInstance(context: Context): AppDatabase {
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .addCallback(callback)
                        .build()
                    INSTANCE = instance
                    instance
                }
            }

            private val callback = object : Callback() {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    PopulateDbAsync().execute()
                }
            }

            private class PopulateDbAsync : AsyncTask<Void, Void, Void>() {

                @Deprecated("Deprecated in Java", ReplaceWith("null"))
                override fun doInBackground(vararg voids: Void): Void? {
                    return null
                }
            }
        }
    }
