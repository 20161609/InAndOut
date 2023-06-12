import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class kakaoMapResponse(
    val documents: List<Place>,
    val meta: Meta
)

data class Place(
    val address_name: String,
    val category_group_code: String,
    val category_group_name: String,
    val category_name: String,
    val distance: String,
    val id: String,
    val phone: String,
    val place_name: String,
    val place_url: String,
    val road_address_name: String,
    val x: String,
    val y: String
)

data class Meta(
    val is_end: Boolean,
    val pageable_count: Int,
    val same_name: SameName,
    val total_count: Int
)

data class SameName(
    val keyword: String,
    val region: List<String>,
    val selected_region: String
)

interface KakaoMapApi {
    @Headers("Authorization: KakaoAK 52bdc80ac9aec713512e402cecfa4d60")
    @GET("/v2/local/search/keyword.json")
    suspend fun searchPlaces(
        @Query("query") query: String,
        @Query("category_group_code") category: String? = null,
        @Query("x") x: Double? = null,
        @Query("y") y: Double? = null
    ): Response<kakaoMapResponse>
}

object RetrofitBuilder {
    private const val BASE_URL = "https://dapi.kakao.com"
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val kakaoMapApi: KakaoMapApi = getRetrofit().create(KakaoMapApi::class.java)
}

fun crawling(query: String){
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitBuilder.kakaoMapApi.searchPlaces(query)
            withContext(Dispatchers.Main) {
                Log.e(query, "???")
                if (response.isSuccessful) {
                    response.body()?.let {
                        for (hospital in it.documents) {
                            val document = withContext(Dispatchers.IO) {
                                Jsoup.connect(hospital.place_url).get()
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("Exception", e.toString())
        }
    }
}