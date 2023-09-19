package ar.edu.unlam.mobile.scaffold.domain.hero

import ar.edu.unlam.mobile.scaffold.data.database.entities.AppearanceEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.BiographyEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.HeroEntity
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

fun DataHero.toHeroEntity(): HeroEntity {
    return HeroEntity(
        appearance = appearanceToAppearanceEntity(this.appearance),
        biography = biographyToBiographyEntity(this.biography),
        id = this.id.toInt(),
        connections = this.connections,
        image = this.image,
        name = this.name,
        powerstats = this.powerstats,
        work = this.work,
        isFavorite = this.isFavorite
    )
}

private fun appearanceToAppearanceEntity(app: Appearance): AppearanceEntity {
    return AppearanceEntity(
        height = app.height.toString(),
        weight = app.weight.toString(),
        eyeColor = app.eyeColor,
        gender = app.gender,
        hairColor = app.hairColor,
        race = app.race
    )
}

fun biographyToBiographyEntity(bio: Biography): BiographyEntity {
    return BiographyEntity(
        aliases = bio.aliases.toString(),
        alignment = bio.alignment,
        alterEgos = bio.alterEgos,
        firstAppearance = bio.firstAppearance,
        fullName = bio.fullName,
        placeOfBirth = bio.placeOfBirth,
        publisher = bio.publisher
    )
}