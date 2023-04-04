package com.ourtown.controller
import com.ourtown.controller.model.ApartModel
import com.ourtown.controller.model.ApartSalesInfo
import com.ourtown.retrofit.ApartApiService
import com.ourtown.retrofit.AreaCodeApiService
import com.ourtown.retrofit.model.*
import org.springframework.web.bind.annotation.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

@RequestMapping("/sales")
@RestController
class OurTownController {
    @GetMapping("areacode")
    fun getAreaCode(
        @RequestParam type: AreaCodeType,
        @RequestParam code: String? = null
    ): List<AreaCodeModel> {
        val regCode = when(type) {
            AreaCodeType.SI -> "*00000000"
            AreaCodeType.GU -> {
                if (code == null) return emptyList()
                code.subSequence(0, 2).toString() + "*" + "00000"
            }
        }

        val call = areaCodeApiServiceForCall(API_URL_FOR_AREACODE)
            .getData(regCode)

        return call.execute().body()?.toModels() ?: emptyList()
    }

    @GetMapping
    fun getSoldList(
        @ModelAttribute request: ApartSalesRequest
    ): ApartModel {
        request.validate()
        val call = apiServiceForCall(API_URL_FOR_APART)
            .getData(request.code, request.dealYmd, ApartSalesRequest.KEY)
        val response = call.execute().body()?.body?.items?.item

        return response?.let {
            ApartModel(
                response.sortedByDescending { it.day }.map {
                    ApartSalesInfo.createBy(it, false)
                }
            )
        } ?: ApartModel()
    }

    @GetMapping("/subdivision")
    fun getSoldSubdivisions(
        @ModelAttribute request: SubdivisionsRequest
    ): ApartModel {
        request.validate()

        val call = apiServiceForCall(API_URL_FOR_SUBDIVISION)
            .getSubdivisions(request.code, request.dealYmd, ApartSalesRequest.KEY)
        val response = call.execute().body()?.body?.items?.item

        return response?.let {
            ApartModel(
                response.sortedByDescending { it.day }.map {
                    ApartSalesInfo.createBy(it, true)
                }
            )
        } ?: ApartModel()
    }

    private fun apiServiceForCall(apiUrl: String): ApartApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

        return retrofit.create(ApartApiService::class.java)
    }

    private fun areaCodeApiServiceForCall(apiUrl: String): AreaCodeApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(AreaCodeApiService::class.java)
    }

    companion object {
        private const val API_URL_FOR_APART = "http://openapi.molit.go.kr:8081"
        private const val API_URL_FOR_SUBDIVISION = "http://openapi.molit.go.kr"
        private const val API_URL_FOR_AREACODE = "https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app"
    }
}