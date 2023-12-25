package domain

import kotlinx.serialization.Serializable

@Serializable
enum class SeatStatus {
    FREE,
    SOLD
}