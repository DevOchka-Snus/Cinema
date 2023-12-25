package validation.impl

import domain.OutputModel
import domain.SessionDateTime
import validation.Validator
import validation.model.Error
import validation.model.Result
import validation.model.Success
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ValidatorImpl: Validator {
    override fun validateInt(command: String): Int {
        do {
            println(command)
            val value = readln()
            var int: Int
            try {
                int = value.toInt()
                return int
            } catch (e: Exception) {
                println("неверный ввод")
            }
        } while (true)
    }

    override fun validateLong(command: String): Long {
        do {
            println(command)
            val value = readln()
            try {
                return value.toLong()
            } catch (e: Exception) {
                println("неверный ввод")
            }
        } while (true)
    }

    override fun validateTime(command: String): SessionDateTime {
        do {
            println(command)
            val value = readln()
            try {
                val dateTime = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy MM dd HH:mm"))
                if (dateTime.isBefore(LocalDateTime.now())) {
                    throw Exception()
                }
                return SessionDateTime(value)
            } catch (e: Exception) {
                println("неверный ввод")
            }
        } while (true)
    }

    override fun validateString(command: String): String {
        do {
            println(command)
            val value = readln()
            if (value.isBlank()) {
                println("строка не должна быть пустой")
                continue
            }
            return value
        } while (true)
    }
}