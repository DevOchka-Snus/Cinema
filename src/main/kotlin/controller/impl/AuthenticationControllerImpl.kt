package controller.impl

import controller.AuthenticationController
import domain.Person
import domain.Role
import service.PersonService
import validation.Validator
import validation.model.Error
import validation.model.Success
import java.util.*

class AuthenticationControllerImpl(
    private val personService: PersonService,
    private val validator: Validator
): AuthenticationController {
    override fun register(role: Role) {
        val login = validator.validateString("Введите логин")
        do {
            val password = validator.validateString("Введите пароль")
            val passwordConfirmation = validator.validateString("Введите подтверждение пароля")
            if (password != passwordConfirmation) {
                println("Несовпадение между паролем и его подтверждением")
                continue
            }
            val result = personService.register(
                Person(
                    UUID.randomUUID().mostSignificantBits,
                    login,
                    Base64.getEncoder().encodeToString(
                        password.toByteArray()
                    ),
                    role
                )
            )
            if (result is Error) {
                println(result.outputModel.message)
                continue
            }
            println("Регистрация прошла успешно")
            return
        } while (true)
    }

    override fun login(role: Role): Long {
        do {
            val login = validator.validateString("Введите логин")
            val password = validator.validateString("Введите пароль")
            val result = personService.login(login, password, role)
            if (result is Error) {
                println(result.outputModel.message)
                println("Возможно, вы не зарегистрировались?")
                val command = validator.validateString("Если хотите зарегистрироваться - введите '1', " +
                        "если хотите повторить вход - любой другой символ")
                if (command == "1") {
                    register(role)
                    println("Теперь выполните вход")
                }
                continue
            }
            println("Вход выполнен\n")
            return (result as Success<Person>).body.id
        } while (true)
    }

    override fun logout(): Boolean {
        println("Хотите выйти из аккаунта?\n1 - Да\n2 - Нет")
        do {
            val command = validator.validateInt("Введите номер команды")
            when (command) {
                1 -> return true
                2 -> return false
                else -> println("Неверный ввод")
            }
        } while (true)
    }
}
