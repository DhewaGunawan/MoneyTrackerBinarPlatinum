package com.example.binarchplatinum.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.binarchplatinum.data.room.dao.CategoriesDao
import com.example.binarchplatinum.data.room.dao.ExpensesDao
import com.example.binarchplatinum.data.room.entity.Category
import com.example.binarchplatinum.data.room.entity.Expenses
import com.example.binarchplatinum.utils.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Database(entities = [Expenses::class, Category::class], version = 1, exportSchema = true)
/*@TypeConverters(Converters::class)*/
abstract class AppDatabase : RoomDatabase() {
    abstract fun expensesDao(): ExpensesDao
    abstract fun categoriesDao(): CategoriesDao

    companion object {
        //TODO name database?
        private const val DB_NAME = "Expenses.db"


        //menyimpan instance Database
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
        }
    }

    private fun prepopulateCategory(): List<Category> {
        return mutableListOf(
            Category(categoryId = 1, categoryName = "Shopping"),
            Category(categoryId = 2, categoryName = "Sport"),
            Category(categoryId = 3, categoryName = "Food & Drink"),
            Category(categoryId = 4, categoryName = "Transportation"),
            Category(categoryId = 5, categoryName = "Groceries"),
            Category(categoryId = 6, categoryName = "Home Bills"),
            Category(categoryId = 7, categoryName = "Entertainment"),
        )
    }
}