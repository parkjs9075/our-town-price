package com.ourtown.controller
import com.ourtown.retrofit.ApartApiService
import com.ourtown.retrofit.model.ApartSalesRequest
import com.ourtown.retrofit.model.ApartSalesResponse
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

@RequestMapping("/main")
@RestController
class OurtownController {
    @GetMapping
    fun main(): String {
        val apiUrl = "http://openapi.molit.go.kr"
        val retrofit = Retrofit.Builder()
                .baseUrl(apiUrl)
                //.addConverterFactory(SimpleXmlConverterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
        val api = retrofit.create(ApartApiService::class.java)
        val call = api.getData("28177", "202009", ApartSalesRequest.KEY)
        val response = call.execute()
        if (response.isSuccessful) {
            println(response.body())
        }

        return ""

    }
}
