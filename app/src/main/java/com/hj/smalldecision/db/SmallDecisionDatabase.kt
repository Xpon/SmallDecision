package com.hj.smalldecision.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase.CONFLICT_ROLLBACK
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hj.smalldecision.SmallDecisionApp
import com.hj.smalldecision.dao.ChooseModuleDao
import com.hj.smalldecision.utils.DataUtils
import com.hj.smalldecision.utils.SmallDecisionTypeConverters
import com.hj.smalldecision.vo.ChooseModule

@Database(
    entities = [
        ChooseModule::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(SmallDecisionTypeConverters::class)
abstract class SmallDecisionDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "weight.db"

        private var INSTANCE: SmallDecisionDatabase? = null

        private var callback = object : Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                var chooseModule = DataUtils.getDefaultChooseModule()
                val contentValues = ContentValues()
                contentValues.put("id", chooseModule.id)
                contentValues.put("title", chooseModule.title)
                contentValues.put("content", chooseModule.content)
                db.insert("choose_module", CONFLICT_ROLLBACK, contentValues)

                var chooseModule_1 = DataUtils.getDefaultChooseModule_1()
                val contentValues_1 = ContentValues()
                contentValues_1.put("id", chooseModule_1.id)
                contentValues_1.put("title", chooseModule_1.title)
                contentValues_1.put("content", chooseModule_1.content)
                db.insert("choose_module", CONFLICT_ROLLBACK, contentValues_1)
            }
        }
        fun get(): SmallDecisionDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    SmallDecisionApp.instance,
                    SmallDecisionDatabase::class.java,
                    DB_NAME
                ).addCallback(callback)
                    .build().also {
                        INSTANCE = it
                    }
            }
        }
    }

    abstract fun chooseModuleDao(): ChooseModuleDao

}

