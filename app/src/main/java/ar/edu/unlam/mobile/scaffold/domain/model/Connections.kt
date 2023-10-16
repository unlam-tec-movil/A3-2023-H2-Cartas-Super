package ar.edu.unlam.mobile.scaffold.domain.model

import com.google.gson.annotations.SerializedName

data class Connections(
    @SerializedName("groupAffiliation") val groupAffiliation: String = "NA",
    @SerializedName("relatives") val relatives: String = "NA"
)
