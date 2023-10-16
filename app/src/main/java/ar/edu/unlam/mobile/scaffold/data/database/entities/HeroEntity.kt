package ar.edu.unlam.mobile.scaffold.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ar.edu.unlam.mobile.scaffold.domain.hero.Appearance
import ar.edu.unlam.mobile.scaffold.domain.hero.Biography
import ar.edu.unlam.mobile.scaffold.domain.hero.Connections
import ar.edu.unlam.mobile.scaffold.domain.hero.DataHero
import ar.edu.unlam.mobile.scaffold.domain.hero.Image
import ar.edu.unlam.mobile.scaffold.domain.hero.Powerstats
import ar.edu.unlam.mobile.scaffold.domain.hero.Work

@Entity(tableName = "hero_table")
data class HeroEntity(
    @ColumnInfo("id") @PrimaryKey(autoGenerate = false) val id: Int = 0,
    @Embedded val appearance: AppearanceEntity = AppearanceEntity(),
    @Embedded val biography: BiographyEntity = BiographyEntity(),
    @Embedded val connections: ConnectionsEntity = ConnectionsEntity(),
    @Embedded val image: ImageEntity = ImageEntity(),
    @ColumnInfo("name") val name: String = "NA",
    @Embedded val powerstats: PowerstatsEntity = PowerstatsEntity(),
    @Embedded val work: WorkEntity = WorkEntity(),
    @ColumnInfo("quantity") val quantity: Int = 0
)

fun HeroEntity.toHeroModel(): DataHero {
    return DataHero(
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
        combat = powerstatsEntity.combat,
        durability = powerstatsEntity.durability,
        intelligence = powerstatsEntity.intelligence,
        power = powerstatsEntity.power,
        speed = powerstatsEntity.speed,
        strength = powerstatsEntity.strength
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
