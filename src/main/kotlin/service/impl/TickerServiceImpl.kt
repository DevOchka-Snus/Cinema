package service.impl

import data.TicketStorage
import domain.MovieSession
import domain.OutputModel
import domain.SeatStatus
import domain.Ticket
import service.MovieSessionService
import service.TicketService
import validation.model.Error
import validation.model.Result
import validation.model.Success
import java.time.LocalDateTime
import java.util.UUID

class TickerServiceImpl(
    private val ticketStorage: TicketStorage,
    private val movieSessionService: MovieSessionService,
): TicketService {
    override fun sellTicket(session: MovieSession, seatNumber: Int, clientId: Long): Ticket {
        session.isMutable = false
        session.cinemaHall.seatList[seatNumber - 1] = SeatStatus.SOLD
        movieSessionService.update(session)
        val ticket = Ticket(UUID.randomUUID().mostSignificantBits, clientId, seatNumber, session)
        ticketStorage.save(ticket)
        return ticket
    }

    override fun refundTicket(ticketId: Long): Result {
        val result = ticketStorage.get(ticketId)
        if (result is Error) {
            return result
        }
        val ticket = (result as Success<Ticket>).body
        if (ticket.session.sessionBegin.isBefore(LocalDateTime.now())) {
            return Error(OutputModel("сеанс уже начался"))
        }
        ticketStorage.delete(ticketId)
        ticket.session.cinemaHall.seatList[ticket.seatNumber - 1] = SeatStatus.FREE
        checkSession(ticket.session)
        val x = movieSessionService.update(ticket.session)
        if (x is Error) {
            return x
        }
        return Success(ticket)
    }

    private fun checkSession(session: MovieSession) {
        val allTickets = ticketStorage.getAll()
        if (
            allTickets.isEmpty() ||
            allTickets.filter {
                it.session.id == session.id
            }.none()
        ) {
            session.isMutable = true
        }
    }

    override fun getAll(): List<Ticket> {
        return ticketStorage.getAll()
    }

    override fun checkTicket(id: Long): Result {
        var result = ticketStorage.get(id)
        if (result is Error) {
            return result
        }
        var ticket = (result as Success<Ticket>).body
        if (ticket.session.sessionEnd.isBefore(LocalDateTime.now())) {
            return Error(OutputModel("Сеанс уже закончился"))
        }
        ticketStorage.delete(ticket.id)
        updateMovieSession(ticket.session)
        return Success(ticket)
    }

    override fun getAllByClientId(clientId: Long): List<Ticket> {
        val allTickets = ticketStorage.getAll()
        return allTickets.filter { it.clientId == clientId }
    }

    private fun updateMovieSession(session: MovieSession) {
        var tickets = getAll()
        for (ticket in tickets) {
            if (ticket.session.id == session.id) {
                return
            }
        }
        session.isMutable = true
        movieSessionService.update(session)
    }

}