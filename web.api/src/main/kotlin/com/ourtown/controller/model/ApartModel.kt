package com.ourtown.controller.model

import com.ourtown.retrofit.model.ItemInfo
import java.time.LocalDate

data class ApartModel(
    val list: List<ApartSalesInfo> = emptyList()
) {
    val apartNames by lazy {
        list.associateBy { it.apartName }.keys
    }
}

data class ApartSalesInfo(
    val dealDate: LocalDate? = null,
    var danji: String = "",
    var beobjeongdong: String = "",
    var sigungu: String = "",
    var areaExclusiveUse: String = "",
    var areaCode: String = "",
    var floor: String = "",
    var gubun: String = "",
    var buildYear: String = "",
    var apartName: String = "",
    var soldAmount: String = ""
) {
    companion object {
        fun createBy(item: ItemInfo, isSubdivision: Boolean) = ApartSalesInfo(
            dealDate = LocalDate.of(item.salesYear, item.month, item.day),
            danji = item.danji,
            beobjeongdong = item.beobjeongdong,
            sigungu = item.sigungu,
            areaExclusiveUse = item.areaExclusiveUse,
            apartName = if (isSubdivision) item.danji else item.apartName,
            floor = item.floor,
            gubun = item.gubun,
            soldAmount = item.salesAmount,
            buildYear = item.buildYear
        )
    }
}
