package domain

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
class SessionDateTime(val dateTimeString: String) {
    fun isAfter(value: SessionDateTime): Boolean {
        val formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm")
        val dateTime = LocalDateTime.parse(dateTimeString, formatter)
        val otherDateTime = LocalDateTime.parse(value.dateTimeString, formatter)
        return dateTime.isAfter(otherDateTime)
    }
    fun isAfter(value: LocalDateTime): Boolean {
        val formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm")
        val dateTime = LocalDateTime.parse(dateTimeString, formatter)
        return dateTime.isAfter(value)
    }
    fun isBefore(value: SessionDateTime): Boolean {
        val formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm")
        val dateTime = LocalDateTime.parse(dateTimeString, formatter)
        val otherDateTime = LocalDateTime.parse(value.dateTimeString, formatter)
        return dateTime.isBefore(otherDateTime)
    }
    fun isBefore(value: LocalDateTime): Boolean {
        val formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm")
        val dateTime = LocalDateTime.parse(dateTimeString, formatter)
        return dateTime.isBefore(value)
    }

    override fun toString(): String {
        return dateTimeString
    }
}
