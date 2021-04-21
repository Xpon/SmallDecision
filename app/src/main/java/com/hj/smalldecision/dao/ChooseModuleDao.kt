package com.hj.smalldecision.dao


import androidx.room.Dao
import androidx.room.Query
import com.hj.smalldecision.vo.ChooseModule


@Dao
interface ChooseModuleDao: BaseDao<ChooseModule> {

    @Query("SELECT * FROM choose_module WHERE id = :id")
    suspend fun getChooseModule(id: Int): ChooseModule

    @Query("SELECT * FROM choose_module")
    suspend fun getChooseModules(): List<ChooseModule>

}