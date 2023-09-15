package ar.edu.unlam.mobile.scaffold.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ar.edu.unlam.mobile.scaffold.data.database.entities.AppearanceEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.BiographyEntity
import ar.edu.unlam.mobile.scaffold.domain.hero.Connections
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