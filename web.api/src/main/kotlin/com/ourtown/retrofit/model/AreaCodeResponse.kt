package com.ourtown.retrofit.model

data class AreaCodeResponse(
    val regcodes: List<AreaCodeModel>
) {
    fun toModels() = regcodes.map {
        AreaCodeModel(
            code = it.code,
            name = it.name
        )
    }
}

data class AreaCodeModel(
    val code: String,
    val name: String
)
