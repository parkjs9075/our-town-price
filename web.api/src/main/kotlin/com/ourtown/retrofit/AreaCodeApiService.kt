package com.ourtown.retrofit

import com.ourtown.retrofit.model.ApartSalesResponse
import com.ourtown.retrofit.model.AreaCodeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AreaCodeApiService {
    @GET("https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes")
    fun getData(
        @Query("regcode_pattern") regCode: String
    ): Call<AreaCodeResponse>
}
