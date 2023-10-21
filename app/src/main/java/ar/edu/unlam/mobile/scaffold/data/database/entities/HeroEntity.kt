package ar.edu.unlam.mobile.scaffold.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ar.edu.unlam.mobile.scaffold.data.network.model.Appearance
import ar.edu.unlam.mobile.scaffold.data.network.model.Biography
import ar.edu.unlam.mobile.scaffold.data.network.model.Connections
import ar.edu.unlam.mobile.scaffold.data.network.model.HeroApiModel
import ar.edu.unlam.mobile.scaffold.data.network.model.Image
import ar.edu.unlam.mobile.scaffold.data.network.model.Powerstats
import ar.edu.unlam.mobile.scaffold.data.network.model.Work

@Entity(tableName = "hero_table")
data class HeroEntity(
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER) @PrimaryKey(autoGenerate = false) val id: Int = 0,
    @Embedded(prefix = "app") val appearance: AppearanceEntity = AppearanceEntity(),
    @Embedded(prefix = "bio") val biography: BiographyEntity = BiographyEntity(),
    @Embedded(prefix = "conn") val connections: ConnectionsEntity = ConnectionsEntity(),
    @Embedded(prefix = "image") val image: ImageEntity = ImageEntity(),
    @ColumnInfo(name = "name") val name: String = "NA",
    @Embedded(prefix = "stat") val powerstats: PowerstatsEntity = PowerstatsEntity(),
    @Embedded(prefix = "work") val work: WorkEntity = WorkEntity(),
    @ColumnInfo(name = "quantity") val quantity: Int = 0
)

fun HeroEntity.toHeroModel(): HeroApiModel {
    return HeroApiModel(
        appearance = appearanceEntityToAppearance(this.appearance),
        biography = biographyEntityToBiography(this.biography),
        id = this.id.toString(),
        connections = connectionsEntityToConnections(this.connections),
        image = imageEntityToImage(this.image),
        name = this.name,
        powerstats = powerstatsEntityToPowerstats(this.powerstats),
        work = workEntityToWork(this.work),
    )
}

private fun workEntityToWork(workEntity: WorkEntity): Work {
    return Work(
        base = workEntity.base,
        occupation = workEntity.occupation
    )
}
private fun powerstatsEntityToPowerstats(powerstatsEntity: PowerstatsEntity): Powerstats {
    return Powerstats(
        combat = powerstatsEntity.combat.toString(),
        durability = powerstatsEntity.durability.toString(),
        intelligence = powerstatsEntity.intelligence.toString(),
        power = powerstatsEntity.power.toString(),
        speed = powerstatsEntity.speed.toString(),
        strength = powerstatsEntity.strength.toString()
    )
}
private fun imageEntityToImage(imageEntity: ImageEntity): Image {
    return Image(imageEntity.url)
}

private fun connectionsEntityToConnections(connectionsEntity: ConnectionsEntity): Connections {
    return Connections(
        groupAffiliation = connectionsEntity.groupAffiliation,
        relatives = connectionsEntity.relatives
    )
}

private fun appearanceEntityToAppearance(app: AppearanceEntity): Appearance {
    return Appearance(
        height = listOf(app.height),
        weight = listOf(app.weight),
        eyeColor = app.eyeColor,
        gender = app.gender,
        hairColor = app.hairColor,
        race = app.race
    )
}

fun biographyEntityToBiography(bio: BiographyEntity): Biography {
    return Biography(
        aliases = listOf(bio.aliases),
        alignment = bio.alignment,
        alterEgos = bio.alterEgos,
        firstAppearance = bio.firstAppearance,
        fullName = bio.fullName,
        placeOfBirth = bio.placeOfBirth,
        publisher = bio.publisher
    )
}
