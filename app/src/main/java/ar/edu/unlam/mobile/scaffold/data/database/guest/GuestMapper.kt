package ar.edu.unlam.mobile.scaffold.data.database.guest

import ar.edu.unlam.mobile.scaffold.data.database.entities.GuestEntity

fun GuestEntity.toDomain(): Guest{
    return Guest(id = this.id, username = this.username)
}

fun Guest.toEntity(): GuestEntity {
    return GuestEntity(id = this.id, username = this.username)
}