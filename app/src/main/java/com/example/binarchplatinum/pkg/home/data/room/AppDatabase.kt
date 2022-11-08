package com.example.binarchplatinum.pkg.home.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.binarchplatinum.pkg.home.data.room.dao.CategoriesDao
import com.example.binarchplatinum.pkg.home.data.room.entity.Category
import com.example.binarchplatinum.pkg.home.data.room.entity.DetailExpenditure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Database(entities = [DetailExpenditure::class, Category::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
//    abstract fun detailExpendituresDao(): DetailExpenditureDao
    abstract fun categoriesDao(): CategoriesDao

    companion object {
        private const val DB_NAME = "Expenditure.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseSeederCallback(context))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}


class DatabaseSeederCallback(private val context: Context) : RoomDatabase.Callback() {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        scope.launch {
            AppDatabase.getInstance(context).categoriesDao().insertCategories(prepopulateCategory())
//            AppDatabase.getInstance(context).detailExpendituresDao().insertDetailExpenditures(prepopulateDetailExpenditures())
        }
    }

    private fun prepopulateCategory(): List<Category> {
        return mutableListOf(
            Category(categoryId = 1, categoryName = "Shopping"),
            Category(categoryId = 2, categoryName = "Sports"),
            Category(categoryId = 3, categoryName = "Food $ Drink"),
            Category(categoryId = 4, categoryName = "Transportation"),
            Category(categoryId = 5, categoryName = "Groceries"),
            Category(categoryId = 6, categoryName = "Home Bills"),
            Category(categoryId = 7, categoryName = "Entertainment"),
        )
    }

//    private fun prepopulateDetailExpenditures(): List<Note> {
//        return mutableListOf(
//            Note(categoryId = 1, title = "My Diary 1", body = "Loren ipsum set dolot almet"),
//            Note(categoryId = 1, title = "My Diary 2", body = "Loren ipsum set dolot almet"),
//            Note(categoryId = 2, title = "My Diary 3", body = "Loren ipsum set dolot almet"),
//            Note(categoryId = 2, title = "My Diary 4", body = "Loren ipsum set dolot almet"),
//            Note(categoryId = 3, title = "My Diary 5", body = "Loren ipsum set dolot almet"),
//            Note(categoryId = 3, title = "My Diary 6", body = "Loren ipsum set dolot almet"),
//        )
//    }
}