package ar.edu.unlam.mobile.scaffold.domain.hero

import com.google.gson.annotations.SerializedName

data class DataHero(
    @SerializedName("appearance") val appearance: Appearance = Appearance(),
    @SerializedName("biography") val biography: Biography = Biography(),
    @SerializedName("connections") val connections: Connections = Connections(),
    @SerializedName("id") val id: String = "NA",
    @SerializedName("image") val image: Image = Image(),
    @SerializedName("name") val name: String = "NA",
    @SerializedName("powerstats") val powerstats: Powerstats = Powerstats(),
    @SerializedName("response") val response: String = "NA",
    @SerializedName("work") val work: Work = Work(),
    val isFavorite: Boolean = false
)
