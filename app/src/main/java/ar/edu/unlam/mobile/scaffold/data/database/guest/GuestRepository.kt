package ar.edu.unlam.mobile.scaffold.data.database.guest

import ar.edu.unlam.mobile.scaffold.data.database.dao.GuestDao

class GuestRepository (private val dao: GuestDao) {
    suspend fun addGuestInDatabase(guest: Guest) {
        dao.insertGuest(guest.toEntity())
    }

    suspend fun verifyDatabase(): Boolean {
        return dao.getGuests().isNotEmpty()
    }

}