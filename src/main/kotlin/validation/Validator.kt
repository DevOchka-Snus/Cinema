package validation

import domain.SessionDateTime
import validation.model.Result
import java.time.LocalDateTime

interface Validator {
    fun validateInt(command: String): Int
    fun validateLong(command: String): Long
    fun validateTime(command: String): SessionDateTime
    fun validateString(command: String): String
}