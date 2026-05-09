package cat.itic.myapp

import com.google.gson.annotations.SerializedName

data class Videogame(
    val id: Long,
    val name: String,
    val platform: String,
    val genre: String?,
    val developer: String?,
    @SerializedName("releasedDate")
    val releasedDate: String,
    val description: String?,
    val image: String?
)