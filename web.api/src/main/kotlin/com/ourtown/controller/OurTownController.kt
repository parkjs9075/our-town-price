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

@RequestMapping("/sales")
@RestController
class OurTownController {
    @GetMapping
    fun getSoldList(
        @ModelAttribute request: ApartSalesRequest
    ): ApartResponse? {
        request.validate()
        val call = apiServiceForCall(API_URL_FOR_APART)
            .getData(request.code, request.dealYmd, ApartSalesRequest.KEY)
        return call.execute().body() ?: ApartResponse()
    }

    @GetMapping("/subdivision")
    fun getSoldSubdivisions(
        @ModelAttribute request: SubdivisionsRequest
    ): ApartResponse? {
        val call = apiServiceForCall(API_URL_FOR_SUBDIVISION)
            .getSubdivisions(request.code, request.dealYmd, ApartSalesRequest.KEY)
        return call.execute().body() ?: ApartResponse()
    }

    private fun apiServiceForCall(apiUrl: String): ApartApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

        return retrofit.create(ApartApiService::class.java)
    }

    companion object {
        private const val API_URL_FOR_APART = "http://openapi.molit.go.kr:8081"
        private const val API_URL_FOR_SUBDIVISION = "http://openapi.molit.go.kr"
    }
}
