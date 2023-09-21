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
    @Embedded val connections: Connections = Connections(),
    @Embedded val image: Image = Image(),
    @ColumnInfo("name") val name: String = "NA",
    @Embedded val powerstats: Powerstats = Powerstats(),
    @Embedded val work: Work = Work(),
    @ColumnInfo("isFavorite") val isFavorite: Boolean = false
)

fun HeroEntity.toHeroModel(): DataHero {
    return DataHero(
        appearance = appearanceEntityToAppearance(this.appearance),
        biography = biographyEntityToBiography(this.biography),
        id = this.id.toString(),
        connections = this.connections,
        image = this.image,
        name = this.name,
        powerstats = this.powerstats,
        work = this.work,
        isFavorite = this.isFavorite
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
