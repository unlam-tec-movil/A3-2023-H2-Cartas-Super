package ar.edu.unlam.mobile.scaffold.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ar.edu.unlam.mobile.scaffold.data.database.entities.DeckEntity

@Dao
interface DeckDao {

    @Query("SELECT * FROM deck_table ORDER BY id")
    suspend fun getAll(): List<DeckEntity>

    @Query("SELECT * FROM deck_table WHERE id is :id")
    suspend fun getDeck(id: Int): DeckEntity

    @Insert
    suspend fun insertDeck(deck: DeckEntity)

    @Insert
    suspend fun insertDeckList(deckList: List<DeckEntity>)
}
