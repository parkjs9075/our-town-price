package com.ourtown.retrofit.model

import org.simpleframework.xml.*
import retrofit2.http.Field

data class ApartSalesResponse(
    @Element(name = "response")
    val response: ApartResponse
)

@Root(name = "response")
class ApartResponse(
    @field:Element(name = "header", required = false)
    var header: HeaderResponse,
    @field:Element(name = "body", required = false)
    var body: BodyResponse
)

@Root(name = "header", strict = false)
data class HeaderResponse(
    @field: Element(name = "resultCode", required = false)
    var resultCode: String? = null,
    @field: Element(name = "resultMsg", required = false)
    var resultMsg: String? = null
)

@Root(name = "body", strict = false)
data class BodyResponse(
    @field:Element(name="items", required = false)
    var items: ItemResponse? = null
)

@Root(name = "items", strict = false)
data class ItemResponse(
    @ElementList(name = "item", inline = true)
    var item: List<ItemInfo>? = emptyList()
)

@Root(name = "item")
data class ItemInfo(
    @field: Element(name = "거래금액", required = false)
    var salesAmount: String? = null
)
