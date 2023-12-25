package domain

import kotlinx.serialization.Serializable

@Serializable
data class Ticket(
    val id: Long,
    val clientId: Long,
    val seatNumber: Int,
    val session: MovieSession


) {
    override fun toString(): String {
        return "Номер билета: $id\nНомер пользователя: $clientId\nНомер места: $seatNumber\n$session)"
    }
}
