package com.ourtown.controller

data class SigunguModel(
    val siName: String,
    val guList: List<SiGuCodes>
) {
    data class SiGuCodes(
        val code: String,
        val guName: String
    )
}