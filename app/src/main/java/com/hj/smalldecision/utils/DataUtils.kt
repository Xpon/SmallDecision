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
        var content = Gson().toJson(kinds)
        return ChooseModule(1, "吃啥？", content)
    }

    fun getDefaultChooseModule_1(): ChooseModule{
        var kinds = ArrayList<Kind>()
        kinds.add(Kind(0, "做过最丢脸的事", true))
        kinds.add(Kind(1, "初吻年龄", true))
        kinds.add(Kind(2, "初恋对象是谁", true))
        kinds.add(Kind(3, "谈过几次恋爱", true))
        kinds.add(Kind(4, null, false))
        kinds.add(Kind(5, null, false))
        kinds.add(Kind(6, null, false))
        kinds.add(Kind(7, null, false))
        kinds.add(Kind(8, "目前最大的愿望", true))
        kinds.add(Kind(9, null, false))
        kinds.add(Kind(10, "你最害怕什么", true))
        kinds.add(Kind(11, "你最郁闷的外号是", true))
        kinds.add(Kind(12, null, false))
        kinds.add(Kind(13, null, false))
        kinds.add(Kind(14, "你最爱的人是", true))
        kinds.add(Kind(15, "我在你眼里是什么样", true))
        kinds.add(Kind(16, null, false))
        kinds.add(Kind(17, "最想感谢的人是谁", true))
        kinds.add(Kind(18, "", true))
        kinds.add(Kind(19, null, false))
        kinds.add(Kind(20, null, false))
        kinds.add(Kind(21, "做过最疯狂的事", true))
        var content = Gson().toJson(kinds)
        return ChooseModule(2, "大冒险", content)
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