package com.hj.smalldecision.inject

import com.hj.smalldecision.db.SmallDecisionDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
    RoomDatabaseModule::class,
    DatabaseDaoModule::class
])
class DatabaseModule

@Module
class RoomDatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(): SmallDecisionDatabase = SmallDecisionDatabase.get()
}

@Module
class DatabaseDaoModule {

    @Provides
    fun provideChooseModuleDao(db: SmallDecisionDatabase) = db.chooseModuleDao()

}