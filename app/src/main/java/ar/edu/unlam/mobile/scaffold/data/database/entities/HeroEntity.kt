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
import ar.edu.unlam.mobile.scaffold.domain.model.AppearanceModel
import ar.edu.unlam.mobile.scaffold.domain.model.BiographyModel
import ar.edu.unlam.mobile.scaffold.domain.model.ConnectionsModel
import ar.edu.unlam.mobile.scaffold.domain.model.Hero
import ar.edu.unlam.mobile.scaffold.domain.model.ImageModel
import ar.edu.unlam.mobile.scaffold.domain.model.StatModel
import ar.edu.unlam.mobile.scaffold.domain.model.WorkModel

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

fun HeroEntity.toHeroApiModel(): HeroApiModel {
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

fun HeroEntity.toHero(): Hero {
    return Hero(
        id = this.id,
        appearance = AppearanceModel(
            eyeColor = this.appearance.eyeColor,
            gender = this.appearance.gender,
            hairColor = this.appearance.hairColor,
            height = this.appearance.height,
            race = this.appearance.race,
            weight = this.appearance.weight
        ),
        biography = BiographyModel(
            aliases = this.biography.aliases,
            alignment = this.biography.alignment,
            alterEgos = this.biography.alterEgos,
            firstAppearance = this.biography.firstAppearance,
            fullName = this.biography.fullName,
            placeOfBirth = this.biography.placeOfBirth,
            publisher = this.biography.publisher
        ),
        connections = ConnectionsModel(
            groupAffiliation = this.connections.groupAffiliation,
            relatives = this.connections.relatives
        ),
        image = ImageModel(
            url = this.image.url
        ),
        name = this.name,
        stats = StatModel(
            combat = this.powerstats.combat,
            durability = this.powerstats.durability,
            intelligence = this.powerstats.intelligence,
            power = this.powerstats.power,
            speed = this.powerstats.speed,
            strength = this.powerstats.strength
        ),
        work = WorkModel(
            base = this.work.base,
            occupation = this.work.occupation
        ),
        quantity = this.quantity
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
