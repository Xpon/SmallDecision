package com.hj.smalldecision.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hj.smalldecision.vo.ChooseModule
import com.hj.smalldecision.vo.Kind

object DataUtils {

    fun getDefaultChooseModule(): ChooseModule{
        var kinds = ArrayList<Kind>()
        kinds.add(Kind(0, "徽菜", true))
        kinds.add(Kind(1, "麻辣小龙虾", true))
        kinds.add(Kind(2, null, false))
        kinds.add(Kind(3, "日式", true))
        kinds.add(Kind(4, "牛排", true))
        kinds.add(Kind(5, null, false))
        kinds.add(Kind(6, null, false))
        kinds.add(Kind(7, "韩式", true))
        kinds.add(Kind(8, "兰州拉面", true))
        kinds.add(Kind(9, null, false))
        kinds.add(Kind(10, "印度菜", true))
        kinds.add(Kind(11, "自己做饭", true))
        kinds.add(Kind(12, null, false))
        kinds.add(Kind(13, null, false))
        kinds.add(Kind(14, "川菜", true))
        kinds.add(Kind(15, "新加坡黑胡椒蟹", true))
        kinds.add(Kind(16, null, false))
        kinds.add(Kind(17, "沙县小吃", true))
        kinds.add(Kind(18, "", true))
        kinds.add(Kind(19, null, false))
        kinds.add(Kind(20, "减肥", true))
        kinds.add(Kind(21, "湘菜", true))
        kinds.add(Kind(22, "米其林三星唐阁粤式餐厅", true))
        kinds.add(Kind(23, null, false))
        kinds.add(Kind(24, "重庆火锅", true))
        kinds.add(Kind(25, "海鲜", true))
        kinds.add(Kind(26, "素食", true))
        var content = Gson().toJson(kinds)
        return ChooseModule(1, "吃啥？", content)
    }

    fun getDefaultCustomKinds(): List<Kind>{
        var kinds = ArrayList<Kind>()
        kinds.add(Kind(0, null, false))
        kinds.add(Kind(1, null, false))
        kinds.add(Kind(2, null, false))
        kinds.add(Kind(3, null, false))
        kinds.add(Kind(4, null, false))
        kinds.add(Kind(5, null, false))
        kinds.add(Kind(6, null, false))
        kinds.add(Kind(7, null, false))
        kinds.add(Kind(8, null, false))
        kinds.add(Kind(9, null, false))
        kinds.add(Kind(10, null, false))
        kinds.add(Kind(11, null, false))
        kinds.add(Kind(12, null, false))
        kinds.add(Kind(13, null, false))
        kinds.add(Kind(14, null, false))
        kinds.add(Kind(15, null, false))
        kinds.add(Kind(16, null, false))
        kinds.add(Kind(17, null, false))
        kinds.add(Kind(18, null, false))
        kinds.add(Kind(19, null, false))
        kinds.add(Kind(20, null, false))
        kinds.add(Kind(21, null, false))
        kinds.add(Kind(22, null, false))
        kinds.add(Kind(23, null, false))
        kinds.add(Kind(24, null, false))
        kinds.add(Kind(25, null, false))
        kinds.add(Kind(26, null, false))
        return kinds
    }

    fun getKinds(kindString: String): ArrayList<Kind>{
        val type = object : TypeToken<ArrayList<Kind>>() {}.type
        return Gson().fromJson(kindString,type)
    }

    fun makeKindsToString(kinds: ArrayList<Kind>): String{
        return Gson().toJson(kinds)
    }
}