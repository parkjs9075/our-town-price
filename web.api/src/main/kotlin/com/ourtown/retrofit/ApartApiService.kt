package com.ourtown.retrofit

import com.ourtown.retrofit.model.ApartResponse
import com.ourtown.retrofit.model.ApartSalesRequest
import com.ourtown.retrofit.model.ApartSalesResponse
import org.springframework.web.bind.annotation.RequestParam
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface  ApartApiService {
    @GET("/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade")
    fun getData(
        @Query("LAWD_CD") LAWD_CD: String,
        @Query("DEAL_YMD") DEAL_YMD: String,
        @Query("serviceKey") serviceKey: String
    ): Call<ApartSalesResponse>

    @GET("/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcSilvTrade")
    fun getSubdivisions(
        @Query("LAWD_CD") LAWD_CD: String,
        @Query("DEAL_YMD") DEAL_YMD: String,
        @Query("serviceKey") serviceKey: String
    ): Call<ApartSalesResponse>
}
