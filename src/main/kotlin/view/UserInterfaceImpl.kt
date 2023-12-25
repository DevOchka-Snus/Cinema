package view

import controller.AuthenticationController
import controller.CinemaWorkerController
import controller.ClientController
import domain.Role
import domain.Type
import validation.Validator

class UserInterfaceImpl(
    private val validator: Validator,
    private val authenticationController: AuthenticationController,
    private val clientController: ClientController,
    private val cinemaWorkerController: CinemaWorkerController
): UserInterface {
    private fun clientProcess() {
        val id = preAuthorize(Role.CLIENT)
        do {
            println("Список команд:")
            println("1 - купить билет на сеанс")
            println("2 - вернуть билет")
            println("3 - вывести все ваши билеты")
            val command = validator.validateInt("Введите номер команды")
            when (command) {
                1 -> clientController.buyTicket(id)
                2 -> clientController.refundTicket(id)
                3 -> clientController.printAllTicketById(id)
                else -> println("Нет такой команды")
            }
        } while (!authenticationController.logout())

    }

    private fun cinemaWorkerProcess() {
        preAuthorize(Role.WORKER)
        do {
            println("Список команд:")
            println("1 - добавить фильм")
            println("2 - добавить сеанс")
            println("3 - редактировать фильм")
            println("4 - редактировать сеанс")
            println("5 - проверить билет")
            val command = validator.validateInt("Введите номер команды")
            when (command) {
                1 -> cinemaWorkerController.create(Type.MOVIE)
                2 -> cinemaWorkerController.create(Type.SESSION)
                3 -> cinemaWorkerController.update(Type.MOVIE)
                4 -> cinemaWorkerController.update(Type.SESSION)
                5 -> cinemaWorkerController.checkTicket()
                else -> println("Нет такой команды")
            }
        } while (!authenticationController.logout())
    }

    private fun preAuthorize(role: Role): Long {
        println("Хотите войти или зарегистрировать новый аккаунт?")
        println("1 - войти")
        println("2 - регистрация")
        do {
            val command = validator.validateInt("Введите номер команды:")
            when (command) {
                1 -> {
                    return authenticationController.login(role)
                }

                2 -> {
                    authenticationController.register(role)
                    println("Теперь выполните вход")
                    return authenticationController.login(role)
                }
                else -> println("Неверный ввод")
            }
        } while (true)
    }

    override fun start() {
        do {
            println("Хотите войти как:")
            println("1 - пользователь")
            println("2 - сотрудник")
            val role = validator.validateInt("Введите номер роли")
            when (role) {
                1 -> clientProcess()
                2 -> cinemaWorkerProcess()
                else -> println("Нет такой роли")
            }
        } while (!finish())
    }

    override fun finish(): Boolean {
        do {
            println("Хотите завершить работу программы?\n1 - Да\n2 - Нет")
            val command = validator.validateInt("Введите номер команды")
            when (command) {
                1 -> return true
                2 -> return false
                else -> println("Неверный ввод")
            }
        } while (true)
    }
}