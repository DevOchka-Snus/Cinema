package controller.impl

import controller.ClientController
import domain.MovieSession
import domain.SeatStatus
import service.MovieSessionService
import service.TicketService
import validation.Validator
import validation.model.Error

class ClientControllerImpl(
    private val validator: Validator,
    private val movieSessionService: MovieSessionService,
    private val ticketService: TicketService
): ClientController {
    override fun buyTicket(clientId: Long) {
        val sessions = movieSessionService.getAllForBuyingTicket()
        if (sessions.isEmpty()) {
            println("Свободные сеансы отсутствуют")
            return
        }
        val session = chooseMovieSession(sessions)
        val seatNumber = chooseSeatNumber(session)
        val ticket = ticketService.sellTicket(session, seatNumber, clientId)
        println("Ваш билет:")
        println(ticket)
    }

    private fun chooseSeatNumber(session: MovieSession): Int {
        do {
            println("Выберите место")
            println(session.cinemaHall)
            val seatNumber = validator.validateInt("Выберите место и введите его номер")
            if (seatNumber < 1 || seatNumber > 100) {
                println("Нет номера с таким местом")
                continue
            }
            if (session.cinemaHall.seatList[seatNumber - 1] == SeatStatus.SOLD) {
                println("Данное место уже продано")
                continue
            }
            return seatNumber
        } while (true)
    }

    private fun chooseMovieSession(sessions: List<MovieSession>): MovieSession {
        println("Список доступных сеансов")
        for (movieSession in sessions) {
            println(movieSession)
        }

        do {
            val id = validator.validateLong("Выберите сеанс и введите его номер")
            for (movieSession in sessions) {
                if (movieSession.id == id) {
                    return movieSession
                }
            }
            println("Нет сеанса с таким номером")
        } while (true)
    }

    override fun refundTicket(clientId: Long) {
        do {
            val ticketId = validator.validateLong("Введите номер билета")
            val result = ticketService.refundTicket(ticketId)
            if (result is Error) {
                println(result.outputModel.message)
                continue
            }
            println("Возврат выполнен успешно")
            return
        } while (true)
    }

    override fun printAllTicketById(clientId: Long) {
        val clientTickets = ticketService.getAllByClientId(clientId)
        if (clientTickets.isEmpty()) {
            println("У вас нет действующих билетов на данный момент")
            return
        }
        for (ticket in clientTickets) {
            println(ticket)
            println()
        }
    }
}