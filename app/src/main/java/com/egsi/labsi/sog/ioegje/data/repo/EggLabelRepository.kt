package com.egsi.labsi.sog.ioegje.data.repo

import android.util.Log
import com.egsi.labsi.sog.ioegje.domain.model.EggLabelEntity
import com.egsi.labsi.sog.ioegje.domain.model.EggLabelParam
import com.egsi.labsi.sog.ioegje.presentation.app.EggLabelApplication.Companion.EGG_LABEL_MAIN_TAG
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface EggLabelApi {
    @Headers("Content-Type: application/json")
    @POST("config.php")
    fun eggLabelGetClient(
        @Body jsonString: JsonObject,
    ): Call<EggLabelEntity>
}


private const val EGG_LABEL_MAIN = "https://egglabbell.com/"
class EggLabelRepository {

    suspend fun eggLabelGetClient(
        eggLabelParam: EggLabelParam,
        eggLabelConversion: MutableMap<String, Any>?
    ): EggLabelEntity? {
        val gson = Gson()
        val api = eggLabelGetApi(EGG_LABEL_MAIN, null)

        val eggLabelJsonObject = gson.toJsonTree(eggLabelParam).asJsonObject
        eggLabelConversion?.forEach { (key, value) ->
            val element: JsonElement = gson.toJsonTree(value)
            eggLabelJsonObject.add(key, element)
        }
        return try {
            val eggLabelRequest: Call<EggLabelEntity> = api.eggLabelGetClient(
                jsonString = eggLabelJsonObject,
            )
            val eggLabelResult = eggLabelRequest.awaitResponse()
            Log.d(EGG_LABEL_MAIN_TAG, "Retrofit: Result code: ${eggLabelResult.code()}")
            if (eggLabelResult.code() == 200) {
                Log.d(EGG_LABEL_MAIN_TAG, "Retrofit: Get request success")
                Log.d(EGG_LABEL_MAIN_TAG, "Retrofit: Code = ${eggLabelResult.code()}")
                Log.d(EGG_LABEL_MAIN_TAG, "Retrofit: ${eggLabelResult.body()}")
                eggLabelResult.body()
            } else {
                null
            }
        } catch (e: java.lang.Exception) {
            Log.d(EGG_LABEL_MAIN_TAG, "Retrofit: Get request failed")
            Log.d(EGG_LABEL_MAIN_TAG, "Retrofit: ${e.message}")
            null
        }
    }


    private fun eggLabelGetApi(url: String, client: OkHttpClient?) : EggLabelApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(client ?: OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create()
    }


}
