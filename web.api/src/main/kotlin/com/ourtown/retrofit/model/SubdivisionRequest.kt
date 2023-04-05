package com.ourtown.retrofit.model

data class SubdivisionsRequest(
    val code: String,
    val dealYmd : String
) {
    fun validate() {
        if (code.isBlank()) {
            throw Exception("Invalid parameter")
        }

        if (dealYmd.isBlank()) {
            throw Exception("Invalid parameter")
        }
    }

    companion object {
        const val KEY = "Jrz2abzS5ivDmhg9LuMf5sIgK5YRHDXKJtC2GH/nkAdZgzNCYcDIulEom1Dsvm9Ry9UccXY/xDMuPnbWc5v7OQ=="
    }
}
