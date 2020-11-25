package com.ourtown.retrofit.model

data class ApartSalesRequest(
        val serviceKey: String,
        val LAWD_CD: String,
        val DEAL_YMD : String
) {

    companion object {
        fun of(code: String, ymd: String) = ApartSalesRequest(
            serviceKey = KEY,
            LAWD_CD = code,
            DEAL_YMD = ymd
        )
        const val KEY = "Jrz2abzS5ivDmhg9LuMf5sIgK5YRHDXKJtC2GH/nkAdZgzNCYcDIulEom1Dsvm9Ry9UccXY/xDMuPnbWc5v7OQ=="
    }
}