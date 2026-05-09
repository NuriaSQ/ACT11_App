package cat.itic.myapp

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface VideogameService {

    @GET("Videogame")
    suspend fun getAllVideogames(): Response<List<Videogame>>

    @GET("Videogame/{id}")
    suspend fun getVideogameById(@Path("id") id: Long): Response<Videogame>

    @POST("Videogame")
    suspend fun createVideogame(@Body videogame: Videogame): Response<String>
}