package ar.edu.unlam.mobile.scaffold.domain.hero

import com.google.gson.annotations.SerializedName


data class Work(
    @SerializedName("base") val base: String = "NA",
    @SerializedName("occupation") val occupation: String = "NA"
)
