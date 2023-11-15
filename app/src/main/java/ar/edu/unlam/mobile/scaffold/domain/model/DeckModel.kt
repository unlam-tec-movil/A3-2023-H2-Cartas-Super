package ar.edu.unlam.mobile.scaffold.domain.model

data class DeckModel(
    val id: Int,
    val carta1: HeroModel,
    val carta2: HeroModel,
    val carta3: HeroModel,
    val carta4: HeroModel,
    val carta5: HeroModel,
    val carta6: HeroModel
)
