package com.hj.smalldecision.repo

import com.hj.smalldecision.dao.ChooseModuleDao
import com.hj.vo.ChooseModule
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChooseModuleRepo @Inject constructor(
    private val chooseModuleDao: ChooseModuleDao
) {
    suspend fun getChooseModule(id: Int) = chooseModuleDao.getChooseModule(id)

    suspend fun addChooseModule(chooseModule: ChooseModule) = chooseModuleDao.insert(chooseModule)
}