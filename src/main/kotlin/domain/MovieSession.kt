package domain

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class MovieSession (
    val id: Long,
    val movie: Movie,
    val cinemaHall: CinemaHall,
    val sessionBegin: SessionDateTime,
    val sessionEnd: SessionDateTime,
    var isMutable: Boolean
) {
    override fun toString(): String {
        return "Номер сеанса: $id\n$movie\nЗрительный зал:\n$cinemaHall\nНачало сеанса: $sessionBegin\nКонец сеанса: $sessionEnd"
    }
}
