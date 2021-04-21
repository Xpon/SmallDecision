package com.hj.smalldecision.ui.home

import androidx.lifecycle.ViewModel
import com.hj.smalldecision.repo.ChooseModuleRepo
import com.hj.vo.ChooseModule
import javax.inject.Inject

class HomeViewModel @Inject constructor(

    private val chooseModuleRepo: ChooseModuleRepo

) : ViewModel() {

    suspend fun getChooseModule(id: Int) = chooseModuleRepo.getChooseModule(id)

    suspend fun addChooseModule(chooseModule: ChooseModule) = chooseModuleRepo.addChooseModule(chooseModule)
}