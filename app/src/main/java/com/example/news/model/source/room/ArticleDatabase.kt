package com.example.news.model.source.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.news.model.Article

@Database(
    entities = [Article::class],
    version = 1, exportSchema = false
)

@TypeConverters(Converters::class)
abstract class ArticleDatabase: RoomDatabase() {
    abstract fun getArticleDao() : ArticleDAO

    companion object{
        @Volatile
        private var INSTANCE: ArticleDatabase? = null
        private val LOCK = Any()

        fun getDatabase(context: Context): ArticleDatabase {
            synchronized(LOCK){
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ArticleDatabase::class.java,
                        "task_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}