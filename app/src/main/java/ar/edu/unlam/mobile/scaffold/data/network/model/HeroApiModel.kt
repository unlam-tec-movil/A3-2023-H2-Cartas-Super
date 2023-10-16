package ar.edu.unlam.mobile.scaffold.data.network.model

import ar.edu.unlam.mobile.scaffold.data.database.entities.AppearanceEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.BiographyEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.ConnectionsEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.HeroEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.ImageEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.PowerstatsEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.WorkEntity
import com.google.gson.annotations.SerializedName

data class HeroApiModel(
    @SerializedName("appearance") val appearance: Appearance = Appearance(),
    @SerializedName("biography") val biography: Biography = Biography(),
    @SerializedName("connections") val connections: Connections = Connections(),
    @SerializedName("id") val id: String = "NA",
    @SerializedName("image") val image: Image = Image(),
    @SerializedName("name") val name: String = "NA",
    @SerializedName("powerstats") val powerstats: Powerstats = Powerstats(),
    @SerializedName("response") val response: String = "NA",
    @SerializedName("work") val work: Work = Work(),
)

fun HeroApiModel.toHeroEntityModel(): HeroEntity {
    return HeroEntity(
        appearance = appearanceToAppearanceEntity(this.appearance),
        biography = biographyToBiographyEntity(this.biography),
        id = this.id.toInt(),
        connections = connectionsApiModelToConnectionsEntity(this.connections),
        image = imageApiModelToImageEntity(this.image),
        name = this.name,
        powerstats = powerstatsApiModelToPowerstatsEntity(this.powerstats),
        work = workApiModelToWorkEntity(this.work)
    )
}

private fun workApiModelToWorkEntity(work: Work): WorkEntity {
    return WorkEntity(
        base = work.base,
        occupation = work.occupation
    )
}
private fun powerstatsApiModelToPowerstatsEntity(powerstats: Powerstats): PowerstatsEntity {
    return PowerstatsEntity(
        combat = powerstats.combat,
        durability = powerstats.durability,
        intelligence = powerstats.intelligence,
        power = powerstats.power,
        speed = powerstats.speed,
        strength = powerstats.strength
    )
}
private fun imageApiModelToImageEntity(image: Image): ImageEntity {
    return ImageEntity(
        url = image.url
    )
}
private fun connectionsApiModelToConnectionsEntity(connections: Connections): ConnectionsEntity {
    return ConnectionsEntity(
        groupAffiliation = connections.groupAffiliation,
        relatives = connections.relatives
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
