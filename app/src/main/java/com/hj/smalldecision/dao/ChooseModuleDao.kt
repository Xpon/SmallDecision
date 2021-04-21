package com.hj.smalldecision.dao


import androidx.room.Dao
import androidx.room.Query
import com.hj.vo.ChooseModule


@Dao
interface ChooseModuleDao: BaseDao<ChooseModule> {

    @Query("SELECT * FROM choose_module WHERE id = :id")
    suspend fun getChooseModule(id: Int): ChooseModule

}