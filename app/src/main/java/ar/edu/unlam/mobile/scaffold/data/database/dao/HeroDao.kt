package ar.edu.unlam.mobile.scaffold.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import ar.edu.unlam.mobile.scaffold.data.database.entities.HeroEntity

@Dao
interface HeroDao {

    @Query("SELECT * FROM hero_table ORDER BY id DESC")
    suspend fun getAll(): List<HeroEntity>

    @Query("SELECT * FROM hero_table WHERE id IS :idHero")
    suspend fun getHero(idHero: Int): HeroEntity?

    @Upsert
    suspend fun insertAll(heroList: List<HeroEntity>)

    @Upsert
    suspend fun insertHero(hero: HeroEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeroes(heroes: List<HeroEntity>)

    @Query("SELECT * FROM hero_table ORDER BY id DESC LIMIT :quantity")
    suspend fun getAllDeck(quantity: Int): List<HeroEntity>

}
