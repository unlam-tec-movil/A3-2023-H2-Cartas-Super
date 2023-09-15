package ar.edu.unlam.mobile.scaffold.data.repository

import ar.edu.unlam.mobile.scaffold.data.database.entities.AppearanceEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.BiographyEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.HeroEntity
import ar.edu.unlam.mobile.scaffold.domain.hero.Appearance
import ar.edu.unlam.mobile.scaffold.domain.hero.Biography
import ar.edu.unlam.mobile.scaffold.domain.hero.DataHero

object DataHeroConverter {

    fun dataHeroToHeroEntity(dh: DataHero): HeroEntity {
        return HeroEntity(
            appearance = appearanceToAppearanceEntity(dh.appearance),
            biography = biographyToBiographyEntity(dh.biography),
            id = dh.id.toInt(),
            connections = dh.connections,
            image = dh.image,
            name = dh.name,
            powerstats = dh.powerstats,
            work = dh.work,
            isFavorite = dh.isFavorite
        )
    }

    fun heroEntityToDataHero(h: HeroEntity): DataHero {
        return DataHero(
            appearance = appearanceEntityToAppearance(h.appearance),
            biography = biographyEntityToBiography(h.biography),
            id = h.id.toString(),
            connections = h.connections,
            image = h.image,
            name = h.name,
            powerstats = h.powerstats,
            work = h.work,
            isFavorite = h.isFavorite
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
}