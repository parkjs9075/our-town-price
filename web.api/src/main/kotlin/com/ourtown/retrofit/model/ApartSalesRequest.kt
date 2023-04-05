package com.ourtown.retrofit.model

data class ApartSalesRequest(
    val page: Int = 1,
    val size: Int = 20,
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
        const val KEY = "A3PCwVME1WkFxWJWS7Intl1fPaRZB0U04F5AdjLtzqGTJ7XdsK+wQm37xhBCPTyhdjXynoaQASmj8YYzF1gu+w=="
    }
}
