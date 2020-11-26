package com.ourtown.retrofit.model

import org.simpleframework.xml.*
import retrofit2.http.Field
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "response")
class ApartResponse(
    @field:Element(name = "header", required = false)
    var header: HeaderResponse = HeaderResponse(),
    @field:Element(name = "body", required = false)
    var body: BodyResponse = BodyResponse()
)

@Root(name = "header", strict = false)
data class HeaderResponse(
    @field: Element(name = "resultCode", required = false)
    var resultCode: String = "",
    @field: Element(name = "resultMsg", required = false)
    var resultMsg: String = ""
)

@Root(name = "body", strict = false)
data class BodyResponse(
    @field:Element(name="items", required = false)
    var items: ItemResponse = ItemResponse(),
    @field:Element(name="numOfRows", required = false)
    var numOfRows: Int = 0,
    @field:Element(name="pageNo", required = false)
    var pageNo: Int = 1,
    @field:Element(name="totalCount", required = false)
    var totalCount: Int = 0
)

@Root(name = "items", strict = false)
data class ItemResponse(
    @field:ElementList(entry = "item", inline = true, required = false)
    var item: List<ItemInfo>? = null
)

@Root(name = "item")
data class ItemInfo(
    @field: Element(name = "년", required = false)
    var salesYear: String? = "",

    @field: Element(name = "단지", required = false)
    var danji: String = "",

    @field: Element(name = "법정동", required = false)
    var beobjeongdong: String = "",

    @field: Element(name = "시군구", required = false)
    var sigungu: String = "",

    @field: Element(name = "월", required = false)
    var month: String = "",

    @field: Element(name = "일", required = false)
    var day: String = "",

    @field: Element(name = "전용면적", required = false)
    var areaExclusiveUse: String = "",

    @field: Element(name = "지번", required = false)
    var jibun: String = "",

    @field: Element(name = "지역코드", required = false)
    var areaCode: String = "",

    @field: Element(name = "층", required = false)
    var floor: String = "",

    @field: Element(name = "구분", required = false)
    var gubun: String = "",

    @field: Element(name = "건축년도", required = false)
    var buildYear: String = "",

    @field: Element(name = "아파트", required = false)
    var apartName: String = ""
) {
    @field: Element(name = "거래금액", required = false)
    var salesAmount: String = ""
        get() = field.replace(" ", "")
}
