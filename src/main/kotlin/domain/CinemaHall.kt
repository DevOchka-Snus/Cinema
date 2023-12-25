package domain

import kotlinx.serialization.Serializable


@Serializable
data class CinemaHall(
    val seatList: MutableList<SeatStatus>
) {
    init {
        for (i in 1..100) {
            seatList.add(SeatStatus.FREE)
        }
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        for (i in 0 until 100) {
            stringBuilder.append(if (seatList[i] == SeatStatus.FREE) i + 1 else "X")
            stringBuilder.append(if ((i + 1) % 10 == 0) "\n" else " ")
        }
        return stringBuilder.toString()
    }


}