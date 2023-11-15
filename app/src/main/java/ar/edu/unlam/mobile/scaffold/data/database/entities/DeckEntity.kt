package ar.edu.unlam.mobile.scaffold.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deck_table")
data class DeckEntity(
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER) @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "carta_1") val carta1: Int,
    @ColumnInfo(name = "carta_2") val carta2: Int,
    @ColumnInfo(name = "carta_3") val carta3: Int,
    @ColumnInfo(name = "carta_4") val carta4: Int,
    @ColumnInfo(name = "carta_5") val carta5: Int,
    @ColumnInfo(name = "carta_6") val carta6: Int
)
