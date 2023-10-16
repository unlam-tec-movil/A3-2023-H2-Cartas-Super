package ar.edu.unlam.mobile.scaffold.domain.model

import com.google.gson.annotations.SerializedName

data class Powerstats(
    @SerializedName("combat") val combat: String = "000",
    @SerializedName("durability") val durability: String = "000",
    @SerializedName("intelligence") val intelligence: String = "000",
    @SerializedName("power") val power: String = "000",
    @SerializedName("speed") val speed: String = "000",
    @SerializedName("strength") val strength: String = "000"
)
